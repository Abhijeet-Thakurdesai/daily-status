package com.status.factory;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

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

	public Team createTeam(TeamDetail detail) {
		Team newTeam = new Team();

		newTeam.setName(detail.getName());
		setCompany(detail, newTeam);
		newTeam.setAlias(detail.getAlias());
		newTeam.setLeads(detail.getLeaders());
		newTeam.setMembers(detail.getMembers());
		return newTeam;
	}

	private void setCompany(TeamDetail detail,Team newTeam) {
		try {
			if (StringUtils.isBlank(detail.getCompanyName())) {
				throw new TeamModuleException("Team Name can Not be blank or Empty");
			}

			Company company = companyDao.getCompanyByName(detail.getCompanyName());
			if (company == null) {
				throw new TeamModuleException("Company name "+ detail.getCompanyName() +" does not exist");
			}

			newTeam.setCompany(company);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
 }
