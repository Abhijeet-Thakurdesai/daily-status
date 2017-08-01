package com.status.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.status.dao.TeamDao;
import com.status.model.Team;

@Repository("teamDao")
public class TeamDaoImpl implements TeamDao{
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
}
