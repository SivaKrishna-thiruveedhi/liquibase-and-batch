package com.cglia.liquibase.entiry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Games {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private int numberOfTeams;
	private int numberOfPlayersPerTeam;
	private int totalNumberOfPlayers;
	private int status;
	private String teamOne;
	private String teamTwo;
	private String winner;
		
	public Games() {
		super();
	}
		
	public Games(int id, String name, int numberOfTeams, int numberOfPlayersPerTeam, int totalNumberOfPlayers,
			int status, String teamOne, String teamTwo, String winner) {
		super();
		this.id = id;
		this.name = name;
		this.numberOfTeams = numberOfTeams;
		this.numberOfPlayersPerTeam = numberOfPlayersPerTeam;
		this.totalNumberOfPlayers = totalNumberOfPlayers;
		this.status = status;
		this.teamOne = teamOne;
		this.teamTwo = teamTwo;
		this.winner = winner;
	}
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfTeams() {
		return numberOfTeams;
	}

	public void setNumberOfTeams(int numberOfTeams) {
		this.numberOfTeams = numberOfTeams;
	}

	public int getNumberOfPlayersPerTeam() {
		return numberOfPlayersPerTeam;
	}

	public void setNumberOfPlayersPerTeam(int numberOfPlayersPerTeam) {
		this.numberOfPlayersPerTeam = numberOfPlayersPerTeam;
	}

	public int getTotalNumberOfPlayers() {
		return totalNumberOfPlayers;
	}

	public void setTotalNumberOfPlayers(int totalNumberOfPlayers) {
		this.totalNumberOfPlayers = totalNumberOfPlayers;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTeamOne() {
		return teamOne;
	}

	public void setTeamOne(String teamOne) {
		this.teamOne = teamOne;
	}

	public String getTeamTwo() {
		return teamTwo;
	}

	public void setTeamTwo(String teamTwo) {
		this.teamTwo = teamTwo;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

}
