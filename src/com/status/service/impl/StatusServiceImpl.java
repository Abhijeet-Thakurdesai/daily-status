package com.status.service.impl;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
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
import com.status.wrapper.EmailScanner;
import com.status.wrapper.EmailSender;

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

	@Autowired
	private EmailScanner emailScanner;
	
	@Autowired
	private EmailSender emailSender;
	
	
	@Transactional
	public List<Status> getStatus(Date date,Team team) {
		return statusDao.getStatus(date,team);
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
	public void check(){
//		emailScanner.getUnreadMailsAndStore();
		
		emailSender.createSummary();
//		try {
//			emailSender.askForStatusMail();
//		} catch (AddressException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
	
	
	@Transactional
	public List<Team> getTeams(){
	return teamDao.getTeams();
	}
	

	@Transactional
	public User getUser(String email){
		return userDao.getByEmailId(email);
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
