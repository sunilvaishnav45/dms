var dmswebsocket ={
	stompClient : null,
	stompConnectionInterval : null,
	init : function(){
		dmswebsocket.webSocketConnection();
	},
	
	fetchConnection : function(){
		var headers = {};
		var options = {debug: false, devel: true};
		var connectURL = "/dms/dms-websocket";
		var socket = new SockJS(connectURL,headers,options);
		return socket;
	},

	/***
	 * This function is used to do the websocket connection and subscription
	 */
	webSocketConnection : function(){
		var socket = dmswebsocket.fetchConnection();
		dmswebsocket.stompClient= Stomp.over(socket);
		var headers = {};
		/*appwebsocket.stompClient.debug = null;*/
		dmswebsocket.stompClient.connect(headers, function(frame) {
			dmswebsocket.webSocketSubForEvent();
			dmswebsocket.webSocketShowNewTaskToAdminsAfterAddingNewTask();
			dmswebsocket.webSocketShowNewHighPriorityTaskToUsersAfterAddingNewTask();
			dmswebsocket.webSocketUpdateTaskDetailToAdminAfterCancelATask();
			dmswebsocket.webSocketShowNewHighPriorityTaskToUsersAfterCancellingATaskByAdmin();
			dmswebsocket.webSocketShowAcceptedTaskDetailModalToAdminAfterAcceptingByUser();
			dmswebsocket.webSocketShowNewHighPriorityTaskToUsersAfterAcceptingByUser();
			dmswebsocket.webSocketPushNewAcceptedTaskToUserTaskListsAfterAcceptingByUser();
			dmswebsocket.webSocketShowModalToAdminAfterActionTakenByAUserOnTask();
			dmswebsocket.webSocketUpdateTaskHistoryAfterActionTakenByUser();
		}, function(message) {
			/**
			 * This call on web socket disconnect
			 */
			dmswebsocket.stompConnectionInterval = setInterval(function(){
				if(navigator.onLine){
					if(!dmswebsocket.stompClient.connected){
						dmswebsocket.webSocketConnection();
					}else{
						clearInterval(dmswebsocket.stompConnectionInterval);
					}
				}
			},15000);
		});
	},
	
	webSocketSubForEvent :  function(){
		var subscribeUrl ="/user/topic/senddata";
		dmswebsocket.stompClient.subscribe(subscribeUrl, function(response){
			var jsonObj = JSON.parse(response.body);
			console.log(jsonObj);
        });
	},
	/**
	 * When a task is added by admin in websocket response this method will be called<br>
	 * Further this method will show added task to all admin
	 */
	webSocketShowNewTaskToAdminsAfterAddingNewTask :  function(){
		var subscribeUrl ="/user/topic/new/task/admin";
		dmswebsocket.stompClient.subscribe(subscribeUrl, function(response){
			var jsonObj = JSON.parse(response.body);
			adminObj.appendNewTaskInView(jsonObj)
        });
	},
	/**
	 * When a task is added by admin in websocket response this method will be called<br>
	 * Further this method will show added task to all user
	 * If user is already accepted 3 tasks than this method won't show anything to user
	 */
	webSocketShowNewHighPriorityTaskToUsersAfterAddingNewTask :  function(){
		var subscribeUrl ="/user/topic/new/task/user";
		dmswebsocket.stompClient.subscribe(subscribeUrl, function(response){
			var jsonObj = JSON.parse(response.body);
			userObj.removeHighPriorityTask();
			console.log(jsonObj.noHighPriorityTask)
			if(jsonObj.maximumAcceptedTask < 3 && !jsonObj.noHighPriorityTask)
				userObj.appendNewHighPriorityTaskInView(jsonObj);
			else
				userObj.showNoHighPriorityTaskMsg();
		});
	},
	/**
	 * Update task details to admins onces task is cancelled by admin <br>
	 */
	webSocketUpdateTaskDetailToAdminAfterCancelATask : function(){
		var subscribeUrl ="/user/topic/cancel/task/admin";
		dmswebsocket.stompClient.subscribe(subscribeUrl, function(response){
			var jsonObj = JSON.parse(response.body);
				adminObj.updateTaskDetailOfCancelledTask(jsonObj)
		});
	},
	/**
	 * Update new high priority task to user once a task is cancel by admin
	 */
	webSocketShowNewHighPriorityTaskToUsersAfterCancellingATaskByAdmin :function(){
		var subscribeUrl ="/user/topic/cancel/task/user";
		dmswebsocket.stompClient.subscribe(subscribeUrl, function(response){
			var jsonObj = JSON.parse(response.body);
			userObj.removeHighPriorityTask();
			if(jsonObj.maximumAcceptedTask < 3 && !jsonObj.noHighPriorityTask)
				userObj.appendNewHighPriorityTaskInView(jsonObj);
			else
				userObj.showNoHighPriorityTaskMsg();
		});
	},
	/**
	 * When a High priority task is accepted by a user this method will be called<br>
	 * It'll show all the details of task accepted by user <br>
	 */
	webSocketShowAcceptedTaskDetailModalToAdminAfterAcceptingByUser :  function(){
		var subscribeUrl ="/user/topic/accepted/task/admin";
		dmswebsocket.stompClient.subscribe(subscribeUrl, function(response){
			var jsonObj = JSON.parse(response.body);
			if(jsonObj)
				adminObj.showAcceptedTaskDetailModal(jsonObj);
		});
	},
	/**
	 * When a user accepted a high priority task <br>
	 * To show new high priority task to each user this method will be trigered <br>
	 * Based on due accepted task this method will show new high priroty task to each user <br>
	 * And will remove old high priority task
	 * 
	 */
	webSocketShowNewHighPriorityTaskToUsersAfterAcceptingByUser :  function(){
		var subscribeUrl ="/user/topic/new/high/priority/task/user";
		dmswebsocket.stompClient.subscribe(subscribeUrl, function(response){
			var jsonObj = JSON.parse(response.body);
			userObj.removeHighPriorityTask();
			if(jsonObj.maximumAcceptedTask < 3 && !jsonObj.noHighPriorityTask)
				userObj.appendNewHighPriorityTaskInView(jsonObj);
			else
				userObj.showNoHighPriorityTaskMsg();
		});
	},
	/**
	 * To show accepted task to user, into accepted task list
	 */
	webSocketPushNewAcceptedTaskToUserTaskListsAfterAcceptingByUser : function(){
		var subscribeUrl ="/user/topic/accepted/high/priority/task/user";
		dmswebsocket.stompClient.subscribe(subscribeUrl, function(response){
			var jsonObj = JSON.parse(response.body);
			userObj.appendAcceptedTaskIntoExisitingTask(jsonObj)
		});
	},
	/**
	 * This method is user to notify Admin that a task status has been modified by user<br>
	 * It will open modal to each admin
	 */
	webSocketShowModalToAdminAfterActionTakenByAUserOnTask : function(){
		var subscribeUrl ="/user/topic/action/taken/task/admin";
		dmswebsocket.stompClient.subscribe(subscribeUrl, function(response){
			var jsonObj = JSON.parse(response.body);
			adminObj.showActionTaskDetailModal(jsonObj);
		});
	},
	/**
	 * Once a task status has been modified by user
	 * This method will update task status in real time
	 */
	webSocketUpdateTaskHistoryAfterActionTakenByUser : function(){
		var subscribeUrl ="/user/topic/action/taken/user";
		dmswebsocket.stompClient.subscribe(subscribeUrl, function(response){
			var jsonObj = JSON.parse(response.body);
			userObj.updateTaskStatus(jsonObj);
		});
	},
	
	/**
	 * This function is used to send messages to the server
	 */
	sendWebSocketMessage : function(dataSendURL,datas){
		dmswebsocket.stompClient.send(dataSendURL, {},datas);
	},
		
};

$(document).ready(function(){
	dmswebsocket.init();
});
