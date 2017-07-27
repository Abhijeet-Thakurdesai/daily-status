package com.status.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDENTIFIER")
	private Long id;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL_ID", unique = true)
	private String emailId;

	@OneToMany(mappedBy = "members", cascade = CascadeType.ALL)
	private Set<Team> teamMembers = new HashSet<Team>();

	@OneToMany(mappedBy = "leads", cascade = CascadeType.ALL)
	private Set<Team> teamLeads = new HashSet<Team>();

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

	public Set<Team> getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(Set<Team> teamMembers) {
		this.teamMembers = teamMembers;
	}

	public Set<Team> getTeamLeads() {
		return teamLeads;
	}

	public void setTeamLeads(Set<Team> teamLeads) {
		this.teamLeads = teamLeads;
	}

}
