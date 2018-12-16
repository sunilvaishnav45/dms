package com.dms.serviceimpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dms.Enum.TaskDeliveryStageEnum;
import com.dms.Enum.TaskPriorityEnum;
import com.dms.dao.DaoDeliveryTask;
import com.dms.database.mapping.DeliveryTask;
import com.dms.database.mapping.TaskHistory;
import com.dms.database.mapping.User;
import com.dms.dto.DtoDeliveryTask;
import com.dms.service.ServiceDeliveryTask;
import com.dms.service.ServiceTaskHistory;

@Service("serviceDeliveryTask")
public class ServiceDeliveryTaskImpl implements ServiceDeliveryTask{
	
	private static final Logger log = Logger.getLogger(ServiceDeliveryTaskImpl.class);
	
	@Autowired
	private ServiceTaskHistory serviceTaskHistory; 

	@Autowired
	private DaoDeliveryTask daoDeliveryTask;
	
	@Override
	public Integer save(DeliveryTask entity) {
		return daoDeliveryTask.save(entity);
	}

	@Override
	public void update(DeliveryTask entity) {
		daoDeliveryTask.update(entity);
	}

	@Override
	public void delete(DeliveryTask entity) {
		daoDeliveryTask.delete(entity);
	}

	@Override
	public List<DeliveryTask> getAll() {
		return daoDeliveryTask.getAll();
	}

	@Override
	public DeliveryTask getById(Integer id) {
		return daoDeliveryTask.getById(id);
	}

	@Override
	public DeliveryTask saveDeliveryTaskFor(String title, TaskPriorityEnum priority,User user) {
		log.info("saveDeliveryTaskFor method is called for "+user.getFullName());
		DeliveryTask deliveryTask = new DeliveryTask();
		deliveryTask.setAddedBy(user);
		deliveryTask.setTaskPriorityEnum(priority);
		deliveryTask.setTitle(title);
		deliveryTask.setCurrentTaskStage(TaskDeliveryStageEnum.NEW);
		java.util.Date today = new java.util.Date();
		deliveryTask.setAddedTimeStamp(new java.sql.Timestamp(today.getTime()));
		//don't want to open db connection to save TaskHistory
		Set<TaskHistory> taskHistories = new HashSet<TaskHistory>();
		TaskHistory taskHistory = (TaskHistory) serviceTaskHistory.populateTaskHistoryFor(deliveryTask,user);
		taskHistories.add(taskHistory);
		deliveryTask.setTaskHistoriesSet(taskHistories);
		this.save(deliveryTask);
		log.info("successfully saved");
		return deliveryTask;
	}

	@Override
	public DeliveryTask updateDeliveryTaskFor(DeliveryTask deliveryTask, TaskDeliveryStageEnum deliveryStageEnum,User user) {
		log.info("updateDeliveryTaskFor method called for "+deliveryTask.getId());
		deliveryTask.setCurrentTaskStage(deliveryStageEnum);
		//don't want to open db connection to save TaskHistory
		Set<TaskHistory> taskHistories = new HashSet<TaskHistory>();
		TaskHistory taskHistory = (TaskHistory) serviceTaskHistory.populateTaskHistoryFor(deliveryTask,user);
		taskHistories.add(taskHistory);
		deliveryTask.setTaskHistoriesSet(taskHistories);
		this.update(deliveryTask);
		log.info("successfully updated");
		return deliveryTask;
	}

	@Override
	public List<DeliveryTask> fetchAllTaskFor(TaskPriorityEnum priorityEnum,
			TaskDeliveryStageEnum taskDeliveryStageEnum) {
		return daoDeliveryTask.fetchAllTaskFor(priorityEnum, taskDeliveryStageEnum);
	}

	@Override
	public DeliveryTask getHighestPriorityTask() {
		log.info("getHighestPriorityTask method called");
		
		DeliveryTask deliveryTask = null;
		List<DeliveryTask> deliveryTasks = new ArrayList<DeliveryTask>();
		
		deliveryTasks = this.fetchAllTaskFor(TaskPriorityEnum.HIGH, TaskDeliveryStageEnum.NEW);
		if(deliveryTasks !=null && !deliveryTasks.isEmpty()){
			log.info("High list priority task found size" + deliveryTasks.size());
			return deliveryTasks.get(0);
		}else{
			deliveryTasks = this.fetchAllTaskFor(TaskPriorityEnum.MEDIUM, TaskDeliveryStageEnum.NEW);
			if(deliveryTasks !=null && !deliveryTasks.isEmpty()){
				log.info("Medium list priority task found size" + deliveryTasks.size());
				return deliveryTasks.get(0);
			}else{
				deliveryTasks = this.fetchAllTaskFor(TaskPriorityEnum.LOW, TaskDeliveryStageEnum.NEW);
				if(deliveryTasks !=null && !deliveryTasks.isEmpty()){
					log.info("low list priority task found size" + deliveryTasks.size());
					return deliveryTasks.get(0);
				}else{
					log.info("there is no task in new status");
				}
			}
		}
		
		return deliveryTask;
	}

	@Override
	public DeliveryTask saveNewDeliveryTaskFor(String title, TaskPriorityEnum priority,User user) {
		log.info("saveNewDeliveryTaskFor method called for title"+title+"and priority "+priority);
		DtoDeliveryTask dtoDeliveryTask = new DtoDeliveryTask();
		dtoDeliveryTask.setTitle(title);
		dtoDeliveryTask.setCurrentTaskStage(TaskDeliveryStageEnum.NEW);
		dtoDeliveryTask.setTaskPriorityEnum(priority);
		log.info("dtoDeliveryTask successfully populated");
		
		/*DeliveryTask deliveryTask = this.saveDeliveryTaskFor(dtoDeliveryTask, user);*/
		return null;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject convertDeliveryTaskIntoJSONObject(DeliveryTask deliveryTask) {
		log.info("convertDeliveryTaskIntoJSONObject method called");
		JSONObject json = new JSONObject();
		if(deliveryTask!=null){
			json.put("id", deliveryTask.getId());
			json.put("addedBy", deliveryTask.getAddedBy().getFullName());
			json.put("title", deliveryTask.getTitle());
			json.put("priority", deliveryTask.getTaskPriorityEnum().getDisplayName());
			json.put("addedTimeStamp", new Date(deliveryTask.getAddedTimeStamp().getTime()));
			json.put("currentTaskStage", deliveryTask.getCurrentTaskStage().getDisplayName());
		}
		return json;
	}

}
