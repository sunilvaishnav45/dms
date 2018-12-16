package com.dms.daoimpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dms.common.dao.GenericDaoImpl;
import com.dms.dao.DaoRole;
import com.dms.database.mapping.Role;
import com.dms.database.mapping.User;

@Repository("daoRole")
@Transactional(rollbackFor = Exception.class)
public class DaoRoleImpl extends GenericDaoImpl<Role, Integer> implements DaoRole {
	
	private static final Logger log= Logger.getLogger(DaoRoleImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRole(User user) {
		Criteria criteria = this.getSession().createCriteria(Role.class, "role");
		criteria.createAlias("role.userRoleMappingSet", "urms", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("urms.user", user));
		return criteria.list();
	}

	@Override
	public Role getRoleByRoleName(String roleName) {
		log.info("getRoleByRoleName method called for "+roleName);
		Criteria criteria = this.getSession().createCriteria(Role.class, "role");
		criteria.add(Restrictions.eq("role.roleName", roleName));
		return (Role) criteria.uniqueResult();
	}

}
