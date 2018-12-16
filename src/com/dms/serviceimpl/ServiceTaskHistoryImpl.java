package com.dms.serviceimpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dms.Enum.TaskDeliveryStageEnum;
import com.dms.dao.DaoTaskHistory;
import com.dms.database.mapping.DeliveryTask;
import com.dms.database.mapping.TaskHistory;
import com.dms.database.mapping.User;
import com.dms.dto.DtoTaskHistories;
import com.dms.login.filter.CustomAuthenticator;
import com.dms.service.ServiceTaskHistory;

@Service("serviceTaskHistory")
public class ServiceTaskHistoryImpl implements ServiceTaskHistory{

	private static final Logger log = Logger.getLogger(ServiceTaskHistoryImpl.class);
	
	@Autowired
	private DaoTaskHistory daoTaskHistory;
	
	@Autowired
	private CustomAuthenticator customAuthenticator;
	
	@Override
	public Integer save(TaskHistory entity) {
		return daoTaskHistory.save(entity);
	}

	@Override
	public void update(TaskHistory entity) {
		daoTaskHistory.update(entity);
	}

	@Override
	public void delete(TaskHistory entity) {
		daoTaskHistory.delete(entity);
	}

	@Override
	public List<TaskHistory> getAll() {
		return daoTaskHistory.getAll();
	}

	@Override
	public TaskHistory getById(Integer id) {
		return daoTaskHistory.getById(id);
	}

	@Override
	public List<TaskHistory> getAllTaskHistoryFor(DeliveryTask deliveryTask) {
		return daoTaskHistory.getAllTaskHistoryFor(deliveryTask);
	}

	@Override
	public TaskHistory populateTaskHistoryFor(DeliveryTask deliveryTask) {
		
		log.info("saveTaskHistoryFor for task id"+deliveryTask.getId());
		TaskHistory taskHistory =  new TaskHistory();
		taskHistory.setActionTakenBy(customAuthenticator.getUser());
		taskHistory.setDeliveryTask(deliveryTask);
		taskHistory.setTaskDeliveryStageEnum(deliveryTask.getCurrentTaskStage());
		java.util.Date today = new java.util.Date();
		taskHistory.setActionTakenTimestamp(new java.sql.Timestamp(today.getTime()));
		return taskHistory;
	}

	@Override
	public List<DtoTaskHistories> getDtoTaskHistories(DeliveryTask deliveryTask) {
		log.info("getDtoTaskHistories method called");
		List<DtoTaskHistories> dtoTaskHistories = new ArrayList<DtoTaskHistories>();
		List<TaskHistory> histories = this.getAllTaskHistoryFor(deliveryTask);
		log.info("Fetched Task History lenght"+histories.size());
		if(histories!=null && !histories.isEmpty()){
			for(TaskHistory history :  histories){
				log.info("Casting History of task into DtoTaskHistories for hstry id "+history.getId());
				DtoTaskHistories taskHistories = new DtoTaskHistories();
				taskHistories.setAddedBy(history.getActionTakenBy().getFullName());
				Date addedDate =  new Date(history.getActionTakenTimestamp().getTime());
				taskHistories.setAddedTimeStamp(addedDate.toString());
				taskHistories.setTaskDeliveryStageEnum(history.getTaskDeliveryStageEnum().getDisplayName());
				dtoTaskHistories.add(taskHistories);
				log.info("DtoTaskHistories successfully populated for hstry id"+history.getId());
			}
		}
		log.info("List<DtoTaskHistories> successfully populated returning result now size "+dtoTaskHistories.size());
		return dtoTaskHistories;
	}

	@Override
	public List<TaskHistory> getAllTaskHistoryFor(User user) {
		return daoTaskHistory.getAllTaskHistoryFor(user);
	}
	
	@Override
	public List<TaskHistory> getAllTaskHistoryFor(DeliveryTask deliveryTask, User user) {
		return daoTaskHistory.getAllTaskHistoryFor(deliveryTask, user);
	}
	
	@Override
	public Integer getTotalCompletedTaskFor(User user) {
		List<TaskHistory> totalTaskForUser = this.getAllTaskHistoryFor(user);
		int completedTaskCount = 0;
		if(totalTaskForUser !=null && !totalTaskForUser.isEmpty()){
			log.info("task found for user "+totalTaskForUser.size());
			for(TaskHistory taskHistory :  totalTaskForUser){
				if(taskHistory.getTaskDeliveryStageEnum().equals(TaskDeliveryStageEnum.ACCEPTED)){
					log.info("taskid"+taskHistory.getDeliveryTask().getId()+"is completed");
					List<TaskHistory> deliveryTasks = this.getAllTaskHistoryFor(taskHistory.getDeliveryTask(),user);
					log.info("Total action taken by user on task"+deliveryTasks.size());
					if(deliveryTasks.size() == 1)//it mean task doesn't declined or completed by user
						completedTaskCount++;
					
				}
			}
		}
		return completedTaskCount;
	}

	@Override
	public TaskHistory populateTaskHistoryFor(DeliveryTask deliveryTask, User user) {
		
		log.info("saveTaskHistoryFor for task id"+deliveryTask.getId());
		TaskHistory taskHistory =  new TaskHistory();
		taskHistory.setActionTakenBy(user);
		taskHistory.setDeliveryTask(deliveryTask);
		taskHistory.setTaskDeliveryStageEnum(deliveryTask.getCurrentTaskStage());
		java.util.Date today = new java.util.Date();
		taskHistory.setActionTakenTimestamp(new java.sql.Timestamp(today.getTime()));
		return taskHistory;
	}

}
