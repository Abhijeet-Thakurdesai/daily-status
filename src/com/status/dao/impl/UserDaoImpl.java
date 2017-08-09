package com.status.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.status.model.User;
import com.status.dao.UserDao;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveOrUpdate(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	@Override
	public User getByEmailId(String email) {
		Query query= sessionFactory.getCurrentSession().createQuery("from User where emailId=:emailId").setParameter("emailId",email);
		return (User) query.uniqueResult();
	}
}
