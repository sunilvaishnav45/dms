package com.dms.viewpreparer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.beans.factory.annotation.Autowired;

import com.dms.Enum.TaskDeliveryStageEnum;
import com.dms.database.mapping.DeliveryTask;
import com.dms.database.mapping.TaskHistory;
import com.dms.database.mapping.User;
import com.dms.login.filter.CustomAuthenticator;
import com.dms.service.ServiceDeliveryTask;
import com.dms.service.ServiceTaskHistory;

/**
 * To set data for user this view prepare will be used
 * @author sunil
 *
 */
public class UserViewPreparer implements ViewPreparer{
	
	
	@Autowired
	private ServiceDeliveryTask serviceDeliveryTask;
	
	@Autowired
	private ServiceTaskHistory serviceTaskHistory;
	
	@Autowired
	private CustomAuthenticator customAuthenticator;
	
	private static final Logger log = Logger.getLogger(UserViewPreparer.class);

	@Override
	public void execute(Request req, AttributeContext context) {
		log.info("UserViewPreparer called");
		
		User user = customAuthenticator.getUser();
		DeliveryTask highPriorityTask = serviceDeliveryTask.getHighestPriorityTask();
		int totalCompletedTask  = serviceTaskHistory.getTotalCompletedTaskFor(user);
		List<TaskHistory> allTaskForUser = new ArrayList<TaskHistory>();
		for(TaskHistory taskHistory : serviceTaskHistory.getAllTaskHistoryFor(user)){
			List<TaskHistory> taskHistories = serviceTaskHistory.getAllTaskHistoryFor(taskHistory.getDeliveryTask(),
					user);
			if(taskHistories.size() == 1){
				allTaskForUser.add(taskHistory);
			}else{
				if(!taskHistory.getTaskDeliveryStageEnum().equals(TaskDeliveryStageEnum.ACCEPTED)){
					allTaskForUser.add(taskHistory);
				}
			}
		}
		
		context.putAttribute("highPriorityTask", new Attribute(highPriorityTask));
		context.putAttribute("taskDeliveryStageCompleteEnum", new Attribute(TaskDeliveryStageEnum.COMPLETED));
		context.putAttribute("taskDeliveryStageDeclineEnum", new Attribute(TaskDeliveryStageEnum.DECLINED));
		context.putAttribute("taskDeliveryStageAcceptEnum", new Attribute(TaskDeliveryStageEnum.ACCEPTED));
		context.putAttribute("totalCompletedTask", new Attribute(totalCompletedTask));
		context.putAttribute("allTaskForUser", new Attribute(allTaskForUser));
		
	}

}
