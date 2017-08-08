package com.status.controller;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.status.events.TeamDetail;
import com.status.events.UserDetail;
import com.status.model.Company;
import com.status.model.Status;
import com.status.service.StatusService;

@Controller
@RequestMapping("/")
public class StatusController {	

	@Autowired
	private StatusService statusSvc;
		
	//@RequestMapping(method = RequestMethod.GET)
//	@ResponseBody
//	public List<Status> getStatus(
//		@RequestParam(value = "date",required = false)
//		@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
//		return statusSvc.getStatus(date);
//	}
	
	
	//for checking of method getTeam 
	@RequestMapping(value="/vll",method = RequestMethod.GET)
	@ResponseBody
	public String getTeam(){
		return statusSvc.getTeam("abc@gmail").getName();
	}
	
	

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Status addStatus(@RequestBody Status status) {		
		return statusSvc.addStatus(status);	
		
	}

	@RequestMapping(value="/create_team", method = RequestMethod.POST)
	@ResponseBody
	public TeamDetail createTeam(@RequestBody TeamDetail team) {
		return statusSvc.createTeam(team);
	}

	@RequestMapping(value="/create_user", method = RequestMethod.POST)
	@ResponseBody
	public UserDetail createUser(@RequestBody UserDetail user) {
		return statusSvc.createUser(user);
	}
	
	@RequestMapping(value="/create_company", method = RequestMethod.POST)
	@ResponseBody
	public Company createTeam(@RequestBody Company company) {
		return statusSvc.createCompany(company);
	}

}
