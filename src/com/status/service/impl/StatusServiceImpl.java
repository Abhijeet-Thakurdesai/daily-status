package com.status.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.status.dao.StatusDao;
import com.status.dao.TeamDao;
import com.status.domain.ErrorUtil;
import com.status.domain.StatusErrorCode;
import com.status.events.TeamDetail;
import com.status.exception.TeamModuleException;
import com.status.factory.TeamFactory;
import com.status.model.Status;
import com.status.model.Team;
import com.status.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService {
	HttpServletResponse response;

	@Autowired
	private ErrorUtil errorCode;

	@Autowired
	private StatusDao statusDao;

	@Autowired
	private TeamDao teamDao;

	@Autowired
	private TeamFactory teamFactory;

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
			if (StringUtils.isBlank(team.getName()))  {
				throw new TeamModuleException("Team Name can not be blank or Empty");
			}
			if (StringUtils.isBlank(team.getCompanyName())) {
				throw new TeamModuleException("Company name can not be blank or Empty");
			}
			if (StringUtils.isBlank(team.getAlias())) {
				throw new TeamModuleException("alias can not be blank or Empty");
			}
			if (CollectionUtils.isEmpty(team.getMembers()) || CollectionUtils.isEmpty(team.getLeaders())) {
				throw new TeamModuleException("Team must have at least one member or leader");
			}
			Team newTeam = teamFactory.createTeam(team);
			teamDao.saveOrUpdate(newTeam);
			return TeamDetail.from(newTeam);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
