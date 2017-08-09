package com.status.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.status.dao.CompanyDao;
import com.status.dao.StatusDao;
import com.status.dao.TeamDao;
import com.status.dao.UserDao;
import com.status.error.ErrorUtil;
import com.status.error.StatusErrorCode;
import com.status.events.TeamDetail;
import com.status.events.UserDetail;
import com.status.factory.UserFactory;
import com.status.factory.impl.TeamFactory;
import com.status.model.Company;
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
	private UserDao userDao;

	@Autowired
	private CompanyDao companyDao;

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
	
	
	@Transactional
	public Company createCompany(Company company) {
		try {
			companyDao.saveOrUpdate(company);
			return company;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	
	@Transactional
	public Team getTeam(String email){
		return teamDao.getTeam(email);
		// for returning team detail
		/*
		try {
			Team newTeam = teamDao.getTeam(email);
			return TeamDetail.from(newTeam);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		 */
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
