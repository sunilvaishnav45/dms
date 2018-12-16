package com.dms.database.mapping;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dms.Enum.TaskDeliveryStageEnum;
import com.dms.Enum.TaskPriorityEnum;

@Entity
@Table(name="delivery_tasks")
public class DeliveryTask {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="added_by")
	private User addedBy;
	
	@Column(name="title")
	private String title;
	
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="priority")
	private TaskPriorityEnum taskPriorityEnum;
	
	@Column(name="added_timestamp")
	private Timestamp addedTimeStamp;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="current_task_stage")
	private TaskDeliveryStageEnum currentTaskStage ;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "deliveryTask",cascade = CascadeType.ALL)
	Set<TaskHistory> taskHistoriesSet  = new HashSet<TaskHistory>();

	public int getId() {
		return id;
	}
	
	public User getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(User addedBy) {
		this.addedBy = addedBy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TaskPriorityEnum getTaskPriorityEnum() {
		return taskPriorityEnum;
	}

	public void setTaskPriorityEnum(TaskPriorityEnum taskPriorityEnum) {
		this.taskPriorityEnum = taskPriorityEnum;
	}

	public Timestamp getAddedTimeStamp() {
		return addedTimeStamp;
	}

	public void setAddedTimeStamp(Timestamp addedTimeStamp) {
		this.addedTimeStamp = addedTimeStamp;
	}

	public TaskDeliveryStageEnum getCurrentTaskStage() {
		return currentTaskStage;
	}

	public void setCurrentTaskStage(TaskDeliveryStageEnum currentTaskStage) {
		this.currentTaskStage = currentTaskStage;
	}

	public Set<TaskHistory> getTaskHistoriesSet() {
		return taskHistoriesSet;
	}

	public void setTaskHistoriesSet(Set<TaskHistory> taskHistoriesSet) {
		this.taskHistoriesSet = taskHistoriesSet;
	}
}
