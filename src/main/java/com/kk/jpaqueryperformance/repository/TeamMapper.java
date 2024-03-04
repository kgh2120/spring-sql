package com.kk.jpaqueryperformance.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeamMapper {

    @Select("select t.id from Team t")
    List<Integer> selectAllTeamId();

    @Delete("delete from Team t where t.id in #{ids}")
    void deleteTeamByTeamIds(List<Integer> ids);
}
