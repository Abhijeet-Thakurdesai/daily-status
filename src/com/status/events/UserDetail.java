package com.status.events;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.status.dao.TeamDao;
import com.status.model.Team;
import com.status.model.User;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class UserDetail {
	private Long id;

	private String firstName;

	private String lastName;

	private String emailId;

	private String companyName;

	private Set<String> teamWhereLeads;

	private Set<String> teamWhereMembers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Set<String> getTeamWhereLeads() {
		return teamWhereLeads;
	}

	public void setTeamWhereLeads(Set<String> teamWhereLeads) {
		this.teamWhereLeads = teamWhereLeads;
	}

	public Set<String> getTeamWhereMembers() {
		return teamWhereMembers;
	}

	public void setTeamWhereMembers(Set<String> teamWhereMembers) {
		this.teamWhereMembers = teamWhereMembers;
	}
	
	public static UserDetail from(User user) {
		Team team = null;
		UserDetail detail = new UserDetail();
		detail.setId(user.getId());
		detail.setFirstName(user.getFirstName());
		detail.setLastName(user.getLastName());
		detail.setEmailId(user.getEmailId());
		if (!CollectionUtils.isEmpty(user.getTeamWhereLeads())) {
			team = user.getTeamWhereLeads().iterator().next();
		} else {
			team = user.getTeamWhereMembers().iterator().next();
		}

		detail.setCompanyName(team.getCompany().getName());
		detail.setTeamWhereLeads(!CollectionUtils.isEmpty(user.getTeamWhereLeads()) ?
				getTeamsName(user.getTeamWhereLeads()) : null);
		detail.setTeamWhereMembers(!CollectionUtils.isEmpty(user.getTeamWhereMembers()) ?
				getTeamsName(user.getTeamWhereMembers()) : null);
		return detail;
	}

	private static Set<String> getTeamsName(Set<Team> teams) {
		Set<String> teamNames = new HashSet<>();
		for (Team team :teams) {
			teamNames.add(team.getName());
		}
		return teamNames;
	}

}