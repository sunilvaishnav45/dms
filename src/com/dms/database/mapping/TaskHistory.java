package com.dms.database.mapping;

import java.sql.Timestamp;

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
import javax.persistence.Table;

import com.dms.Enum.TaskDeliveryStageEnum;

@Entity
@Table(name="task_history")
public class TaskHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int Id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="delivery_task_id")
	private DeliveryTask deliveryTask;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="task_delivery_stage")
	private TaskDeliveryStageEnum taskDeliveryStageEnum;
	
	@ManyToOne(fetch  = FetchType.EAGER)
	@JoinColumn(name = "action_taken_by")
	private User actionTakenBy;
	
	@Column(name="action_taken_timestamp")
	private Timestamp actionTakenTimestamp;

	public int getId() {
		return Id;
	}

	public DeliveryTask getDeliveryTask() {
		return deliveryTask;
	}

	public void setDeliveryTask(DeliveryTask deliveryTask) {
		this.deliveryTask = deliveryTask;
	}

	public TaskDeliveryStageEnum getTaskDeliveryStageEnum() {
		return taskDeliveryStageEnum;
	}

	public void setTaskDeliveryStageEnum(TaskDeliveryStageEnum taskDeliveryStageEnum) {
		this.taskDeliveryStageEnum = taskDeliveryStageEnum;
	}

	public User getActionTakenBy() {
		return actionTakenBy;
	}

	public void setActionTakenBy(User actionTakenBy) {
		this.actionTakenBy = actionTakenBy;
	}

	public Timestamp getActionTakenTimestamp() {
		return actionTakenTimestamp;
	}

	public void setActionTakenTimestamp(Timestamp actionTakenTimestamp) {
		this.actionTakenTimestamp = actionTakenTimestamp;
	}
}
