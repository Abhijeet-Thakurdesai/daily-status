package com.status.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IDENTIFIER")
	private Long id;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL_ID", unique = true)
	private String emailId;

	@ManyToMany(mappedBy = "members", cascade = CascadeType.ALL)
	private Set<Team> teamWhereMembers = new HashSet<>();

	@ManyToMany(mappedBy = "leads", cascade = CascadeType.ALL)
	private Set<Team> teamWhereLeads = new HashSet<>();
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

	public Set<Team> getTeamWhereMembers() {
		return teamWhereMembers;
	}

	public void setTeamWhereMembers(Set<Team> teamWhereMembers) {
		this.teamWhereMembers = teamWhereMembers;
	}

	public Set<Team> getTeamWhereLeads() {
		return teamWhereLeads;
	}

	public void setTeamWhereLeads(Set<Team> teamWhereLeads) {
		this.teamWhereLeads = teamWhereLeads;
	}

}
