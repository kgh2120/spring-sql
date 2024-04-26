package com.kk.springsql.insert;

import static com.kk.springsql.entity.QTeamEntity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;

import com.kk.springsql.entity.QTeamEntity;
import com.kk.springsql.entity.Team;
import com.kk.springsql.entity.TeamEntity;
import com.kk.springsql.mapper.TeamMapper;
import com.kk.springsql.repository.TeamRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
@Transactional
@SpringBootTest
class InsertQueryTest {

	@Autowired
	InsertService insertService;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	DataSource dataSource;

	SimpleJdbcInsert simpleJdbcInsert;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	EntityManager entityManager;

	@Autowired
	TeamMapper teamMapper;

	JPAQueryFactory jq;

	final int N = 10000;

	@BeforeEach
	void beforeEach() {
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("team")
			.usingGeneratedKeyColumns("id");

		jq = new JPAQueryFactory(entityManager);

	}


	// Spring Data JPA
	// @Test
	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {100, 1000, 10000, 10_0000, 50_0000})
	void dataJpaSaveTest(int n) throws Exception{
		String testName = "[DataJpa Save]";

		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			teamRepository.save(new TeamEntity("team"));
		}
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, n, end - start);
	}


	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {100, 1000, 10000, 10_0000, 50_0000})
	void dataJpaSaveAllTest(int n) throws Exception{
		String testName = "[DataJpa SaveAll]";

		List<TeamEntity> teams = new ArrayList<>();
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			teams.add(new TeamEntity("team"));
		}
		teamRepository.saveAll(teams);
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, n, end - start);
	}

	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {100, 1000, 10000, 10_0000, 50_0000})
	void dataJpaSaveAndFlushTest(int n) throws Exception{
		String testName = "[DataJpa SaveAndFlush]";

		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			teamRepository.saveAndFlush(new TeamEntity("team"));
		}
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, n, end - start);
	}

	@Test
	void dataJpaSaveAndFlushTest() throws Exception{
		String testName = "[DataJpa SaveAndFlush]";

		int n = 10_0000;
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			teamRepository.saveAndFlush(new TeamEntity("team"));
		}
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, n, end - start);
	}

	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {100, 1000, 10000})
	void dataJpaSaveAllAndFlushTest(int n) throws Exception{
		String testName = "[DataJpa SaveAllAndFlush]";

		List<TeamEntity> teams = new ArrayList<>();
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			teams.add(new TeamEntity("team"));
		}
		teamRepository.saveAllAndFlush(teams);
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, n, end - start);
	}

	// EntityManager
	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {100, 1000, 10000, 10_0000, 50_0000})
	void entityManagerPersistTest(int n) throws Exception{
		String testName = "[EntityManager Persist]";
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			entityManager.persist(new TeamEntity("team"));
		}
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, n, end - start);
	}

	//QueryDSL
	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {100, 1000, 10000, 10_0000, 50_0000})
	void queryDSLInsertTest(int n) throws Exception{
		String testName = "[QueryDSL Insert]";
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			jq.insert(teamEntity)
				.columns(teamEntity.name)
				.values("name")
				.execute();
		}
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, n, end - start);
	}

	// Mybatis
	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {100, 1000, 10000, 10_0000, 50_0000})
	void mybatisXMLTest(int n) throws Exception{
		String testName = "[Mybatis XML]";
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			teamMapper.insertTeamXML(new Team("team"));
		}
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, n, end - start);
	}

	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {100, 1000, 10000, 10_0000, 50_0000})
	void mybatisAnnotationTest(int n) throws Exception{
		String testName = "[Mybatis Annotation]";
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			teamMapper.insertTeamDirect(new Team("team"));
		}
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, n, end - start);
	}

	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {100, 1000, 10000, 10_0000, 50_0000})
	void mybatisProviderTest(int n) throws Exception{
		String testName = "[Mybatis Provider]";
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			teamMapper.insertTeamProvider(new Team("team"));
		}
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, n, end - start);
	}


	// JdbcTemplate
	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {100, 1000, 10000, 10_0000, 50_0000})
	void jdbcTemplateUpdateTest(int n) throws Exception {
		// given
		String testName = "[JdbcTemplate update]";

		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			jdbcTemplate.update("insert into team (name) values (?)", "team");
		}
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, n, end - start);
	}

	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {100, 1000, 10000, 10_0000, 50_0000})
	void simpleJdbcInsertExecuteTest(int n) throws Exception {
		// given
		String testName = "[SimpleJdbcInsert Execute]";
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			Map<String, Object> params = new HashMap<>(1);
			params.put("name", "team");
			simpleJdbcInsert.execute(params);
		}
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, n, end - start);
	}



	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {100, 1000, 10000, 10_0000, 50_0000})
	void preparedStatementCreatorTest(int n) throws Exception {
		// given
		String testName = "[PreparedStatementCreator]";
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pstm = con.prepareStatement("insert into team (name) values (?)");
					pstm.setString(1, "team");
					return pstm;
				}
			});
		}
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, n, end - start);
	}

	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {100, 1000, 10000, 10_0000, 50_0000})
	void namedParameterJdbcTemplateHashMapTest(int n) throws Exception {
		// given
		String testName = "[NamedParameterJdbcTemplate - Map]";
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			String sql = "insert into team (name) values (:name)";
			Map<String, Object> params = new HashMap<>();
			params.put("name", "team");
			namedParameterJdbcTemplate.update(sql, params);
		}
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, n, end - start);
	}

	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {100, 1000, 10000, 10_0000, 50_0000})
	void namedParameterJdbcTemplateBeanPropertySqlParameterSourceTest(int n) throws Exception {
		// given
		String testName = "[NamedParameterJdbcTemplate - BeanPropertySqlParameterSource]";
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			String sql = "insert into team (name) values (:name)";
			Team team = new Team();
			team.setName("team");
			BeanPropertySqlParameterSource namedParameterSource = new BeanPropertySqlParameterSource(team);
			namedParameterJdbcTemplate.update(sql, namedParameterSource);
		}
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, n, end - start);
	}


	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {100, 1000, 10000, 10_0000, 50_0000})
	void jdbcTemplateBulkUpdateTest(int n) throws Exception {
		// given
		String testName = "[JdbcTemplate BulkUpdate]";

		long start = System.currentTimeMillis();
		List<String> teamNames =  new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			teamNames.add("team");
		}

		jdbcTemplate.batchUpdate("insert into team (name) values(?)", new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, teamNames.get(i));
			}
			@Override
			public int getBatchSize() {
				return teamNames.size();
			}
		});
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, n, end - start);
	}



	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {100, 1000, 10000, 10_0000, 50_0000})
	void namedParameterJdbcTemplateBulkUpdateTest(int n) throws Exception {
		// given
		String testName = "[NamedParameterJdbcTemplate BulkUpdate]";

		long start = System.currentTimeMillis();
		SqlParameterSource[] parameterSources = new SqlParameterSource[n];
		for (int i = 0; i < n; i++) {
			Map<String, Object> params = new HashMap<>();
			params.put("name", "team");
			parameterSources[i] = new MapSqlParameterSource(params);
		}

		namedParameterJdbcTemplate.batchUpdate("insert into team (name) values (:name)", parameterSources);
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, n, end - start);
	}





}