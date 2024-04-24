package com.kk.springsql.mapper;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.kk.springsql.entity.Team;

@SpringBootTest
class TeamMapperTest {

	@Autowired
	TeamMapper teamMapper;

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Test
	void insertXMLTest() throws Exception{
	// given

		Team team = new Team();
		team.setName("team1");
		// when
		teamMapper.insertTeamXML(team);
		// then
		List<Team> result = jdbcTemplate.query("select * from team", new RowMapper<Team>() {
			@Override
			public Team mapRow(ResultSet rs, int rowNum) throws SQLException {

				Team t = new Team();
				t.setId(rs.getInt(1));
				t.setName(rs.getString(2));

				return t;
			}
		});
		Assertions.assertThat(result.size()).isSameAs(1);
	}

	@Test
	void insertProviderTest() throws Exception{
		// given

		Team team = new Team();
		team.setName("team1");
		// when
		teamMapper.insertTeamProvider(team);
		// then
		List<Team> result = jdbcTemplate.query("select * from team", new RowMapper<Team>() {
			@Override
			public Team mapRow(ResultSet rs, int rowNum) throws SQLException {

				Team t = new Team();
				t.setId(rs.getInt(1));
				t.setName(rs.getString(2));

				return t;
			}
		});
		Assertions.assertThat(result.size()).isSameAs(1);
	}
	@Test
	void insertDirectTest() throws Exception{
		// given

		Team team = new Team();
		team.setName("team1");
		// when
		teamMapper.insertTeamDirect(team);
		// then
		List<Team> result = jdbcTemplate.query("select * from team", new RowMapper<Team>() {
			@Override
			public Team mapRow(ResultSet rs, int rowNum) throws SQLException {

				Team t = new Team();
				t.setId(rs.getInt(1));
				t.setName(rs.getString(2));

				return t;
			}
		});
		Assertions.assertThat(result.size()).isSameAs(1);
	}
}