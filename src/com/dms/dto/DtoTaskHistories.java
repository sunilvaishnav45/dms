package com.dms.dto;

public class DtoTaskHistories {

	private String addedBy;
	
	private String addedTimeStamp;
	
	private String taskDeliveryStageEnum;

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public String getAddedTimeStamp() {
		return addedTimeStamp;
	}

	public void setAddedTimeStamp(String addedTimeStamp) {
		this.addedTimeStamp = addedTimeStamp;
	}

	public String getTaskDeliveryStageEnum() {
		return taskDeliveryStageEnum;
	}

	public void setTaskDeliveryStageEnum(String taskDeliveryStageEnum) {
		this.taskDeliveryStageEnum = taskDeliveryStageEnum;
	}
}
