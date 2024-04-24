package com.kk.springsql.repository;

import java.util.List;

import com.kk.springsql.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<MemberEntity
        , Integer> {



	int deleteByIdIn(List<Integer> ids);


	@Query("delete from MemberEntity m where m.team.id in :teamIds")
	@Modifying
	void deleteAllByTeam_IdIn(List<Integer> teamIds);

	@Query("select m.id from MemberEntity m where m.team.id in :teamids")
	List<Integer> findAllIdsByTeamIds(List<Integer> teamids);
}
