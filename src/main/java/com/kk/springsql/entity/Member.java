package com.kk.springsql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	private Integer id;
	private String name;
	private Integer age;

	private Integer teamId;
}
