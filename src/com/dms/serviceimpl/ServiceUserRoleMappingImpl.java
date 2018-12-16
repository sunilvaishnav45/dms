package com.dms.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dms.dao.DaoUserRoleMapping;
import com.dms.database.mapping.Role;
import com.dms.database.mapping.User;
import com.dms.database.mapping.UserRoleMapping;
import com.dms.service.ServiceUserRoleMapping;

@Service("serviceUserRoleMapping")
public class ServiceUserRoleMappingImpl implements ServiceUserRoleMapping{
	
	@Autowired
	private DaoUserRoleMapping daoUserRoleMapping;

	@Override
	public Integer save(UserRoleMapping entity) {
		return daoUserRoleMapping.save(entity);
	}

	@Override
	public void update(UserRoleMapping entity) {
		daoUserRoleMapping.update(entity);
	}

	@Override
	public void delete(UserRoleMapping entity) {
		daoUserRoleMapping.delete(entity);
	}

	@Override
	public List<UserRoleMapping> getAll() {
		return daoUserRoleMapping.getAll();
	}

	@Override
	public UserRoleMapping getById(Integer id) {
		return daoUserRoleMapping.getById(id);
	}

	@Override
	public List<UserRoleMapping> getUserRoleByUser(User user) {
		return daoUserRoleMapping.getUserRoleByUser(user);
	}

	@Override
	public List<UserRoleMapping> getUserByUserRole(Role role) {
		return daoUserRoleMapping.getUserByUserRole(role);
	}

}
