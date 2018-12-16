package com.dms.dao;

import com.dms.common.dao.GenericDao;
import com.dms.database.mapping.User;

public interface DaoUser extends GenericDao<User, Integer>{
	/**
	 * Get user for email address
	 * @param email
	 * @return
	 */
	public User getUserFor(String email);

}
