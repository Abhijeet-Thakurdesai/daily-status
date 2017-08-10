package com.status.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
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
		Query query= sessionFactory.getCurrentSession().createQuery("from Team where company.id=:companyId and name=:teamName").setParameter("companyId", companyId ).setParameter("teamName", teamName);
		return (Team) query.uniqueResult();
	}
	
	@Override
	public Team getTeam(String email) {
		Query query= sessionFactory.getCurrentSession().createQuery("from Team where recipientalias=:email").setParameter("email", email );
		return (Team) query.uniqueResult();
	}
	
	
	@Override
	public boolean isExist(Long id,String name) {
		Team team = getTeam(id, name);
		return team != null ? true :false;
	}

	
	
	@Override
	public Set<Team> getTeamsByIds(Set<Long> teamId) {
		Set<Team> teams = new HashSet<>();
		for (Long id : teamId) {
			teams.add(getTeam(id));
		}
		return teams;
	}

	@Override
	public String getCompanyNameById(Long teamId) {
		return getTeam(teamId).getCompany().getName();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Team> getTeams() {
		// TODO Auto-generated method stub		
		Query query = sessionFactory.getCurrentSession().createQuery("from Team");
		return query.list();
	}
}
