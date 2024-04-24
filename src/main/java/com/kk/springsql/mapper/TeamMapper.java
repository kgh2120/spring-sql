package com.kk.springsql.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;

import com.kk.springsql.entity.Team;

@Mapper
public interface TeamMapper {

	@Insert("insert into Team (name) values (#{name})")
	void insertTeamDirect(Team team);

	void insertTeamXML(Team team);

	@InsertProvider(type = TeamProvider.class, method = "insertQuery")
	void insertTeamProvider(Team team);

}
