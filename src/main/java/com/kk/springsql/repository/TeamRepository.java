package com.kk.springsql.repository;

import java.util.List;

import com.kk.springsql.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {

	@Query("select t.id from TeamEntity t")
	List<Integer> findAllIds();

	@Query("delete from TeamEntity t where t.id in :ids")
	@Modifying
	int deleteByIdIn(List<Integer> ids);
}
