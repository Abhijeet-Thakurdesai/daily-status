package com.status.factory.impl;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.status.dao.TeamDao;
import com.status.dao.UserDao;
import com.status.error.ErrorUtil;
import com.status.error.StatusErrorCode;
import com.status.events.UserDetail;
import com.status.exception.TeamModuleException;
import com.status.factory.UserFactory;
import com.status.model.Team;
import com.status.model.User;

public class UserFactoryImpl implements UserFactory {

	@Autowired
	private UserDao userDao;

	@Autowired
	private TeamDao teamDao;

	@Autowired
	private ErrorUtil errorCode;

	@Override
	public User createUser(UserDetail detail) throws TeamModuleException {
		User user = new User();
		user.setId(detail.getId());
		setFirstName(detail, user);
		setLastName(detail, user);
		setEmailId(detail, user);
		user.setEmailId(detail.getEmailId());
		
		return null;
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
		isValidTeam(detail.getTeamWhereMembers());

		Set<Team> teams = null;
		for (Long id : detail.getTeamWhereMembers()) {
			teams.add(teamDao.getTeam(id));
		}

		user.setTeamWhereMembers(teams);
	}

	private void setTeamWhereLeads(UserDetail detail, User user) throws TeamModuleException {
		isEmptyTeamLeadAndMember(detail);
		isValidTeam(detail.getTeamWhereMembers());

		Set<Team> teams = null;
		for (Long id : detail.getTeamWhereLeads()) {
			teams.add(teamDao.getTeam(id));
		}

		user.setTeamWhereLeads(teams);
	}

	private void isEmptyTeamLeadAndMember(UserDetail detail) throws TeamModuleException {
		if (CollectionUtils.isEmpty(detail.getTeamWhereLeads()) &&
				CollectionUtils.isEmpty(detail.getTeamWhereMembers())) {
			throw new TeamModuleException(errorCode.getError(StatusErrorCode.team_required));
		}
	}


	private void isValidTeam(Set<Long> ids) throws TeamModuleException {
		for (Long teamId : ids) {
			if (!teamDao.isExist(teamId)) {
				throw new TeamModuleException(errorCode.getError(StatusErrorCode.invalid_team));
			}
		}
	}
}
