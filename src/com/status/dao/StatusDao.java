package com.status.dao;

import java.util.Date;
import java.util.List;
import com.status.model.Status;
import com.status.model.Team;

public interface StatusDao {
	
	List<Status> getStatus(Date date,Team team);
	void addStatus(Status stat);
}
