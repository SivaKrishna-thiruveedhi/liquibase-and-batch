package com.cglia.liquibase.repo;

import com.cglia.liquibase.entiry.Games;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepo extends JpaRepository<Games, Integer> {

}
