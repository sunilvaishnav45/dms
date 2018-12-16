package com.dms.daoimpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dms.common.dao.GenericDaoImpl;
import com.dms.dao.DaoTaskHistory;
import com.dms.database.mapping.DeliveryTask;
import com.dms.database.mapping.TaskHistory;
import com.dms.database.mapping.User;


@Repository("daoTaskHistory")
@Transactional(rollbackFor = Exception.class)
public class DaoTaskHistoryImpl extends GenericDaoImpl<TaskHistory, Integer> implements DaoTaskHistory{

	private static final Logger  log = Logger.getLogger(DaoTaskHistoryImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TaskHistory> getAllTaskHistoryFor(DeliveryTask deliveryTask) {
		log.info("getAllTaskHistoryFor method called for deliveryTaskId"+deliveryTask.getId());
		Criteria cr  = this.getSession().createCriteria(TaskHistory.class,"th");
		cr.add(Restrictions.eq("th.deliveryTask", deliveryTask));
		cr.addOrder(Order.asc("th.actionTakenTimestamp"));
		log.info("list has been fetched - size"+cr.list().size());
		return cr.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskHistory> getAllTaskHistoryFor(User user) {
		log.info("getAllTaskHistoryFor method called for"+user.getFullName());
		Criteria cr  =  this.getSession().createCriteria(TaskHistory.class,"th");
		cr.add(Restrictions.eq("th.actionTakenBy", user));
		log.info("fetched all Task history for user size "+cr.list().size());
		return cr.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskHistory> getAllTaskHistoryFor(DeliveryTask deliveryTask, User user) {
		log.info("getAllTaskHistoryFor method called for"+user.getFullName());
		Criteria cr  =  this.getSession().createCriteria(TaskHistory.class,"th");
		cr.add(Restrictions.eq("th.deliveryTask", deliveryTask));
		cr.add(Restrictions.eq("th.actionTakenBy", user));
		cr.addOrder(Order.asc("th.actionTakenTimestamp"));
		log.info("fetched all Task history for user size "+cr.list().size());
		return cr.list();
	}

}
