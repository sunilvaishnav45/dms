package com.dms.Enum;

import org.apache.log4j.Logger;

public enum TaskDeliveryStageEnum {
	
	NEW("New"),ACCEPTED("Accepted"),COMPLETED("Completed"),DECLINED("Declined"),CANCELLED("Cancelled");
	
	private static final Logger log  = Logger.getLogger(TaskDeliveryStageEnum.class);
	
	private String displayName;
	
	private TaskDeliveryStageEnum(String displayName){
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * To get enum by displayName, this method will be used
	 * @param displayName
	 * @return TaskDeliveryEnum
	 */
	public static TaskDeliveryStageEnum getTaskDeliveryEnumFor(String displayName){
		log.info("getTaskDeliveryEnumFor method called for "+displayName);
		if(!displayName.isEmpty() && displayName !=null){
			for(TaskDeliveryStageEnum taskDeliveryEnum  : TaskDeliveryStageEnum.values() ){
				if(taskDeliveryEnum.displayName.equals(displayName)){
					log.info("TaskDeliveryEnum found for, returning now "+displayName);
					return taskDeliveryEnum;
				}
			}
		}
		return null;
	}

}
