package com.kk.springsql.mapper;

import org.apache.ibatis.jdbc.SQL;

import com.kk.springsql.entity.Team;

public class TeamProvider {

	public String insertQuery(){
		return new SQL().INSERT_INTO("team")
			.VALUES("name", "#{name}")
			.toString();
	}
}
