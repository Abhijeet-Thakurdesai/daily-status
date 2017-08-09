package com.status.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TEAMS")
public class Team {
	@Id
	@Column(name = "IDENTIFIER")
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@Column(name = "NAME")
	private String name;

	@OneToOne
	@JoinColumn(name = "COMP_ID")
	private Company company;

	@Column(name = "ALIAS", unique = true)
	private String alias;

	@OneToMany
	private Set<Status> statuslist = new HashSet<>();
	
	
	
	
	public Set<Status> getStatuslist() {
		return statuslist;
	}

	public void setStatuslist(Set<Status> statuslist) {
		this.statuslist = statuslist;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
            name = "TEAM_LEADS",
            joinColumns = @JoinColumn(name = "TEAM_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
	private Set<User> leads;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
            name = "TEAM_MEMBERS",
            joinColumns = @JoinColumn(name = "TEAM_ID" ),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Set<User> getLeads() {
		return leads;
	}

	public void setLeads(Set<User> leads) {
		this.leads = leads;
	}

	public Set<User> getMembers() {
		return members;
	}

	public void setMembers(Set<User> members) {
		this.members = members;
	}

}
