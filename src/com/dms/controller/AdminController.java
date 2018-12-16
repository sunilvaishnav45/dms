package com.dms.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dms.Enum.TaskDeliveryStageEnum;
import com.dms.Enum.TaskPriorityEnum;
import com.dms.custom.property.CustomTaskDeliveryStageEnumEditor;
import com.dms.custom.property.CustomTaskPriorityEnumEditor;
import com.dms.database.mapping.DeliveryTask;
import com.dms.database.mapping.User;
import com.dms.database.mapping.UserRoleMapping;
import com.dms.dto.DtoDeliveryTask;
import com.dms.dto.DtoTaskHistories;
import com.dms.login.filter.CustomAuthenticator;
import com.dms.service.ServiceDeliveryTask;
import com.dms.service.ServiceTaskHistory;
import com.dms.service.ServiceUser;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger log = Logger.getLogger(AdminController.class);
	
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
	
	
	/**
	 * Landing page for admin
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value={"","/"}, method=RequestMethod.GET)
	@Secured("ROLE_admin")
	public String adminLandingPage(Model model, @ModelAttribute("dtoDeliveryTask") DtoDeliveryTask dtoDeliveryTask) {
		log.info("adminLandingPage method called");
		User user =  customAuthenticator.getUser();
		log.info("user name"+user.getFullName());
		model.addAttribute("loggedInUser", user);
		return "admin";
	}
	/**
	 * Ajax call to fetch all the task history for a task id
	 * @param model
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value="/task/history", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	@Secured("ROLE_admin")
	public List<DtoTaskHistories> getTaskHistoryiesFor(Model model,@RequestParam("taskId") Integer taskId){
		log.info("admin wants to fetch all the task history for "+taskId);
		List<DtoTaskHistories> taskHistories = new ArrayList<DtoTaskHistories>();
		DeliveryTask deliveryTask = serviceDeliveryTask.getById(taskId);
		if(deliveryTask!=null){
			taskHistories = serviceTaskHistory.getDtoTaskHistories(deliveryTask);
		}
		return taskHistories;
	}
	
	/**
	 * To save a new task this method will be called <br>
	 * Before saving the {@link DeliveryTask} in db it will check every thing is valid or not <br>
	 * If not than it won't allow to save {@link DeliveryTask} in db
	 * @param title
	 * @param priority
	 * @param email
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings({ "unchecked"})
	@MessageMapping("/task/save/{title}/{priority}/{email}")
	public void webSocketSaveNewTask(@DestinationVariable("title") String title,
			@DestinationVariable("priority") String priority, @DestinationVariable("email") String email)
			throws UnsupportedEncodingException {
		
		String decodedTitle = URLDecoder.decode(title,"UTF-8").toString();
		String decodedPriority = URLDecoder.decode(priority,"UTF-8").toString();
		String decodedEmail = URLDecoder.decode(email,"UTF-8").toString();
		TaskPriorityEnum priorityEnum = TaskPriorityEnum.getTaskPriorityEnumFor(decodedPriority);
		
		User loggedInUser =  serviceUser.getUserFor(decodedEmail);
		log.info("logged in user"+loggedInUser.getFullName());
		
		if(decodedTitle!=null && !decodedTitle.isEmpty() && priorityEnum!=null){
			log.info("admin wants to save new delivery task for title" + decodedTitle + "and priority"
					+ priorityEnum.getDisplayName());
			
			DeliveryTask deliveryTask = serviceDeliveryTask.saveDeliveryTaskFor(decodedTitle, priorityEnum, loggedInUser);
			
			if(deliveryTask !=null){
				log.info("Saved Delivery task is not null");
				
				List<UserRoleMapping> adminRoleMappings = serviceUser.getAllRoleBasedUser("Admin");
				log.info("total admin fetched"+adminRoleMappings.size());
				for(UserRoleMapping adminRoleMapping : adminRoleMappings){
					User admin = adminRoleMapping.getUser();
					log.info("Returning delivery task to "+admin.getFullName());
					JSONObject jsonObject = serviceDeliveryTask.convertDeliveryTaskIntoJSONObject(deliveryTask);
					simpMessageSendingOperations.convertAndSendToUser(admin.getEmail(),"/topic/new/task/admin", jsonObject);
				}
				
				List<UserRoleMapping> userRoleMappings = serviceUser.getAllRoleBasedUser("User");
				DeliveryTask maximumPriorityTask = serviceDeliveryTask.getHighestPriorityTask();
				JSONObject jsonObject = serviceDeliveryTask.convertDeliveryTaskIntoJSONObject(maximumPriorityTask);
				if(maximumPriorityTask == null)
					jsonObject.put("noHighPriorityTask", true);
				log.info("total user fetched"+userRoleMappings.size());
				for(UserRoleMapping userRoleMapping : userRoleMappings){
					User user = userRoleMapping.getUser();
					int maximumAcceptedTask  = serviceTaskHistory.getTotalCompletedTaskFor(user);
					log.info("Returning delivery task to "+user.getFullName()+" ,maximumAcceptedTask" +maximumAcceptedTask);
					jsonObject.put("maximumAcceptedTask", maximumAcceptedTask);
					simpMessageSendingOperations.convertAndSendToUser(user.getEmail(), "/topic/new/task/user", jsonObject);
				}
			}
		}
	}
	
	/**
	 * To cancel a new task this method will be called <br>
	 * Before cancel the {@link DeliveryTask} in db it will check every thing is valid or not <br>
	 * If not than it won't allow to cancel {@link DeliveryTask} in db
	 * If a high priority task is cancelled than user should see new high priority task data <br>
	 * This method will return high priority task data to user
	 * @param title
	 * @param priority
	 * @param email
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings({ "unchecked"})
	@MessageMapping("/task/cancel/{id}/{email}")
	public void webSocketCancelTask(@DestinationVariable("id") Integer taskId, @DestinationVariable("email") String email)
			throws UnsupportedEncodingException {
		String decodedEmail = URLDecoder.decode(email,"UTF-8").toString();
		
		User loggedInUser =  serviceUser.getUserFor(decodedEmail);
		log.info("logged in user"+loggedInUser.getFullName()+"changeTaskStatusToCancelFor called for taskId"+taskId);
		
		DeliveryTask deliveryTask = serviceDeliveryTask.getById(taskId);
		TaskDeliveryStageEnum deliveryStageEnum = deliveryTask.getCurrentTaskStage();
		if (deliveryStageEnum.equals(TaskDeliveryStageEnum.NEW) && deliveryTask!=null){
			DeliveryTask cancelledTask = serviceDeliveryTask.updateDeliveryTaskFor(deliveryTask,TaskDeliveryStageEnum.CANCELLED,loggedInUser);
			
			List<UserRoleMapping> adminRoleMappings = serviceUser.getAllRoleBasedUser("Admin");
			log.info("total admin fetched"+adminRoleMappings.size());
			for(UserRoleMapping adminRoleMapping : adminRoleMappings){
				User admin = adminRoleMapping.getUser();
				log.info("Returning delivery task to "+admin.getFullName());
				JSONObject jsonObject = serviceDeliveryTask.convertDeliveryTaskIntoJSONObject(cancelledTask);
				simpMessageSendingOperations.convertAndSendToUser(admin.getEmail(),"/topic/cancel/task/admin", jsonObject);
			}

			List<UserRoleMapping> userRoleMappings = serviceUser.getAllRoleBasedUser("User");
			DeliveryTask maximumPriorityTask = serviceDeliveryTask.getHighestPriorityTask();
			JSONObject jsonObject = serviceDeliveryTask.convertDeliveryTaskIntoJSONObject(maximumPriorityTask);
			if(maximumPriorityTask == null)
				jsonObject.put("noHighPriorityTask", true);
			log.info("total user fetched"+userRoleMappings.size());
			for(UserRoleMapping userRoleMapping : userRoleMappings){
				User user = userRoleMapping.getUser();
				int maximumAcceptedTask  = serviceTaskHistory.getTotalCompletedTaskFor(user);
				log.info("Returning delivery task to "+user.getFullName()+" ,maximumAcceptedTask" +maximumAcceptedTask);
				jsonObject.put("maximumAcceptedTask", maximumAcceptedTask);
				simpMessageSendingOperations.convertAndSendToUser(user.getEmail(), "/topic/cancel/task/user", jsonObject);
			}
		
		}
	}
	
	@InitBinder("dtoDeliveryTask")
	public void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(TaskDeliveryStageEnum.class, "currentTaskStage", new CustomTaskDeliveryStageEnumEditor());
		binder.registerCustomEditor(TaskPriorityEnum.class, "taskPriorityEnum", new CustomTaskPriorityEnumEditor());
	}
}
