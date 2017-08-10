package com.status.factory.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.status.dao.CompanyDao;
import com.status.error.ErrorUtil;
import com.status.error.StatusErrorCode;
import com.status.events.TeamDetail;
import com.status.exception.TeamModuleException;
import com.status.model.Company;
import com.status.model.Team;

public class TeamFactory {

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private ErrorUtil errorCode;

	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
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
			throw new TeamModuleException(errorCode.getError(StatusErrorCode.company_name_required));
		}

		Company company = companyDao.getCompanyByName(detail.getCompanyName());
		if (company == null) {
			throw new TeamModuleException(errorCode.getError(StatusErrorCode.company_not_found, detail.getCompanyName()));
		}

		newTeam.setCompany(company);
	}

	private void setName(TeamDetail detail,Team newTeam) throws TeamModuleException {
		if (StringUtils.isBlank(detail.getName())) {
			throw new TeamModuleException(errorCode.getError(StatusErrorCode.team_name_required));
		}

		newTeam.setName(detail.getName());
	}

	private void setAlias(TeamDetail detail,Team newTeam) throws TeamModuleException {
		if (StringUtils.isBlank(detail.getRecipientalias())) {
			throw new TeamModuleException(errorCode.getError(StatusErrorCode.alias_name_required));
		}

		newTeam.setRecipientAlias(detail.getRecipientalias());
		newTeam.setSenderAlias(detail.getSenderalias());
	}

	private void setTeamLeaders(TeamDetail detail, Team newTeam) throws TeamModuleException {
		if (CollectionUtils.isEmpty(detail.getLeaders())) {
			throw new TeamModuleException(errorCode.getError(StatusErrorCode.team_leader_required));
		}

		newTeam.setLeads(detail.getLeaders());
	}

	private void setTeamMembers(TeamDetail detail, Team newTeam) throws TeamModuleException {
		if (CollectionUtils.isEmpty(detail.getMembers())) {
			throw new TeamModuleException(errorCode.getError(StatusErrorCode.team_member_required));
		}

		newTeam.setMembers(detail.getMembers());
	}
 }
