package com.dms.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dms.common.dao.GenericDaoImpl;
import com.dms.dao.DaoUserRoleMapping;
import com.dms.database.mapping.Role;
import com.dms.database.mapping.User;
import com.dms.database.mapping.UserRoleMapping;

@Repository("daoUserRoleMapping")
@Transactional(rollbackFor=Exception.class)
@SuppressWarnings("unchecked")
public class DaoUserRoleMappingImpl extends GenericDaoImpl<UserRoleMapping, Integer> implements DaoUserRoleMapping{

	@Override
	public List<UserRoleMapping> getUserRoleByUser(User user) {
		Criteria criteria =  this.getSession().createCriteria(UserRoleMapping.class, "userRoleMapping");
		criteria.add(Restrictions.eq("userRoleMapping.user",user));
		return criteria.list();
	}

	@Override
	public List<UserRoleMapping> getUserByUserRole(Role role) {
		Criteria criteria =  this.getSession().createCriteria(UserRoleMapping.class, "userRoleMapping");
		criteria.add(Restrictions.eq("userRoleMapping.role",role));
		return criteria.list();
	}

}
