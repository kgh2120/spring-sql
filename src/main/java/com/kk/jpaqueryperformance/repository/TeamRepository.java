package com.kk.jpaqueryperformance.repository;

import java.util.List;

import com.kk.jpaqueryperformance.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends JpaRepository<Team, Integer> {

	@Query("select t.id from Team t")
	List<Integer> findAllIds();

	@Query("delete from Team t where t.id in :ids")
	@Modifying
	int deleteByIdIn(List<Integer> ids);
}
