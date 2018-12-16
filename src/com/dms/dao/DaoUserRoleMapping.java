package com.dms.dao;

import java.util.List;

import com.dms.common.dao.GenericDao;
import com.dms.database.mapping.Role;
import com.dms.database.mapping.User;
import com.dms.database.mapping.UserRoleMapping;

public interface DaoUserRoleMapping extends GenericDao<UserRoleMapping, Integer> {
	/**
	 * Fetch the user role mappings for the user
	 * @param user
	 * @return
	 */
	public List<UserRoleMapping> getUserRoleByUser(User user);
	
	/**
	 * Fetch the users for a role
	 * @param role
	 * @return
	 */
	public List<UserRoleMapping> getUserByUserRole(Role role);

}
