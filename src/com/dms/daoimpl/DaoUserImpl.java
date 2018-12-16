package com.dms.daoimpl;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dms.common.dao.GenericDaoImpl;
import com.dms.dao.DaoUser;
import com.dms.database.mapping.User;

@Repository("daoUser")
@Transactional(rollbackFor=Exception.class)
public class DaoUserImpl extends GenericDaoImpl<User, Integer> implements DaoUser{
	
	@Override
	public User getUserFor(String email) {
		Criteria criteria =  this.getSession().createCriteria(User.class, "user");
		criteria.add(Restrictions.eq("user.email",email));
		return (User) criteria.uniqueResult();
	}
}
