package com.kk.jpaqueryperformance.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Team extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member> members;
}
