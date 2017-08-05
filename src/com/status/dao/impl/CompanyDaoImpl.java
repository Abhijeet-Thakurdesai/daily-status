package com.status.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.status.dao.CompanyDao;
import com.status.model.Company;

@Repository("companyDao")
public class CompanyDaoImpl  implements CompanyDao{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Company getCompanyByName(String name) {
		Query query= sessionFactory.getCurrentSession().createQuery("from Company where name=:name").setParameter("name", name);
		return (Company) query.uniqueResult();
	}

	@Override
	public Long getIdByName(String name) {
		Query query= sessionFactory.getCurrentSession().createQuery("from Company where name=:name").setParameter("name", name);
		return ((Company)query.uniqueResult()).getId();
	}

	@Override
	public void saveOrUpdate(Company company) {
		sessionFactory.getCurrentSession().saveOrUpdate(company);
	}

}
