package com.kk.jpaqueryperformance.delete;

import com.kk.jpaqueryperformance.entity.Member;
import com.kk.jpaqueryperformance.entity.Team;
import com.kk.jpaqueryperformance.repository.MemberRepository;
import com.kk.jpaqueryperformance.repository.TeamRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static com.kk.jpaqueryperformance.PerformanceLogger.logPerf;

import java.util.List;

/**
 *
     [delete by cascade] cost 1255ms
     [deleteQueryImprove] cost 327ms
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
        for (int i = 0; i < 10; i++) {
            Team team = new Team("팀" + i);
            em.persist(team);
            for (int j = 0; j < 1000; j++) {
                Member member = new Member("회원" + j, 25);
                member.associatedWithTeam(team);
                em.persist(member);
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
        logPerf(log, "delete by cascade", start, end); // [delete by cascade] cost 548ms
        // TEAM N, 각 팀에 멤버가 M개 있다고 할 경우
        /*
         * Team을 Select   1
         * Team과 연관된 Member를 Select  N
         * Member의 ID를 기준으로 delete  M
         *
         * 총 N * (M + 1) + 1번 쿼리가 나간다.
         */

        /*
        Hibernate: select t1_0.id,t1_0.created_at,t1_0.name,t1_0.updated_at from team t1_0
    Hibernate: select m1_0.team_id,m1_0.id,m1_0.age,m1_0.created_at,m1_0.name,m1_0.updated_at from member m1_0 where m1_0.team_id=?
    select m1_0.team_id,m1_0.id,m1_0.age,m1_0.created_at,m1_0.name,m1_0.updated_at from member m1_0 where m1_0.team_id=?
                ...
               Hibernate: delete from member where id=?
                Hibernate: delete from member where id=?
                Hibernate: delete from member where id=?
                Hibernate: delete from member where id=?
                ...
                Hibernate: delete from team where id=?
                ... 반복...
         */
    }

    @Test
    void deleteQueryImprove() throws Exception {
        long start = System.currentTimeMillis();

        List<Integer> teamids = teamRepository.findAllIds();
        memberRepository.deleteAllByTeam_IdIn(teamids);
        teamRepository.deleteByIdIn(teamids);
        em.flush();
        long end = System.currentTimeMillis();
        logPerf(log, "deleteQueryImprove", start, end); // [deleteQueryImprove] cost 130ms
        /*
            Hibernate: select t1_0.id from team t1_0
            Hibernate: delete from member where team_id in (?,?,?,?,?,?,?,?,?,?)
            Hibernate: delete from team where id in (?,?,?,?,?,?,?,?,?,?)
         */
    }

}
