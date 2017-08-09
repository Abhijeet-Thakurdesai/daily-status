package com.status.factory;

import com.status.events.TeamDetail;
import com.status.exception.TeamModuleException;
import com.status.model.Team;

public interface TeamFactory {
	Team createTeam(TeamDetail detail) throws TeamModuleException;
}
