package com.status.dao;

import java.util.Set;

import com.status.model.Team;

public interface TeamDao {

	void saveOrUpdate(Team team);

	Team getTeam(Long id);

	Team getTeam(String companyName, String TeamName);

	Team getTeam(Long companyId, String teamName);

	Set<Team> getTeamsByIds(Set<Long> teamId);

	Team getTeamByAlias(String alias);

	boolean isExist(Long id, String companyName);
}
