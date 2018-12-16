package com.dms.service;

import org.json.simple.JSONObject;

import com.dms.Enum.TaskDeliveryStageEnum;
import com.dms.Enum.TaskPriorityEnum;
import com.dms.dao.DaoDeliveryTask;
import com.dms.database.mapping.DeliveryTask;
import com.dms.database.mapping.TaskHistory;
import com.dms.database.mapping.User;

public interface ServiceDeliveryTask extends DaoDeliveryTask{
	
	/**
	 * To save a {@link DeliveryTask} in db
	 * @param dtoDeliveryTask
	 * @return {@link DeliveryTask}
	 */
	public DeliveryTask saveDeliveryTaskFor(String title, TaskPriorityEnum priority,User user);
	
	/**
	 * For a {@link TaskDeliveryStageEnum} this method update the {@link DeliveryTask}<br>
	 * This method will also push this in {@link TaskHistory} <br>
	 * @param deliveryTask
	 * @param deliveryStageEnum
	 * @return
	 */
	public DeliveryTask updateDeliveryTaskFor(DeliveryTask deliveryTask,TaskDeliveryStageEnum deliveryStageEnum,User user);
	
	/**
	 * Condition for High priority task
	 * 1.Task should be in new stage
	 * 2.If Multiple task in High priority than fetch oldest task <br>
	 * 3.If there is no high priority task than fetch medium priority task <br>
	 * 4.If Multiple task in medium priority than fetch oldest task <br>
	 * 5.If there is no medium priority task than fetch law priority task <br>
	 * 6.If Multiple task in low priority than fetch oldest task <br>
	 * @return {@link DeliveryTask}
	 */
	public DeliveryTask getHighestPriorityTask();
	
	/**
	 * Save new {@link DeliveryTask} for title and {@link TaskPriorityEnum}
	 * @param title
	 * @param priorityEnum
	 * @return
	 */
	public DeliveryTask saveNewDeliveryTaskFor(String title,TaskPriorityEnum priority,User user);
	
	public JSONObject convertDeliveryTaskIntoJSONObject(DeliveryTask deliveryTask);
	
}
