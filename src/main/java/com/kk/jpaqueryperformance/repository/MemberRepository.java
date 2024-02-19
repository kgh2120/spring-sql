package com.kk.jpaqueryperformance.repository;

import com.kk.jpaqueryperformance.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member
        , Integer> {
}
