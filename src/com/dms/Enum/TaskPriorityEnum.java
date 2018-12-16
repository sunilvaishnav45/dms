package com.dms.Enum;

import org.apache.log4j.Logger;

public enum TaskPriorityEnum {
	
	HIGH("High"),MEDIUM("Medium"),LOW("Low");
	
	private static final Logger log  = Logger.getLogger(TaskPriorityEnum.class);
	
	private String displayName;
	
	private TaskPriorityEnum(String displayName){
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
	/**
	 * To get enum by displayName, this method will be used
	 * @param displayName
	 * @return TaskPriorityEnum
	 */
	public static TaskPriorityEnum getTaskPriorityEnumFor(String displayName){
		log.info("getTaskPriorityEnumFor method called for "+displayName);
		if(!displayName.isEmpty() && displayName !=null){
			for(TaskPriorityEnum taskPriorityEnum  : TaskPriorityEnum.values() ){
				if(taskPriorityEnum.displayName.equals(displayName)){
					log.info("TaskPriorityEnum found for, returning now "+displayName);
					return taskPriorityEnum;
				}
			}
		}
		return null;
	}
}
