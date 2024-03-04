package com.kk.jpaqueryperformance.delete;

import com.kk.jpaqueryperformance.entity.Team;
import com.kk.jpaqueryperformance.repository.MemberRepository;
import com.kk.jpaqueryperformance.repository.TeamMapper;
import com.kk.jpaqueryperformance.repository.TeamRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.kk.jpaqueryperformance.PerformanceLogger.logPerf;

@Slf4j
@Transactional
@SpringBootTest
public class DeleteQueryPerfTest {

    @Autowired
    EntityManager em;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    TeamMapper teamMapper;

    @BeforeEach
    void beforeEach(){

        for (int i = 0; i < 10_0000; i++) {
            em.persist(new Team());
        }
        em.flush();
        em.clear();
    }

    /*
        가정 : delete in 쿼리보다 deleteAll이 느린 이유는 in 쿼리에서의 데이터가 너무 많아서 그렇다.
     */
    @Test
    void deleteAllTest() throws Exception{
        long start = System.currentTimeMillis();
        teamRepository.deleteAll();
        em.flush();
        long end = System.currentTimeMillis();
        logPerf(log, "deleteAllTest", start, end); // [deleteAllTest] cost 2477ms

    }
    @Test
    void deleteInTest() throws Exception{
        long start = System.currentTimeMillis();
        List<Integer> ids = teamRepository.findAllIds();
        teamRepository.deleteByIdIn(ids);
        em.flush();
        long end = System.currentTimeMillis();
        logPerf(log, "deleteInTest", start, end); //[deleteInTest] cost 22170ms

    }

    @Test
    void emDeleteBulkTest() throws Exception{
        long start = System.currentTimeMillis();
        List<Integer> ids = em.createQuery("select t.id from Team t ", Integer.class)
                .getResultList();
        em.createQuery("delete from Team t where t.id in :ids")
                        .setParameter("ids", ids)
                                .executeUpdate();
        em.flush();
        long end = System.currentTimeMillis();
        logPerf(log, "emDeleteBulkTest", start, end); // [deleteInTest] cost 21112ms
    }

    @Test
    void jdbcTemplateDeleteTest() throws Exception{
        long start = System.currentTimeMillis();
        List<Integer> ids = jdbcTemplate.queryForList("select t.id from Team t", Integer.class);
        jdbcTemplate.batchUpdate("delete from Team t where t.id = ?", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1, ids.get(i));
            }

            @Override
            public int getBatchSize() {
                return ids.size();
            }
        });

        long end = System.currentTimeMillis();
        logPerf(log, "jdbcBulkDeleteTest", start, end); // [jdbcBulkDeleteTest] cost 428ms
    }

    @Test
    void mapperDeleteTest() throws Exception{
        long start = System.currentTimeMillis();
        List<Integer> ids = teamMapper.selectAllTeamId();
        teamMapper.deleteTeamByTeamIds(ids);
        long end = System.currentTimeMillis();
        logPerf(log, "mapperDeleteTest", start, end); // [deleteInTest] cost 21112ms
    }
}
