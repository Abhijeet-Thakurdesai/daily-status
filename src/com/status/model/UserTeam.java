package com.status.model;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_TEAMS")
public class UserTeam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDENTIFIER")
	private Long id;

	@ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@ManyToOne(targetEntity = Team.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "TEAM_ID")
	private Team team;
	
	@Column(name = "IS_LEAD")
	private boolean isLead;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public boolean isLead() {
		return isLead;
	}

	public void setLead(boolean isLead) {
		this.isLead = isLead;
	}
	
}
