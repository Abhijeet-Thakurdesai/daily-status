package com.status.factory.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.status.dao.CompanyDao;
import com.status.dao.TeamDao;
import com.status.dao.UserDao;
import com.status.error.ErrorUtil;
import com.status.error.StatusErrorCode;
import com.status.events.UserDetail;
import com.status.exception.TeamModuleException;
import com.status.factory.UserFactory;
import com.status.model.Company;
import com.status.model.Team;
import com.status.model.User;

public class UserFactoryImpl implements UserFactory {
	@Autowired
	private UserDao userDao;

	@Autowired
	private TeamDao teamDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private ErrorUtil errorCode;

	@Override
	public User createUser(UserDetail detail) throws TeamModuleException {
		User user = new User();
		setFirstName(detail, user);
		setLastName(detail, user);
		setEmailId(detail, user);
		setCompany(detail);
		isEmptyTeamLeadAndMember(detail);
		if (!CollectionUtils.isEmpty(detail.getTeamWhereLeads())) {
			setTeamWhereLeads(detail, user);
		}
		if (!CollectionUtils.isEmpty(detail.getTeamWhereMembers())) {
			setTeamWhereMember(detail, user);
		}

		return user;
	}

	private void setFirstName(UserDetail detail, User user) throws TeamModuleException {
		if (StringUtils.isBlank(detail.getFirstName())) {
			throw new TeamModuleException(errorCode.getError(StatusErrorCode.first_name_required));
		}
		user.setFirstName(detail.getFirstName());
	}

	private void setLastName(UserDetail detail, User user) throws TeamModuleException {
		if (StringUtils.isBlank(detail.getLastName())) {
			throw new TeamModuleException(errorCode.getError(StatusErrorCode.last_name_required));
		}

		user.setLastName(detail.getLastName());
	}

	private void setEmailId(UserDetail detail, User user) throws TeamModuleException {
		if (userDao.getByEmailId(detail.getEmailId()) != null) {
			throw new TeamModuleException(errorCode.getError(StatusErrorCode.email_id_already_used));
		}

		user.setEmailId(detail.getEmailId());
	}

	private void setTeamWhereMember(UserDetail detail, User user) throws TeamModuleException {
		isEmptyTeamLeadAndMember(detail);

		Long companyId = companyDao.getIdByName(detail.getCompanyName());
		isValidTeam(companyId, detail.getTeamWhereMembers());
		Set<Team> teams = new HashSet<>();
		for (String team : detail.getTeamWhereMembers()) {
			teams.add(teamDao.getTeam(companyId, team));
		}

		user.setTeamWhereMembers(teams);
	}

	private void setTeamWhereLeads(UserDetail detail, User user) throws TeamModuleException {
		isEmptyTeamLeadAndMember(detail);

		Long companyId = companyDao.getIdByName(detail.getCompanyName());
		isValidTeam(companyId, detail.getTeamWhereLeads());
		Set<Team> teams = new HashSet<>();
		for (String team : detail.getTeamWhereLeads()) {
			teams.add(teamDao.getTeam(companyId,team));
		}

		user.setTeamWhereLeads(teams);
	}

	private void isEmptyTeamLeadAndMember(UserDetail detail) throws TeamModuleException {
		if (CollectionUtils.isEmpty(detail.getTeamWhereMembers()) &&
				CollectionUtils.isEmpty(detail.getTeamWhereLeads())) {
			throw new TeamModuleException(errorCode.getError(StatusErrorCode.team_required));
		}
	}

	private void isValidTeam(Long id, Set<String> teams) throws TeamModuleException {
		for (String team : teams) {
			if (!teamDao.isExist(id, team)) {
				throw new TeamModuleException(errorCode.getError(StatusErrorCode.invalid_team, team));
			}
		}
	}

	private void setCompany(UserDetail detail) throws TeamModuleException {
		Company company = companyDao.getCompanyByName(detail.getCompanyName());
		if(company == null) {
			throw new TeamModuleException(errorCode.getError(StatusErrorCode.invalid_company, detail.getCompanyName()));
		}

	}
}
