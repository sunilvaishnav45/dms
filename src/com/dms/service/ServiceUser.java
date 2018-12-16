package com.dms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dms.dao.DaoUser;
import com.dms.database.mapping.UserRoleMapping;

@Service
public interface ServiceUser extends DaoUser{
	
	/**
	 * For role "Admin" it will return all admins
	 * For role "User" it will return all users 
	 * @return
	 */
	public List<UserRoleMapping> getAllRoleBasedUser(String role);

}
