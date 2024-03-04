package com.kk.jpaqueryperformance.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    @Delete("delete from Member m where m.team_id in #{ids}")
    void deleteMemberByTeamIds(List<Integer> ids);
}
