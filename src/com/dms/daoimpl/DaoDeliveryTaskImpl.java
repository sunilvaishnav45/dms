package com.dms.daoimpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dms.Enum.TaskDeliveryStageEnum;
import com.dms.Enum.TaskPriorityEnum;
import com.dms.common.dao.GenericDaoImpl;
import com.dms.dao.DaoDeliveryTask;
import com.dms.database.mapping.DeliveryTask;

@Repository("daoDeliveryTask")
@Transactional(rollbackFor = Exception.class)
public class DaoDeliveryTaskImpl extends GenericDaoImpl<DeliveryTask, Integer> implements DaoDeliveryTask{
	
	private static final Logger log = Logger.getLogger(DaoDeliveryTaskImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryTask> fetchAllTaskFor(TaskPriorityEnum priorityEnum,
			TaskDeliveryStageEnum taskDeliveryStageEnum) {
		log.info("fetchAllTaskFor method called priority" + priorityEnum.getDisplayName() + " And status"
				+ taskDeliveryStageEnum.getDisplayName());
		Criteria cr  = this.getSession().createCriteria(DeliveryTask.class,"dt");
		cr.add(Restrictions.eq("dt.currentTaskStage", taskDeliveryStageEnum));
		cr.add(Restrictions.eq("dt.taskPriorityEnum", priorityEnum));
		cr.addOrder(Order.asc("dt.addedTimeStamp"));
		log.info("result successfully fetched returning list now size "+cr.list().size());
		return cr.list();
	}
	
	
}
