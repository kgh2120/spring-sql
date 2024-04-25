package com.kk.springsql.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import com.kk.springsql.entity.Team;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@SpringBootTest
class InsertQueryTest {
    /*
        기준  100000;
        EntityManager Persist :  cost 2775ms
        DataJpa Save :  cost 3448ms
        DataJpa SaveAll :  cost 3177ms
        Jdbc Insert :  cost 650ms
        Jdbc Bulk Insert :  cost 503ms

        기준  500000;
        EntityManager Persist :  cost 9483ms
        DataJpa Save :  cost 11682ms
        DataJpa SaveAll :  cost 11191ms
        Jdbc Insert :  cost 1855ms
        Jdbc Bulk Insert :  cost 1434ms
     */

	@Autowired
	InsertService insertService;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	DataSource dataSource;

	SimpleJdbcInsert simpleJdbcInsert;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	final int N = 10000;

	@BeforeEach
	void beforeEach() {
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("team")
			.usingGeneratedKeyColumns("id");

	}

	@Test
	void jdbcTemplateUpdateTest() throws Exception {
		// given
		String testName = "[JdbcTemplate update]";

		long start = System.currentTimeMillis();
		for (int i = 0; i < N; i++) {
			jdbcTemplate.update("insert into team (name) values (?)", "team");
		}
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, N, end - start);
	}

	@Test
	void simpleJdbcInsertExecuteTest() throws Exception {
		// given
		String testName = "[SimpleJdbcInsert Execute]";
		long start = System.currentTimeMillis();
		for (int i = 0; i < N; i++) {
			Map<String, Object> params = new HashMap<>(1);
			params.put("name", "team");
			simpleJdbcInsert.execute(params);
		}
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, N, end - start);
	}

	@Test
	void preparedStatementCreatorTest() throws Exception {
		// given
		String testName = "[PreparedStatementCreator]";
		long start = System.currentTimeMillis();
		for (int i = 0; i < N; i++) {
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
		log.info("{} N -> {} :  cost {}ms ", testName, N, end - start);
	}

	@Test
	void namedParameterJdbcTemplateHashMapTest() throws Exception {
		// given
		String testName = "[NamedParameterJdbcTemplate - Map]";
		long start = System.currentTimeMillis();
		for (int i = 0; i < N; i++) {
			String sql = "insert into team (name) values (:name)";
			Map<String, Object> params = new HashMap<>();
			params.put("name", "team");
			namedParameterJdbcTemplate.update(sql, params);
		}
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, N, end - start);
	}

	@Test
	void namedParameterJdbcTemplateBeanPropertySqlParameterSourceTest() throws Exception {
		// given
		String testName = "[NamedParameterJdbcTemplate - BeanPropertySqlParameterSource]";
		long start = System.currentTimeMillis();
		for (int i = 0; i < N; i++) {
			String sql = "insert into team (name) values (:name)";
			Team team = new Team();
			team.setName("team");
			BeanPropertySqlParameterSource namedParameterSource = new BeanPropertySqlParameterSource(team);
			namedParameterJdbcTemplate.update(sql, namedParameterSource);
		}
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, N, end - start);
	}


	@Test
	void jdbcTemplateBulkUpdateTest() throws Exception {
		// given
		String testName = "[JdbcTemplate BulkUpdate]";

		long start = System.currentTimeMillis();
		List<String> teamNames =  new ArrayList<>(N);
		for (int i = 0; i < N; i++) {
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
		log.info("{} N -> {} :  cost {}ms ", testName, N, end - start);
	}

	@Test
	void namedParameterJdbcTemplateBulkUpdateTest() throws Exception {
		// given
		String testName = "[NamedParameterJdbcTemplate BulkUpdate]";

		long start = System.currentTimeMillis();
		SqlParameterSource[] parameterSources = new SqlParameterSource[N];
		for (int i = 0; i < N; i++) {
			Map<String, Object> params = new HashMap<>();
			params.put("name", "team");
			parameterSources[i] = new MapSqlParameterSource(params);
		}

		namedParameterJdbcTemplate.batchUpdate("insert into team (name) values (:name)", parameterSources);
		long end = System.currentTimeMillis();
		log.info("{} N -> {} :  cost {}ms ", testName, N, end - start);
	}

}