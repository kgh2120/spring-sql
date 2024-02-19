package com.kk.jpaqueryperformance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Getter
@Entity
public class Team extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE)
    private List<Member> members;

    public Team(String name) {
        this.name = name;
    }
}
