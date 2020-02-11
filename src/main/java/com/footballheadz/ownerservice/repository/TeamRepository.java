package com.footballheadz.ownerservice.repository;

import com.footballheadz.ownerservice.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
