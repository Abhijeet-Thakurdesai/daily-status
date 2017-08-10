package com.status.service;

import java.util.Date;
import java.util.List;

import com.status.events.TeamDetail;
import com.status.events.UserDetail;
import com.status.model.Company;
import com.status.model.Status;
import com.status.model.Team;
import com.status.model.User;

public interface StatusService {
	
	List<Status> getStatus(Date date,Team team);

	Status addStatus(Status status);

	TeamDetail createTeam(TeamDetail team);

	UserDetail createUser(UserDetail user);
	
	Company createCompany(Company company);
	
	Team getTeam(String email);
	
	User getUser(String email);
	
	List<Team> getTeams();
	
	void check();

}