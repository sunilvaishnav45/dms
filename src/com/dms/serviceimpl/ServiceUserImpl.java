package com.dms.serviceimpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dms.dao.DaoUser;
import com.dms.database.mapping.Role;
import com.dms.database.mapping.User;
import com.dms.database.mapping.UserRoleMapping;
import com.dms.service.ServiceRole;
import com.dms.service.ServiceUser;
import com.dms.service.ServiceUserRoleMapping;

@Service("serviceUser")
public class ServiceUserImpl implements ServiceUser {

	@Autowired
	private DaoUser daoUser;
	
	@Autowired
	private ServiceUserRoleMapping serviceUserRoleMapping;
	
	@Autowired
	private ServiceRole serviceRole;
	
	private static final Logger log = Logger.getLogger(ServiceUserImpl.class);

	@Override
	public User getUserFor(String email) {
		return daoUser.getUserFor(email);
	}

	@Override
	public Integer save(User entity) {
		return daoUser.save(entity);
	}

	@Override
	public void update(User entity) {
		daoUser.update(entity);
	}

	@Override
	public void delete(User entity) {
		daoUser.delete(entity);
	}

	@Override
	public List<User> getAll() {
		return daoUser.getAll();
	}

	@Override
	public User getById(Integer id) {
		return daoUser.getById(id);
	}

	@Override
	public List<UserRoleMapping> getAllRoleBasedUser(String role) {
		log.info("getAllRoleBasedUser method called, fetching all users for role"+role);
		if(role.equals("Admin")){
			Role roleAdmin = serviceRole.getRoleByRoleName("Admin");
			log.info("role successfully fetched for"+roleAdmin.getRoleName());
			return serviceUserRoleMapping.getUserByUserRole(roleAdmin);
		}else if(role.equals("User")){
			Role roleUser  = serviceRole.getRoleByRoleName("User");
			log.info("role successfully fetched for"+roleUser.getRoleName());
			return serviceUserRoleMapping.getUserByUserRole(roleUser);
		}
		return null;
	}

}
