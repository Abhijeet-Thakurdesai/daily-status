package com.status.dao.impl;

import java.util.Set;

import org.h2.engine.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.status.dao.TeamDao;
import com.status.model.Team;

@Repository("teamDao")
public class TeamDaoImpl implements TeamDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveOrUpdate(Team team) {
		sessionFactory.getCurrentSession().saveOrUpdate(team);
	}

	@Override
	public Team getTeam(Long id) {
		return (Team) sessionFactory.getCurrentSession().get(Team.class,id);
	}

	@Override
	public Team getTeam(Long companyId, String teamName) {
		Query query= sessionFactory.getCurrentSession().createQuery("from Team where company.id=:companyId and name=:teamName").setParameter("companyId", companyId ).setParameter("teamName", teamName );
		return (Team) query.uniqueResult();
	}
	@Override
	public boolean isExist(Long id) {
		Team team = getTeam(id);
		return team != null ? true :false;
	}

	@Override
	public Set<Team> getTeamsByIds(Set<Long> teamId) {
		Set<Team> teams = null;
		for (Long id : teamId) {
			teams.add(getTeam(id));
		}
		return teams;
	}

}
