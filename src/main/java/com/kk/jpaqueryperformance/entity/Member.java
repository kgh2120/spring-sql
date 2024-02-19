package com.kk.jpaqueryperformance.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Member extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer age;


    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;
}
