package com.dms.dao;

import java.util.List;

import com.dms.common.dao.GenericDao;
import com.dms.database.mapping.DeliveryTask;
import com.dms.database.mapping.TaskHistory;
import com.dms.database.mapping.User;

public interface DaoTaskHistory  extends GenericDao<TaskHistory,Integer>{

	/**
	 * To fetch all the task for a {@link DeliveryTask} this method will be used <br>
	 * Assuming a valid {@link DeliveryTask} is calling the method
	 * @param deliveryTask
	 * @return List<TaskHistory>
	 */
	public List<TaskHistory> getAllTaskHistoryFor(DeliveryTask deliveryTask);
	
	/**
	 * To fetch all the task for {@link User}
	 * @param user
	 * @return List<TaskHistory>
	 */
	public List<TaskHistory> getAllTaskHistoryFor(User user);
	
	/**
	 *  To fetch all the task for {@link User} and {@link DeliveryTask}
	 * @param deliveryTask
	 * @param user
	 * @return
	 */
	public List<TaskHistory>  getAllTaskHistoryFor(DeliveryTask deliveryTask,User user);
}
