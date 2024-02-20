package com.kk.jpaqueryperformance.repository;

import java.util.List;

import com.kk.jpaqueryperformance.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member
        , Integer> {



	int deleteByIdIn(List<Integer> ids);


	@Query("delete from Member m where m.team.id in :teamIds")
	@Modifying
	void deleteAllByTeam_IdIn(List<Integer> teamIds);
}
