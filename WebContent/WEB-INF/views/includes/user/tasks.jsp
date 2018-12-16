<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 

<tiles:importAttribute name="allTaskForUser"/>
<tiles:importAttribute name="taskDeliveryStageCompleteEnum"/>
<tiles:importAttribute name="taskDeliveryStageDeclineEnum"/>
<tiles:importAttribute name="taskDeliveryStageAcceptEnum"/>

<div class="row">
	<div class="col-12">
		<span class="border-primary border-bottom">
			List of accepted task(s)
		</span>
		<input type="hidden" id="email" value="${loggedInUser.email }"/>
	</div>
	<div class="col-12" id="existingTaskListWrap">
		<c:choose>
			<c:when test="${fn:length(allTaskForUser) gt 0}">
				<span class="text-gary _font-size-10 pl-2">
					* Click task to see details of task
				</span>
				<c:forEach items="${allTaskForUser}" var="task">
					<div class="card mb-3">
						<div class="card-header" id="taskhead${task.id}">
							<h5 class="mb-0">
								<button class="btn btn-light w-100 text-left" data-toggle="collapse" data-target="#task${task.id}" aria-expanded="true" aria-controls="task${task.id}">
								 	${task.deliveryTask.title}
								</button>
							</h5>
						</div>
						
						<div id="task${task.id}" class="collapse" aria-labelledby="taskhead${task.id}" data-parent="#existingTaskListWrap">
							<div class="card-body">
								<div class="row _j-taskdetail">
									<div class="col-sm-6 col-12 mb-3">
										<span class="text-info text-uppercase">
											Title : 
										</span>
										<span class="text-secondary">
											${task.deliveryTask.title}
										</span>
									</div>
									<div class="col-sm-6 col-12 mb-3">
										<span class="text-info text-uppercase">
											Created by : 
										</span>
										<span class="text-secondary">
											${task.deliveryTask.addedBy.fullName}
										</span>
									</div>
									<div class="col-sm-6 col-12 mb-3">
										<span class="text-info text-uppercase">
											Created date : 
										</span>
										<span class="text-secondary">
											${task.deliveryTask.addedTimeStamp}
										</span>
									</div>
									<div class="col-12 col-sm-6 mb-3">
										<span class="text-info text-uppercase">
											Priority :  
										</span>
										<span class="text-secondary">
											${task.deliveryTask.taskPriorityEnum.displayName}
										</span>
									</div>
									<div class="col-12 col-sm-6 mb-3">
										<span class="text-info text-uppercase">
											Current Status : 
										</span>
										<span class="text-secondary _j-currentstatus">
											${task.deliveryTask.currentTaskStage.displayName} 
										</span>
									</div>
									<c:if
										test="${(taskDeliveryStageAcceptEnum eq  task.taskDeliveryStageEnum)}">
										<div class="col-12 col-sm-6 mb-3 _jwctparent">
											<span class="">
												Want to action on task?
											</span>
											<select class="d-inline-block _j-taskChangeStatus" data-taskHistoryId="${task.id}">
												<option value="">No</option>
												<option value="${taskDeliveryStageCompleteEnum.displayName }">${taskDeliveryStageCompleteEnum.displayName }</option>
												<option value="${taskDeliveryStageDeclineEnum.displayName }">${taskDeliveryStageDeclineEnum.displayName }</option>
											</select>
										</div>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<div class="pl-3 _font-size-10 mt-3" id="noTaskAddedMsg">
					No task accepted yet <br>
					Once accepted will be displayed here <br>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div> 

<script id="newAcceptedTask" type="text/x-jQuery-tmpl">
<div class="card mt-3">
	<div class="card-header" id="taskhead\${id}">
		<h5 class="mb-0">
			<button class="btn btn-light w-100 h-100 text-left" data-toggle="collapse" data-target="#task\${id}" aria-expanded="true" aria-controls="task\${id}">
			 	\${title}
			</button>
		</h5>
	</div>
	
		<div id="task\${id}" class="collapse" aria-labelledby="taskhead\${id}" data-parent="#existingTaskListWrap">
		<div class="card-body">
			<div class="row _j-taskdetail">
				<div class="col-sm-6 col-12 mb-3">
					<span class="text-info text-uppercase">
						Title : 
					</span>
					<span class="text-secondary">
						\${title}
					</span>
				</div>
				<div class="col-sm-6 col-12 mb-3">
					<span class="text-info text-uppercase">
						Created by : 
					</span>
					<span class="text-secondary">
						\${addedBy}
					</span>
				</div>
				<div class="col-sm-6 col-12 mb-3">
					<span class="text-info text-uppercase">
						Created date : 
					</span>
					<span class="text-secondary">
						\${addedTimeStamp}
					</span>
				</div>
				<div class="col-12 col-sm-6 mb-3">
					<span class="text-info text-uppercase">
						Priority :  
					</span>
					<span class="text-secondary">
						\${priority}
					</span>
				</div>
				<div class="col-12 col-sm-6 mb-3">
					<span class="text-info text-uppercase">
						Current Status : 
					</span>
					<span class="text-secondary _j-currentstatus">
						\${currentTaskStage} 
					</span>
				</div>
				<div class="col-12 col-sm-6 mb-3 _jwctparent">
					<span class="">
						Want to action on task?
					</span>
					<select class="d-inline-block _j-taskChangeStatus" data-taskHistoryId="\${taskHistoryId}">
						<option value="">No</option>
						<option value="\${completedenum }">\${completedenum }</option>
						<option value="\${declineenum }">\${declineenum}</option>
					</select>
				</div>
			</div>
		</div>
	</div>
</div>
</script>