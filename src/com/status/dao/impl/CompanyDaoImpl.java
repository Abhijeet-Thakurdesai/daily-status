package com.status.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.status.dao.ComapnyDao;
import com.status.model.Company;

@Repository("companyDao")
public class CompanyDaoImpl  implements ComapnyDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Company getCompanyByName(String name) {
		Query query= sessionFactory.getCurrentSession().createQuery("from Company where name=:name").setParameter("name", name);
		return (Company) query.uniqueResult();
	}

}
