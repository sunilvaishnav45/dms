/**
 * To handle all the events in admin page this object will be used
 */
var adminObj = {
	
		init  : function(){
			adminObj.showHistoryOfTask();
			adminObj.cancelExistingTask();
			adminObj.addNewTask();
		},
		/**
		 * To add new task this method will be triggered <br>
		 * It will make a websocket request to server
		 */
		addNewTask :  function(){
			$("#saveBtn").on("click",function(){
				var $form = $(this).closest("form");
				var url ="/websocket/task/save";
				var titleVal = $("#title").val().trim();
				var priorityVal = $("#taskPriorityEnum").val();
				var email = $("#email").val();
				var title = encodeURIComponent(titleVal);
				var priority = encodeURIComponent(priorityVal);
				dmswebsocket.sendWebSocketMessage(url+"/"+title+"/"+priority+"/"+email, JSON.stringify({}));
			})
		},
		/**
		 * After a task is saved by admin 
		 * This method will be triggered by websocket.js @see webSocketShowNewTaskToAdmins()
		 * It will remove first no msg wrapper
		 * Than it will show newly added task to each admin in real time
		 * @param datas
		 */
		appendNewTaskInView : function(datas){
			$("#noTaskMsg").remove();
			var $newTaskView = adminObj.getTaskViewTemplate().tmpl(datas);
			$("#existingTaskListWrap").append($newTaskView);
			adminObj.cancelExistingTask();
			$("#closeBtn").click();
		},
		/**
		 * To get template of new added task <br>
		 * @returns
		 */
		getTaskViewTemplate : function(){
			return $("#newTask");
		},
		/**
		 * Make ajax call to show the history of clicked Task <br>
		 * On click of row the history modal will be open
		 */
		showHistoryOfTask :  function(){
			$("#existingTaskListWrap").on("click","._j-task",function(){
				var $dis = $(this);
				var deliveryTaskId = $dis.attr("data-deliveryTaskId");
				if(deliveryTaskId){
					$.ajax({
						  method: "POST",
						  url: "/dms/admin/task/history",
						  data: {taskId: deliveryTaskId}
						}).done(function( dtoTaskHistory ) {
						    if(dtoTaskHistory!=null){
						    	var $taskHistoryModalBtn = $("#taskHistoryModalBtn");
						    	var $taskHistoryModal = $("#taskHistoryModal");
						    	var $taskHistoryModalBody = $taskHistoryModal.find(".modal-body ._historydetail");
						    	$taskHistoryModalBody.html("");
						    	$.each(dtoTaskHistory,function(index,value){
						    		console.log(value);
						    		var $taskHistoryModalTemplate = adminObj.getTaskHistoryModalTemplate(value);
							    	$taskHistoryModalBody.append($taskHistoryModalTemplate);
						    	})
						    	$taskHistoryModalBtn.click();
						    }else{
						    	alert("Somthing went wrong, Please try again!")
						    }
						  });
					}
				
				});
			},
			/**
			 * To get template of task history modal this method will be used
			 */
			getTaskHistoryModalTemplate : function(datas){
				var $parentWrap = $("<div class='mb-2'><div>");
				var $row = $("<div class='row'></div>");
				var $userName = $("<div class='col-4'>"+datas.addedBy+"</div>");
				var $addedTime = $("<div class='col-4'>"+datas.addedTimeStamp+"</div>");
				var $taskStatus = $("<div class='col-4'>"+datas.taskDeliveryStageEnum+"</div>");
				$row.append($userName);
				$row.append($taskStatus);
				$row.append($addedTime);
				$parentWrap.append($row);
				return $parentWrap;
			},
			/**
			 * To cancel a existing task this method will be used
			 * @see webSocketShowAcceptedTaskDetailModalAdmin()
			 */
			cancelExistingTask  : function(){
				$("._j-taskCancelSelect").on("change",function(){
					var $dis = $(this);
					var deliveryTaskId  = $dis.attr("data-existingTaskId");
					var url ="/websocket/task/cancel";
					var loggedUserInEmail = $("#email").val();
					var email = encodeURIComponent(loggedUserInEmail);
					dmswebsocket.sendWebSocketMessage(url+"/"+deliveryTaskId+"/"+email, JSON.stringify({}));
				});
			},
			/**
			 * Update Cancel task detail in view once task is cancel<br>
			 * Calling from websocket js @see webSocketUpdateTaskDetailToAdminAfterCancelATask
			 */
			updateTaskDetailOfCancelledTask : function(datas){
				var $parentCollapse = $("#task"+datas.id)
				var $currentStatus = $parentCollapse.find("._j-currentstatus");
				var $changeStatusSelect = $parentCollapse.find("._jwctparent");
				$currentStatus.html(datas.currentTaskStage);
				$changeStatusSelect.remove();
			},
			/**
			 * To show task detail which is accepted by a user<br>
			 * This method will trigger a modal <br>
			 * @see webSocketShowAcceptedTaskDetailModalAdmin()
			 * Also update the task status 
			 */
			showAcceptedTaskDetailModal : function(datas){
				var $modalDetails = adminObj.getAcceptedTaskDetailModalTemplate().tmpl(datas);
				var $acceptedTaskDetailWrap = $("#acceptedTaskDetails");
				$acceptedTaskDetailWrap.html("");
				$acceptedTaskDetailWrap.append($modalDetails);
				$("#acceptedTaskModalBtn").click();
				
				//update task status
				var $parentCollapse = $("#task"+datas.id)
				var $currentStatus = $parentCollapse.find("._j-currentstatus");
				var $changeStatusSelect = $parentCollapse.find("._jwctparent");
				$currentStatus.html(datas.currentTaskStage);
				$changeStatusSelect.remove();
			},
			/**
			 * To get template of accepted task detail <br>
			 * @returns
			 */
			getAcceptedTaskDetailModalTemplate : function(){
				return $("#acceptedTask");
			},
			
			/**
			 * To show task detail which is actioned by a user<br>
			 * This method will trigger a modal <br>
			 * Also update the task status 
			 */
			showActionTaskDetailModal : function(datas){
				var $modalDetails = adminObj.getActionTaskDetailModalTemplate().tmpl(datas);
				var $actionTaskDetailWrap = $("#actionTaskDetails");
				$actionTaskDetailWrap.html("");
				$actionTaskDetailWrap.append($modalDetails);
				$("#actionTaskModalBtn").click();
				
				//update task status
				var $parentCollapse = $("#task"+datas.id)
				var $currentStatus = $parentCollapse.find("._j-currentstatus");
				var $changeStatusSelect = $parentCollapse.find("._jwctparent");
				$currentStatus.html(datas.currentTaskStage);
				$changeStatusSelect.remove();
			},
			/**
			 * To get template of action task detail <br>
			 * @returns
			 */
			getActionTaskDetailModalTemplate : function(){
				return $("#actionOnATask");
			},
			
			
}
$(document).ready(function(){
	adminObj.init()
});