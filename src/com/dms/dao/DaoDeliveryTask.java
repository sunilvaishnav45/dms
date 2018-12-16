package com.dms.dao;

import java.util.List;

import com.dms.Enum.TaskDeliveryStageEnum;
import com.dms.Enum.TaskPriorityEnum;
import com.dms.common.dao.GenericDao;
import com.dms.database.mapping.DeliveryTask;

public interface DaoDeliveryTask extends GenericDao<DeliveryTask, Integer>{

	/**
	 * To Get {@link DeliveryTask} based on {@link TaskPriorityEnum} and {@link TaskDeliveryStageEnum}
	 * Result will be order by Addition timestamp
	 * @param priorityEnum
	 * @param taskDeliveryStageEnum
	 * @return
	 */
	public List<DeliveryTask> fetchAllTaskFor(TaskPriorityEnum priorityEnum, TaskDeliveryStageEnum taskDeliveryStageEnum);
}
