package com.kk.springsql.insert;

import static com.kk.springsql.entity.QTeamEntity.*;

import com.kk.springsql.entity.MemberEntity;
import com.kk.springsql.entity.Team;
import com.kk.springsql.entity.TeamEntity;
import com.kk.springsql.mapper.TeamMapper;
import com.kk.springsql.repository.MemberRepository;
import com.kk.springsql.repository.TeamRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

@Transactional
@RequiredArgsConstructor
@Service
public class InsertService {

    public static final String INSERT_MEMBER_QUERY = "insert into member (name, age, created_at, updated_at) values (?,?,?,?)";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JPAQueryFactory jq;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final TeamRepository teamRepository;
    private final EntityManager entityManager;
    private final TeamMapper teamMapper;

    public void insertEntityManagerPersist(int n) {
        for (int i = 0; i < n; i++) {
            entityManager.persist(createTeam());
        }
    }

    private static TeamEntity createTeam() {
        return new TeamEntity("팀");
    }

    public void insertDataJpaSave(int n) {
        for (int i = 0; i < n; i++) {
            teamRepository.save(createTeam());
        }
    }

    public void insertDataJpaSaveAll(int n) {
        List<TeamEntity> teamEntities = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            teamEntities.add(createTeam());
        }
        teamRepository.saveAll(teamEntities);
    }
    public void insertDataJpaSaveAndFlush(int n) {
        for (int i = 0; i < n; i++) {
            teamRepository.saveAndFlush(createTeam());
        }
    }

    public void insertDataJpaSaveAllAndFlush(int n) {
        List<TeamEntity> teamEntities = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            teamEntities.add(createTeam());
        }
        teamRepository.saveAllAndFlush(teamEntities);
    }

    public void insertQueryDslInsert(int n) {
        for (int i = 0; i < n; i++) {
            jq.insert(teamEntity)
                .columns(teamEntity.name)
                .values("name")
                .execute();
        }
    }
    public void insertMybatisXML(int n) {
        for (int i = 0; i < n; i++) {
            teamMapper.insertTeamXML(new Team("team"));
        }
    }
    public void insertMybatisAnnotation(int n) {
        for (int i = 0; i < n; i++) {
            teamMapper.insertTeamDirect(new Team("team"));
        }
    }
    public void insertMybatisProvider(int n) {
        for (int i = 0; i < n; i++) {
            teamMapper.insertTeamProvider(new Team("team"));
        }
    }
    public void insertJdbcTemplateUpdate(int n)  {
        for (int i = 0; i < n; i++) {
            jdbcTemplate.update("insert into team (name) values (?)", "team");
        }
    }
    public void insertSimpleJdbcInsertExecute(int n)  {
        // given
        for (int i = 0; i < n; i++) {
            Map<String, Object> params = new HashMap<>(1);
            params.put("name", "team");
            simpleJdbcInsert.execute(params);
        }
    }
    public void insertPreparedStatementCreator(int n)  {
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
    }
    public void insertNamedParameterJdbcTemplateHashMap(int n)  {
        // given
        for (int i = 0; i < n; i++) {
            String sql = "insert into team (name) values (:name)";
            Map<String, Object> params = new HashMap<>();
            params.put("name", "team");
            namedParameterJdbcTemplate.update(sql, params);
        }
    }
    public void insertNamedParameterJdbcTemplateBeanPropertySqlParameterSource(int n)  {
        // given
        for (int i = 0; i < n; i++) {
            String sql = "insert into team (name) values (:name)";
            Team team = new Team();
            team.setName("team");
            BeanPropertySqlParameterSource namedParameterSource = new BeanPropertySqlParameterSource(team);
            namedParameterJdbcTemplate.update(sql, namedParameterSource);
        }
    }
    public void insertJdbcTemplateBulkUpdate(int n)  {
        // given
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
    }

    public void insertNamedParameterJdbcTemplateBulkUpdate(int n)  {
        // given
      
        SqlParameterSource[] parameterSources = new SqlParameterSource[n];
        for (int i = 0; i < n; i++) {
            Map<String, Object> params = new HashMap<>();
            params.put("name", "team");
            parameterSources[i] = new MapSqlParameterSource(params);
        }
        namedParameterJdbcTemplate.batchUpdate("insert into team (name) values (:name)", parameterSources);
    }
}
