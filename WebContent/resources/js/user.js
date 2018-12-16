/**
 * All user event will be handle in this js
 */
var userObj = {
		init : function(){
			userObj.acceptHighPriorityTask();
			userObj.changeStatus();
		},
		/**
		 * On click of accept high priority task this method will be called <br>
		 * It will make a ajax call to server to update the status <br>
		 * And there is any other task in priority, server will return it <br>
		 * And that will be updated in view based on logged in user
		 * If user is already accepted three task than there won't be any high priority task for him/her
		 */
		acceptHighPriorityTask  : function(){
			$("#acceptTask").on("change",function(){
				var $dis = $(this);
				var taskId = $dis.attr("data-existingTaskId");
				var isTaskAccepted = $dis.val();
				if(isTaskAccepted=="Yes"){
					var url ="/websocket/accept/high/priority/task";
					var loggedUserInEmail = $("#email").val();
					var email = encodeURIComponent(loggedUserInEmail);
					dmswebsocket.sendWebSocketMessage(url+"/"+taskId+"/"+email, JSON.stringify({}));
				}
			});
		},
		/**
		 * Append new accepted task to user task history
		 */
		appendAcceptedTaskIntoExisitingTask : function(datas){
			var $newAcceptedTaskTemplate = userObj.getNewAcceptedTaskTemp().tmpl(datas);
			$("#noTaskAddedMsg").remove();
			$("#existingTaskListWrap").append($newAcceptedTaskTemplate);
			userObj.changeStatus();
		},
		/**
		 * New Accepted Task Template
		 */
		getNewAcceptedTaskTemp : function(){
			return $("#newAcceptedTask");
		},
		/**
		 * Remove High priority task 
		 */
		removeHighPriorityTask : function(){
			$("#hpt").children().remove();
		},
		/**
		 * To Push new High priority task this method will be used
		 * @param datas
		 */
		appendNewHighPriorityTaskInView : function(datas){
			var $newHighPriorityTaskView = userObj.getNewHighPriorityTaskViewTemplate().tmpl(datas);
			$("#hpt").append($newHighPriorityTaskView);
			userObj.acceptHighPriorityTask();
		},
		
		getNewHighPriorityTaskViewTemplate : function(){
			return $("#newHighPriorityTask");
		},
		showNoHighPriorityTaskMsg : function(){
			$("#hpt").append($(userObj.getNoHighPriorityTaskMsgTemplate().tmpl()));
		},
		/**
		 * To get no high priority task this method will be used
		 * @returns
		 */
		getNoHighPriorityTaskMsgTemplate : function(){
			return $("#noHighPriorityTaskMsg");
		},
		/** 
		 * If user want to change a task status which is accept by him/her <br>
		 * This method will be used
		 */
		changeStatus  : function(){
			$("._j-taskChangeStatus").on("change",function(){
				var $dis = $(this);
				var taskHistoryId = $dis.attr("data-taskHistoryId");
				var isTaskAccepted = $dis.val();
				if(!(isTaskAccepted=="NO")){
					var url ="/websocket/action/on/accepted/task";
					var loggedUserInEmail = $("#email").val();
					var email = encodeURIComponent(loggedUserInEmail);
					dmswebsocket.sendWebSocketMessage(url+"/"+taskHistoryId+"/"+isTaskAccepted+"/"+email, JSON.stringify({}));
				}
			});
		},
		/**
		 * Update task once user take action on a task status
		 */
		updateTaskStatus : function(datas){
			var $actionModificationSelect  =$("[data-taskHistoryId='"+datas.taskHistoryId+"']");
			var $detailParent = $actionModificationSelect.closest("._j-taskdetail");
			var $currentStatus = $detailParent.find("._j-currentstatus");
			$currentStatus.html(datas.actiontakenemun);
			$actionModificationSelect.closest("._jwctparent").remove();
			
		},
}

$(document).ready(function(){
	userObj.init();
});