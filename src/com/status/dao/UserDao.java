package com.status.dao;

import com.status.model.User;

public interface UserDao {
	void saveOrUpdate(User user);
	User getByEmailId(String email);
}
