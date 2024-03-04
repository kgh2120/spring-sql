package com.kk.jpaqueryperformance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer age;

    public Member(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Team team;

    public void associatedWithTeam(Team team) {
        this.team = team;
    }
}
