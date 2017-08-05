package com.status.dao;

import java.util.Set;

import com.status.model.Team;

public interface TeamDao {

	void saveOrUpdate(Team team);

	Team getTeam(Long id);

	Team getTeam(Long companyId, String teamName);

	boolean isExist(Long id, String companyName);

	Set<Team> getTeamsByIds(Set<Long> teamId);

	String getCompanyNameById(Long teamId);


}
