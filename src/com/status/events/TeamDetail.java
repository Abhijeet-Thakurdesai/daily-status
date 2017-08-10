package com.status.events;

import java.util.Set;
import com.status.model.Team;
import com.status.model.User;

public class TeamDetail {
	private Long id;

	private String name;

	private String companyName;

	private String recipientalias;
	
	private String senderalias;
	

	private Set<User> leaders;

	private Set<User> members;

	
	


	public String getRecipientalias() {
		return recipientalias;
	}

	public void setRecipientalias(String recipientalias) {
		this.recipientalias = recipientalias;
	}

	public String getSenderalias() {
		return senderalias;
	}

	public void setSenderalias(String senderalias) {
		this.senderalias = senderalias;
	}

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
		detail.setRecipientalias(team.getRecipientAlias());
		detail.setSenderalias(team.getSenderAlias());
		detail.setCompanyName(team.getCompany().getName());
		detail.setLeaders(team.getLeads());
		detail.setMembers(team.getMembers());

		return detail;
	}

}
