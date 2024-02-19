package com.kk.jpaqueryperformance.repository;

import com.kk.jpaqueryperformance.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
