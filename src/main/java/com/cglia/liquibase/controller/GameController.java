package com.cglia.liquibase.controller;

import java.util.List;

import com.cglia.liquibase.entiry.Games;
import com.cglia.liquibase.service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController {
	
	@Autowired
	GameService gameService;
	
	@GetMapping("/getall")
	public List<Games> getall() {
		return gameService.getAllGames();
	}
}
