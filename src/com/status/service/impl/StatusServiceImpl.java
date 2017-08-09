package com.status.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.status.dao.StatusDao;
import com.status.dao.TeamDao;
import com.status.dao.UserDao;
import com.status.error.ErrorUtil;
import com.status.error.StatusErrorCode;
import com.status.events.TeamDetail;
import com.status.events.UserDetail;
import com.status.exception.TeamModuleException;
import com.status.factory.TeamFactory;
import com.status.factory.UserFactory;
import com.status.model.Status;
import com.status.model.Team;
import com.status.model.User;
import com.status.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService {

	@Autowired
	private ErrorUtil errorCode;

	@Autowired
	private StatusDao statusDao;

	@Autowired
	private TeamDao teamDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private TeamFactory teamFactory;

	@Autowired
	private UserFactory userFactory;

	@Transactional
	public List<Status> getStatus(Date date) {
		return statusDao.getStatus(date);

	}

	@Transactional
	public Status addStatus(Status status) {
		if (StringUtils.isBlank(status.getStatus())) {
			status.setStatus(errorCode.getError(StatusErrorCode.invalid_status));
		} else if (StringUtils.isBlank(status.getEmail())) {
			status.setEmail(errorCode.getError(StatusErrorCode.invalid_email));
		} else {
			statusDao.addStatus(status);
		}	
		return status;
	}

	@Transactional
	public TeamDetail createTeam(TeamDetail team) {
		try {
			Team newTeam = new Team();
			newTeam = teamDao.getTeam(team.getCompanyName(), team.getName());
			if (newTeam != null) {
				throw new TeamModuleException(MessageFormat.format(errorCode.getError(StatusErrorCode.team_already_exist), team.getName()));
			}

			newTeam = teamFactory.createTeam(team);
			teamDao.saveOrUpdate(newTeam);
			return TeamDetail.from(newTeam);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	@Transactional
	public UserDetail createUser(UserDetail detail) {
		try {
			User newUser = userFactory.createUser(detail);
			for(Team team :newUser.getTeamWhereLeads()) {
				team.getLeads().add(newUser);
				teamDao.saveOrUpdate(team);
			}
			for(Team team :newUser.getTeamWhereMembers()) {
				team.getMembers().add(newUser);
				teamDao.saveOrUpdate(team);
			}
			userDao.saveOrUpdate(newUser);
			return UserDetail.from(newUser);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
