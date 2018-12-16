package com.dms.dto;

import com.dms.Enum.TaskDeliveryStageEnum;
import com.dms.Enum.TaskPriorityEnum;

/**
 * Use to get data from view to save new task in db 
 * @author sunil
 *
 */
public class DtoDeliveryTask {

	private String title;
	
	private TaskPriorityEnum taskPriorityEnum;
	
	private TaskDeliveryStageEnum currentTaskStage;
	
	private String email;

	public String getTitle() {
		return title;
	}

	public TaskPriorityEnum getTaskPriorityEnum() {
		return taskPriorityEnum;
	}

	public void setTaskPriorityEnum(TaskPriorityEnum taskPriorityEnum) {
		this.taskPriorityEnum = taskPriorityEnum;
	}

	public TaskDeliveryStageEnum getCurrentTaskStage() {
		return currentTaskStage;
	}

	public void setCurrentTaskStage(TaskDeliveryStageEnum currentTaskStage) {
		this.currentTaskStage = currentTaskStage;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
