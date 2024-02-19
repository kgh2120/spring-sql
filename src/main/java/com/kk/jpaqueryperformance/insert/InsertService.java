package com.kk.jpaqueryperformance.insert;

import com.kk.jpaqueryperformance.entity.Member;
import com.kk.jpaqueryperformance.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class InsertService {

    public static final String INSERT_MEMBER_QUERY = "insert into member (name, age, created_at, updated_at) values (?,?,?,?)";
    public static final int NUMBER_OF_INSERT = 100000;
    private final MemberRepository memberRepository;
    private final JdbcTemplate jdbcTemplate;
    private final EntityManager em;

    public void insertEntityManagerPersist() {
        for (int i = 0; i < NUMBER_OF_INSERT; i++) {
            em.persist(new Member("회원", 20));
        }
    }

    public void insertDataJpaSave() {
        for (int i = 0; i < NUMBER_OF_INSERT; i++) {
            memberRepository.save(new Member("회원", 20));
        }
    }

    public void insertDataJpaSaveAll() {
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_INSERT; i++) {
            members.add(new Member("회원", 20));
        }
        memberRepository.saveAll(members);
    }

    public void insertJdbcTemplate() {
        for (int i = 0; i < NUMBER_OF_INSERT; i++) {
            LocalDateTime now = LocalDateTime.now();
            jdbcTemplate.update(INSERT_MEMBER_QUERY,
                    "회원", 20, now, now);
        }
    }

    public void insertBulk() {
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_INSERT; i++) {
            members.add(new Member("회원", 20));
        }

        jdbcTemplate.batchUpdate(INSERT_MEMBER_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Date date = new Date(Clock.systemDefaultZone().millis());
                Member member = members.get(i);
                ps.setString(1, member.getName());
                ps.setInt(2, member.getAge());
                ps.setDate(3, date);
                ps.setDate(4, date);
            }

            @Override
            public int getBatchSize() {
                return members.size();
            }
        });

    }

}
