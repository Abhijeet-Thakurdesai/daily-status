package com.status.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TEAMS")
public class Team {
	@Id
	@Column(name = "IDENTIFIER")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(targetEntity = Company.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "COMP_ID")
	private Set<Company> company = new HashSet<Company>();
	
	@Column(name = "NAME")
	private String name;
   
	@OneToMany(mappedBy = "user")
	@JoinColumn(name = "USER_ID")
	private Set<UserTeam> userTeams = new HashSet<UserTeam>();
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Set<Company> getCompany() {
		return company;
	}

	public void setCompany(Set<Company> company) {
		this.company = company;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Set<UserTeam> getUserTeams() {
		return userTeams;
	}

	public void setUserTeams(Set<UserTeam> userTeams) {
		this.userTeams = userTeams;
	}
}
