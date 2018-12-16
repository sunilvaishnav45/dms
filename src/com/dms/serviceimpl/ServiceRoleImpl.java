package com.dms.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dms.dao.DaoRole;
import com.dms.database.mapping.Role;
import com.dms.database.mapping.User;
import com.dms.service.ServiceRole;

@Service("serviceRole")
public class ServiceRoleImpl implements ServiceRole{

	@Autowired
	private DaoRole daoRole;
	
	@Override
	public List<Role> getRole(User user) {
		return daoRole.getRole(user);
	}

	@Override
	public Integer save(Role entity) {
		return daoRole.save(entity);
	}

	@Override
	public void update(Role entity) {
		daoRole.update(entity);
	}

	@Override
	public void delete(Role entity) {
		daoRole.delete(entity);
	}

	@Override
	public List<Role> getAll() {
		return daoRole.getAll();
	}

	@Override
	public Role getById(Integer id) {
		return daoRole.getById(id);
	}

	@Override
	public Role getRoleByRoleName(String roleName) {
		return daoRole.getRoleByRoleName(roleName);
	}

}
