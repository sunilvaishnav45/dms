package com.dms.viewpreparer;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.beans.factory.annotation.Autowired;

import com.dms.Enum.TaskDeliveryStageEnum;
import com.dms.Enum.TaskPriorityEnum;
import com.dms.database.mapping.DeliveryTask;
import com.dms.service.ServiceDeliveryTask;


/**
 * To serve data to Admin page this method will be used
 * @author sunil
 *
 */
public class AdminViewPreparer implements ViewPreparer {

	@Autowired 
	private ServiceDeliveryTask  serviceDeliveryTask;
	
	private static Logger log =  Logger.getLogger(AdminViewPreparer.class);

	@Override
	public void execute(Request req, AttributeContext context) {
		log.info("Admin view prepare called");
		//fetching all the task to show to admin
		List<DeliveryTask> deliveryTasks = serviceDeliveryTask.getAll();
		
		context.putAttribute("deliveryTasks", new Attribute(deliveryTasks));
		context.putAttribute("taskDeliveryStageAcceptedEnum", new Attribute(TaskDeliveryStageEnum.ACCEPTED));
		context.putAttribute("taskDeliveryStageCancelEnum", new Attribute(TaskDeliveryStageEnum.CANCELLED));
		context.putAttribute("taskPriorityEnum", new Attribute(TaskPriorityEnum.values()));
		context.putAttribute("taskDeliveryStageNewEnum", new Attribute(TaskDeliveryStageEnum.NEW));
	}
	
	
}
