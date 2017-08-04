package com.status.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.status.dao.StatusDao;
import com.status.dao.TeamDao;
import com.status.error.ErrorUtil;
import com.status.error.StatusErrorCode;
import com.status.events.TeamDetail;
import com.status.events.UserDetail;
import com.status.factory.impl.TeamFactory;
import com.status.model.Status;
import com.status.model.Team;
import com.status.model.User;
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
			Team newTeam = teamFactory.createTeam(team);
			teamDao.saveOrUpdate(newTeam);
			return TeamDetail.from(newTeam);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public User createUser(UserDetail user) {
		try {
			User user = 
		} catch (Exception e) {
			
		}
		return null;
	}
}
