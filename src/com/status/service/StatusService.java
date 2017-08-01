package com.status.service;

import java.util.Date;
import java.util.List;

import com.status.events.TeamDetail;
import com.status.model.Status;
import com.status.model.Team;

public interface StatusService {
	
	List<Status> getStatus(Date date);

	Status addStatus(Status status);

	TeamDetail createTeam(TeamDetail team);

}