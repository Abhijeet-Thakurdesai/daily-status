package com.status.dao;

import com.status.model.Company;

public interface CompanyDao {
	Company getCompanyByName(String name);

	Long getIdByName(String name);

	void saveOrUpdate(Company company);

}
