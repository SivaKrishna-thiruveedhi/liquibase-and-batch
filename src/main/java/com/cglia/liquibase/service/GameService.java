package com.cglia.liquibase.service;

import java.util.List;

import com.cglia.liquibase.entiry.Games;
import com.cglia.liquibase.repo.GameRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
	
	@Autowired
	GameRepo gameRepo;

	public List<Games> getAllGames() {
		return gameRepo.findAll();
	}
}
