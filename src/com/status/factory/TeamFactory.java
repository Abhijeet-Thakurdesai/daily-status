package com.status.factory;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.status.dao.ComapnyDao;
import com.status.events.TeamDetail;
import com.status.exception.TeamModuleException;
import com.status.model.Company;
import com.status.model.Team;

public class TeamFactory {

	@Autowired
	private ComapnyDao companyDao;

	public ComapnyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(ComapnyDao companyDao) {
		this.companyDao = companyDao;
	}

	public Team createTeam(TeamDetail detail) throws TeamModuleException {
		Team newTeam = new Team();
		setName(detail, newTeam);
		setCompany(detail, newTeam);
		setAlias(detail, newTeam);
		setTeamLeaders(detail, newTeam);
		setTeamMembers(detail, newTeam);
		return newTeam;
	}

	private void setCompany(TeamDetail detail,Team newTeam) throws TeamModuleException {
		if (StringUtils.isBlank(detail.getCompanyName())) {
			throw new TeamModuleException("Company name can Not be blank or Empty");
		}

		Company company = companyDao.getCompanyByName(detail.getCompanyName());
		if (company == null) {
			throw new TeamModuleException("Company name "+ detail.getCompanyName() +" does not exist");
		}

		newTeam.setCompany(company);
	}

	private void setName(TeamDetail detail,Team newTeam) throws TeamModuleException {
		if (StringUtils.isBlank(detail.getName())) {
			throw new TeamModuleException("Team Name can Not be blank or Empty");
		}

		newTeam.setName(detail.getName());
	}

	private void setAlias(TeamDetail detail,Team newTeam) throws TeamModuleException {
		if (StringUtils.isBlank(detail.getAlias())) {
			throw new TeamModuleException("Team alias name can Not be blank or Empty");
		}
		newTeam.setAlias(detail.getAlias());
	}

	private void setTeamLeaders(TeamDetail detail, Team newTeam) throws TeamModuleException {
		if (CollectionUtils.isEmpty(detail.getLeaders())) {
			throw new TeamModuleException("Team must have at least one Leader");
		}

		newTeam.setLeads(detail.getLeaders());
	}

	private void setTeamMembers(TeamDetail detail, Team newTeam) throws TeamModuleException {
		if (CollectionUtils.isEmpty(detail.getMembers())) {
			throw new TeamModuleException("Team must have at least one Member");
		}

		newTeam.setMembers(detail.getMembers());
	}
 }
