package com.status.dao;

import com.status.model.Team;

public interface TeamDao {
	
	void saveOrUpdate(Team team);
	
	Team getTeam(Long id);
}
