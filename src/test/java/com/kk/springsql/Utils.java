package com.kk.springsql;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import jakarta.persistence.EntityManager;

public class Utils {
// [DataJpa SaveAndFlush] N -> 100 :  cost 107ms
// [DataJpa SaveAndFlush] N -> 1000 :  cost 378ms
// [DataJpa SaveAndFlush] N -> 10000 :  cost 8383ms
// [JdbcTemplate BulkUpdate] N -> 100 :  cost 5ms
// [JdbcTemplate BulkUpdate] N -> 1000 :  cost 4ms
// [JdbcTemplate BulkUpdate] N -> 10000 :  cost 28ms
// [JdbcTemplate BulkUpdate] N -> 100000 :  cost 174ms
// [JdbcTemplate BulkUpdate] N -> 500000 :  cost 633ms
// [Mybatis Annotation] N -> 10000 :  cost 74ms
// [Mybatis Annotation] N -> 10000 :  cost 37ms
// [Mybatis Annotation] N -> 10000 :  cost 35ms
// [Mybatis Annotation] N -> 10000 :  cost 27ms
// [Mybatis Annotation] N -> 10000 :  cost 26ms
// [SimpleJdbcInsert Execute] N -> 100 :  cost 7ms
// [SimpleJdbcInsert Execute] N -> 1000 :  cost 7ms
// [SimpleJdbcInsert Execute] N -> 10000 :  cost 25ms
// [SimpleJdbcInsert Execute] N -> 100000 :  cost 235ms
// [SimpleJdbcInsert Execute] N -> 500000 :  cost 805ms
// [EntityManager Persist] N -> 100 :  cost 4ms
// [EntityManager Persist] N -> 1000 :  cost 31ms
// [EntityManager Persist] N -> 10000 :  cost 216ms
// [EntityManager Persist] N -> 100000 :  cost 1926ms
// [EntityManager Persist] N -> 500000 :  cost 9537ms
// [JdbcTemplate update] N -> 100 :  cost 2ms
// [JdbcTemplate update] N -> 1000 :  cost 3ms
// [JdbcTemplate update] N -> 10000 :  cost 22ms
// [JdbcTemplate update] N -> 100000 :  cost 167ms
// [JdbcTemplate update] N -> 500000 :  cost 801ms
// [NamedParameterJdbcTemplate BulkUpdate] N -> 100 :  cost 3ms
// [NamedParameterJdbcTemplate BulkUpdate] N -> 1000 :  cost 3ms
// [NamedParameterJdbcTemplate BulkUpdate] N -> 10000 :  cost 15ms
// [NamedParameterJdbcTemplate BulkUpdate] N -> 100000 :  cost 149ms
// [NamedParameterJdbcTemplate BulkUpdate] N -> 500000 :  cost 797ms
// [PreparedStatementCreator] N -> 100 :  cost 1ms
// [PreparedStatementCreator] N -> 1000 :  cost 2ms
// [PreparedStatementCreator] N -> 10000 :  cost 19ms
// [PreparedStatementCreator] N -> 100000 :  cost 148ms
// [PreparedStatementCreator] N -> 500000 :  cost 786ms
// [DataJpa SaveAllAndFlush] N -> 100 :  cost 4ms
// [DataJpa SaveAllAndFlush] N -> 1000 :  cost 22ms
// [DataJpa SaveAllAndFlush] N -> 10000 :  cost 222ms
// [QueryDSL Insert] N -> 100 :  cost 94ms
// [QueryDSL Insert] N -> 1000 :  cost 120ms
// [QueryDSL Insert] N -> 10000 :  cost 369ms
// [QueryDSL Insert] N -> 100000 :  cost 1054ms
// [QueryDSL Insert] N -> 500000 :  cost 3196ms
// [Mybatis XML] N -> 100 :  cost 2ms
// [Mybatis XML] N -> 1000 :  cost 4ms
// [Mybatis XML] N -> 10000 :  cost 25ms
// [Mybatis XML] N -> 100000 :  cost 193ms
// [Mybatis XML] N -> 500000 :  cost 991ms
// [NamedParameterJdbcTemplate - Map] N -> 100 :  cost 1ms
// [NamedParameterJdbcTemplate - Map] N -> 1000 :  cost 4ms
// [NamedParameterJdbcTemplate - Map] N -> 10000 :  cost 24ms
// [NamedParameterJdbcTemplate - Map] N -> 100000 :  cost 200ms
// [NamedParameterJdbcTemplate - Map] N -> 500000 :  cost 925ms
// [NamedParameterJdbcTemplate - BeanPropertySqlParameterSource] N -> 100 :  cost 5ms
// [NamedParameterJdbcTemplate - BeanPropertySqlParameterSource] N -> 1000 :  cost 9ms
// [NamedParameterJdbcTemplate - BeanPropertySqlParameterSource] N -> 10000 :  cost 62ms
// [NamedParameterJdbcTemplate - BeanPropertySqlParameterSource] N -> 100000 :  cost 251ms
// [NamedParameterJdbcTemplate - BeanPropertySqlParameterSource] N -> 500000 :  cost 1015ms
// [Mybatis Provider] N -> 100 :  cost 6ms
// [Mybatis Provider] N -> 1000 :  cost 15ms
// [Mybatis Provider] N -> 10000 :  cost 64ms
// [Mybatis Provider] N -> 100000 :  cost 384ms
// [Mybatis Provider] N -> 500000 :  cost 1523ms
// [DataJpa Save] N -> 100 :  cost 4ms
// [DataJpa Save] N -> 1000 :  cost 29ms
// [DataJpa Save] N -> 10000 :  cost 290ms
// [DataJpa Save] N -> 100000 :  cost 2270ms
// [DataJpa Save] N -> 500000 :  cost 11472ms
// [DataJpa SaveAll] N -> 100 :  cost 4ms
// [DataJpa SaveAll] N -> 1000 :  cost 21ms
// [DataJpa SaveAll] N -> 10000 :  cost 215ms
// [DataJpa SaveAll] N -> 100000 :  cost 2151ms
// [DataJpa SaveAll] N -> 500000 :  cost 10972ms
}
