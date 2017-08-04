package com.status.factory;

import com.status.events.UserDetail;
import com.status.exception.TeamModuleException;
import com.status.model.User;

public interface UserFactory {
	User createUser(UserDetail detail) throws TeamModuleException;
}
