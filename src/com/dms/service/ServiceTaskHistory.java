package com.dms.service;

import java.util.List;

import com.dms.dao.DaoTaskHistory;
import com.dms.database.mapping.DeliveryTask;
import com.dms.database.mapping.TaskHistory;
import com.dms.database.mapping.User;
import com.dms.dto.DtoTaskHistories;

public interface ServiceTaskHistory extends DaoTaskHistory{
	
	/**
	 * For a {@link DeliveryTask} this method populate {@link TaskHistory}<br>
	 * Assuming a valid {@link DeliveryTask} is set as arrgument.
	 * @param deliveryTask
	 * @return
	 */
	public TaskHistory populateTaskHistoryFor(DeliveryTask deliveryTask);
	
	/**
	 * For a {@link DeliveryTask} this method populate {@link TaskHistory}<br>
	 * Assuming a valid {@link DeliveryTask} is set as arrgument.
	 * @param deliveryTask
	 * @return
	 */
	public TaskHistory populateTaskHistoryFor(DeliveryTask deliveryTask,User user);
	
	/**
	 * Fetch all {@link TaskHistory} By {@link DeliveryTask} need to be cast into {@link DtoTaskHistories} <br>
	 * So {@link List} of {@link DtoTaskHistories} can be shown in view easily
	 * @return
	 */
	public List<DtoTaskHistories> getDtoTaskHistories(DeliveryTask deliveryTask);
	
	/**
	 * To get Total completed task for a user this method will be used<br>
	 * @param user
	 * @return
	 */
	public Integer getTotalCompletedTaskFor(User user);

}
