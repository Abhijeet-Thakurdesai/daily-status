package com.status.events;

import java.util.Set;
import com.status.model.Team;
import com.status.model.User;

public class TeamDetail {
	private Long id;

	private String name;
	
	private String companyName;

	private String alias;

	private Set<User> leaders;

	private Set<User> members;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Set<User> getMembers() {
		return members;
	}

	public void setMembers(Set<User> members) {
		this.members = members;
	}

	public Set<User> getLeaders() {
		return leaders;
	}

	public void setLeaders(Set<User> leaders) {
		this.leaders = leaders;
	}

	public static TeamDetail from(Team team) {
		TeamDetail detail = new TeamDetail();

		detail.setId(team.getId());
		detail.setName(team.getName());
		detail.setAlias(team.getAlias());
		detail.setCompanyName(team.getCompany().getName());
		detail.setLeaders(team.getLeads());
		detail.setMembers(team.getMembers());

		return detail;
	}

}
