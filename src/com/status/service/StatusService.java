package com.status.service;

import java.util.Date;
import java.util.List;

import com.status.events.TeamDetail;
import com.status.events.UserDetail;
import com.status.model.Status;
import com.status.model.Team;
import com.status.model.User;

public interface StatusService {
	
	List<Status> getStatus(Date date);

	Status addStatus(Status status);

	TeamDetail createTeam(TeamDetail team);

	User createUser(UserDetail user);

}