package com.kk.jpaqueryperformance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class MemberEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer age;

    public MemberEntity(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = FetchType.LAZY)
    // @OnDelete(action = OnDeleteAction.CASCADE)
    private TeamEntity team;

    public void associatedWithTeam(TeamEntity teamEntity) {
        this.team = teamEntity;
    }
}
