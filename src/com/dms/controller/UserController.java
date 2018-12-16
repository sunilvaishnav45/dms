package com.dms.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dms.Enum.TaskDeliveryStageEnum;
import com.dms.database.mapping.DeliveryTask;
import com.dms.database.mapping.TaskHistory;
import com.dms.database.mapping.User;
import com.dms.database.mapping.UserRoleMapping;
import com.dms.login.filter.CustomAuthenticator;
import com.dms.service.ServiceDeliveryTask;
import com.dms.service.ServiceTaskHistory;
import com.dms.service.ServiceUser;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private CustomAuthenticator customAuthenticator;
	
	@Autowired
	private SimpMessageSendingOperations simpMessageSendingOperations;
	
	@Autowired
	private ServiceUser serviceUser;
	
	@Autowired
	private ServiceTaskHistory serviceTaskHistory ;
	
	@Autowired
	private ServiceDeliveryTask  serviceDeliveryTask;
	
	private static Logger log = Logger.getLogger(UserController.class);
	
	@RequestMapping(value={"","/"},method=RequestMethod.GET)
	@Secured("ROLE_user")
	public String userLandingPage(Model model, HttpServletRequest req, HttpServletResponse res) {
		log.info("deliveryManLandingPage method called");
		User user =  customAuthenticator.getUser();
		model.addAttribute("loggedInUser", user);
		return "user";
	}
	
	/**
	 * To accept a task by user <br>
	 * If user already having 3 incomplete task, then there won't be any action<br>
	 * Will return accepted task to each admin <br>
	 * Will return new high priority task to each user <br>
	 * Will also return accepted task to same user to push into his/her accepted task list
	 * @param taskId
	 * @param email
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings({ "unchecked"})
	@MessageMapping("/accept/high/priority/task/{taskId}/{email}")
	public void acceptHighPriorityTask(@DestinationVariable("taskId") Integer taskId,
			@DestinationVariable("email") String email) throws UnsupportedEncodingException {
		String decodedEmail = URLDecoder.decode(email,"UTF-8").toString();
		
		User loggedInUser =  serviceUser.getUserFor(decodedEmail);
		log.info("logged in user"+loggedInUser.getFullName()+"acceptTaskFor method called for"+ taskId);
		
		DeliveryTask deliveryTask  =  serviceDeliveryTask.getById(taskId);
		DeliveryTask highPriorityTask =  serviceDeliveryTask.getHighestPriorityTask();
		
		if(deliveryTask!=null && (taskId == highPriorityTask.getId())){
			int completedTaskCount = serviceTaskHistory.getTotalCompletedTaskFor(loggedInUser);
			if(completedTaskCount < 3){
				DeliveryTask updatedDeliveryTask = serviceDeliveryTask.updateDeliveryTaskFor(deliveryTask,TaskDeliveryStageEnum.ACCEPTED,loggedInUser);
			
				if(updatedDeliveryTask !=null){
					log.info("updated Delivery task is not null");
					//sending accepted task to all admin
					List<UserRoleMapping> adminRoleMappings = serviceUser.getAllRoleBasedUser("Admin");
					log.info("total admin fetched"+adminRoleMappings.size());
					for(UserRoleMapping adminRoleMapping : adminRoleMappings){
						User admin = adminRoleMapping.getUser();
						log.info("Returning delivery task to "+admin.getFullName());
						JSONObject jsonObject = serviceDeliveryTask.convertDeliveryTaskIntoJSONObject(updatedDeliveryTask);
						jsonObject.put("acceptedBy", loggedInUser.getFullName());
						simpMessageSendingOperations.convertAndSendToUser(admin.getEmail(),"/topic/accepted/task/admin", jsonObject);
					}
					
					//sending new high priority task to each user with their pending accepted task
					List<UserRoleMapping> userRoleMappings = serviceUser.getAllRoleBasedUser("User");
					DeliveryTask maximumPriorityTask = serviceDeliveryTask.getHighestPriorityTask();
					JSONObject jsonObject = serviceDeliveryTask.convertDeliveryTaskIntoJSONObject(maximumPriorityTask);
					if(maximumPriorityTask == null)
						jsonObject.put("noHighPriorityTask", true);
					else
						jsonObject.put("noHighPriorityTask", false);
					
					log.info("total user fetched"+userRoleMappings.size());
					for(UserRoleMapping userRoleMapping : userRoleMappings){
						User user = userRoleMapping.getUser();
						int maximumAcceptedTask  = serviceTaskHistory.getTotalCompletedTaskFor(user);
						log.info("Returning delivery task to "+user.getFullName()+" ,maximumAcceptedTask" +maximumAcceptedTask);
						jsonObject.put("maximumAcceptedTask", maximumAcceptedTask);
						simpMessageSendingOperations.convertAndSendToUser(user.getEmail(), "/topic/new/high/priority/task/user", jsonObject);
					}
					
					//To display Accepted Task to user send a JSON to particular logged in user
					TaskHistory latestTaskHistory = serviceTaskHistory.getAllTaskHistoryFor(updatedDeliveryTask, loggedInUser).get(0);
					JSONObject accpetedTaskJson = serviceDeliveryTask.convertDeliveryTaskIntoJSONObject(deliveryTask);
					accpetedTaskJson.put("completedenum", TaskDeliveryStageEnum.COMPLETED.getDisplayName());
					accpetedTaskJson.put("declineenum", TaskDeliveryStageEnum.DECLINED.getDisplayName());
					accpetedTaskJson.put("taskHistoryId", latestTaskHistory.getId());
					simpMessageSendingOperations.convertAndSendToUser(loggedInUser.getEmail(), "/topic/accepted/high/priority/task/user", accpetedTaskJson);
				}
			}
		}
	
	}
	
	/**
	 * When user is trying to complete or decline any task than this method will be called<br>
	 * Will be used to send taken action message to all Admin
	 * @param taskId
	 * @param email
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings({ "unchecked"})
	@MessageMapping("/action/on/accepted/task/{taskId}/{action}/{email}")
	public void actionOnAcceptedTaskByUser(@DestinationVariable("taskId") Integer taskHistoryId,
			@DestinationVariable("action") String action, @DestinationVariable("email") String email)
			throws UnsupportedEncodingException {
		String decodedEmail = URLDecoder.decode(email,"UTF-8").toString();
		String changeToStatusName = URLDecoder.decode(action,"UTF-8").toString();
		
		User loggedInUser =  serviceUser.getUserFor(decodedEmail);
		log.info("logged in user"+loggedInUser.getFullName()+"acceptTaskFor method called for"+ taskHistoryId);
		TaskDeliveryStageEnum  taskDeliveryStageEnum = TaskDeliveryStageEnum.getTaskDeliveryEnumFor(changeToStatusName);
		
		TaskHistory taskHistory = serviceTaskHistory.getById(taskHistoryId);
		if(taskHistory!=null && taskDeliveryStageEnum !=null){
			log.info("a valid task history is updating");
			DeliveryTask deliveryTask = taskHistory.getDeliveryTask();
			if (deliveryTask.getCurrentTaskStage().equals(TaskDeliveryStageEnum.ACCEPTED)) {
				log.info("a valid task history is updating for valid user  and accepted task");
				if(taskDeliveryStageEnum!=null){
					log.info("updating task into"+taskDeliveryStageEnum.getDisplayName());
					DeliveryTask updatedDeliveryTask =  serviceDeliveryTask.updateDeliveryTaskFor(deliveryTask, taskDeliveryStageEnum,loggedInUser);
					
					//sending action taken task to all admin
					List<UserRoleMapping> adminRoleMappings = serviceUser.getAllRoleBasedUser("Admin");
					log.info("total admin fetched"+adminRoleMappings.size());
					for(UserRoleMapping adminRoleMapping : adminRoleMappings){
						User admin = adminRoleMapping.getUser();
						log.info("Returning delivery task to "+admin.getFullName());
						JSONObject jsonObject = serviceDeliveryTask.convertDeliveryTaskIntoJSONObject(updatedDeliveryTask);
						jsonObject.put("actionby", loggedInUser.getFullName());
						simpMessageSendingOperations.convertAndSendToUser(admin.getEmail(),"/topic/action/taken/task/admin", jsonObject);
					}
					
					//To display Accepted Task to user send a JSON to particular logged in user
					JSONObject accpetedTaskJson = serviceDeliveryTask.convertDeliveryTaskIntoJSONObject(deliveryTask);
					accpetedTaskJson.put("actiontakenemun", updatedDeliveryTask.getCurrentTaskStage().getDisplayName());
					accpetedTaskJson.put("taskHistoryId", taskHistoryId);
					simpMessageSendingOperations.convertAndSendToUser(loggedInUser.getEmail(), "/topic/action/taken/user", accpetedTaskJson);
				}
				
			}
		}
	}
}
