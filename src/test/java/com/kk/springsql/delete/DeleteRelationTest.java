package com.kk.springsql.delete;

import com.kk.springsql.entity.MemberEntity;
import com.kk.springsql.entity.TeamEntity;
import com.kk.springsql.repository.MemberRepository;
import com.kk.springsql.repository.TeamRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static com.kk.springsql.PerformanceLogger.logPerf;

import java.sql.*;
import java.util.List;

/**
 * [delete by cascade] cost 1255ms
 * [deleteQueryImprove] cost 327ms
 */
@Slf4j
@Transactional
@SpringBootTest
public class DeleteRelationTest {

    @Autowired
    EntityManager em;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @BeforeEach
    void beforeEach() {
        for (int i = 0; i < 10_000; i++) {
            TeamEntity teamEntity = new TeamEntity("팀" + i);
            em.persist(teamEntity);
            for (int j = 0; j < 10; j++) {
                MemberEntity memberEntity = new MemberEntity("회원" + j, 25);
                memberEntity.associatedWithTeam(teamEntity);
                em.persist(memberEntity);
            }
        }
        em.flush();
        em.clear();
    }

    @Test
    void deleteByCascade() throws Exception {
        long start = System.currentTimeMillis();
        teamRepository.deleteAll();
        em.flush();
        long end = System.currentTimeMillis();
        logPerf(log, "delete by cascade", start, end); //  [delete by cascade] cost 505ms [delete by cascade] cost 736ms
    }

    @Test
    void deleteQueryImprove() throws Exception {
        long start = System.currentTimeMillis();

        List<Integer> teamids = teamRepository.findAllIds();
        memberRepository.deleteAllByTeam_IdIn(teamids);
        teamRepository.deleteByIdIn(teamids);
        em.flush();
        long end = System.currentTimeMillis();
        logPerf(log, "deleteQueryImprove", start, end); // [deleteQueryImprove] cost 291ms [deleteQueryImprove] cost 2884ms
        /*
            Hibernate: select t1_0.id from team t1_0
            Hibernate: delete from member where team_id in (?,?,?,?,?,?,?,?,?,?)
            Hibernate: delete from team where id in (?,?,?,?,?,?,?,?,?,?)
         */
    }

    @Test
    void deleteJdbcTemplateTest() throws Exception{
        long start = System.currentTimeMillis();
        List<Integer> teamIds = jdbcTemplate.queryForList("select t.id from Team t", Integer.class);
        jdbcTemplate.batchUpdate(
            "delete from member m where m.team_id = ? ", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {preparedStatement.setLong(1, teamIds.get(i));}
                @Override
                public int getBatchSize() {return teamIds.size();}
            });
        jdbcTemplate.batchUpdate("delete from Team t where t.id = ? ", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {preparedStatement.setLong(1, teamIds.get(i));}
                @Override public int getBatchSize() {return teamIds.size();}
            });
        long end = System.currentTimeMillis();
        logPerf(log, "deleteJdbcTemplateTest", start, end); // [deleteJdbcTemplateTest] cost 495ms
    }

}
