package com.kk.springsql.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class BeanConfig {

	private final DataSource dataSource;
	private final EntityManager em;
	@Bean
	public SimpleJdbcInsert simpleJdbcInsert(){
		return new SimpleJdbcInsert(dataSource).withTableName("team")
			.usingGeneratedKeyColumns("id");
	}
	@Bean
	public JPAQueryFactory jpaQueryFactory(){
		return new JPAQueryFactory(em);
	}
}
