package com.dms.dao;

import java.util.List;

import com.dms.common.dao.GenericDao;
import com.dms.database.mapping.Role;
import com.dms.database.mapping.User;

public interface DaoRole extends GenericDao<Role, Integer>{
	
	/**
	 * Fetch the role of user for user
	 * @param user
	 * @return
	 */
	public List<Role> getRole(User user);
	
	/**
	 * Get {@link Role} by role name
	 * ex: get Admin role by Admin( string ) 
	 * @param roleName
	 * @return
	 */
	public Role getRoleByRoleName(String roleName);
}
