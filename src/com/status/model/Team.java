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

	@Column(name = "RECIPIENTALIAS", unique = true)
	private String recipientAlias;
	
	@Column(name = "SENDERALIAS", unique = true)
	private String senderAlias;
	



	public String getRecipientAlias() {
		return recipientAlias;
	}

	public void setRecipientAlias(String recipientAlias) {
		this.recipientAlias = recipientAlias;
	}

	public String getSenderAlias() {
		return senderAlias;
	}

	public void setSenderAlias(String senderAlias) {
		this.senderAlias = senderAlias;
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
