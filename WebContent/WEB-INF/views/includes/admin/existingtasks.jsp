<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
 
<tiles:importAttribute name="deliveryTasks"/>
<tiles:importAttribute name="taskDeliveryStageAcceptedEnum"/>
<tiles:importAttribute name="taskDeliveryStageCancelEnum"/>
<tiles:importAttribute name="taskDeliveryStageNewEnum"/>
 
<div class="row">
	<div class="col-12">
		<span class="border-primary border-bottom">
			List of existing task(s)
		</span>
	</div>
	<div class="col-12" id="existingTaskListWrap">
		<c:choose>
			<c:when test="${fn:length(deliveryTasks) gt 0}">
				<span class="text-gary _font-size-10 pl-2">
					* Click task to see various state transitions
				</span>
				<div id="existingTaskListWrap">
					<c:forEach items="${deliveryTasks}" var="task">
						<div class="card mt-3">
							<div class="card-header" id="taskhead${task.id}">
								<h5 class="mb-0">
									<button class="btn btn-light w-100 text-left" data-toggle="collapse" data-target="#task${task.id}" aria-expanded="true" aria-controls="task${task.id}">
									 	${task.title}
									</button>
								</h5>
							</div>
							
							<div id="task${task.id}" class="collapse" aria-labelledby="taskhead${task.id}" data-parent="#existingTaskListWrap">
								<div class="card-body">
									<div class="row _j-taskdetail">
										<div class="col-12 mb-3 text-right">
											<button type="button" class="btn btn-secondary _j-task" data-deliveryTaskId="${task.id}">
												View History
											</button>
										</div>
										<div class="col-sm-6 col-12 mb-3">
											<span class="text-info text-uppercase">
												Title : 
											</span>
											<span class="text-secondary">
												${task.title}
											</span>
										</div>
										<div class="col-sm-6 col-12 mb-3">
											<span class="text-info text-uppercase">
												Created by : 
											</span>
											<span class="text-secondary">
												${task.addedBy.fullName}
											</span>
										</div>
										<div class="col-sm-6 col-12 mb-3">
											<span class="text-info text-uppercase">
												Created date : 
											</span>
											<span class="text-secondary">
												${task.addedTimeStamp}
											</span>
										</div>
										<div class="col-12 col-sm-6 mb-3">
											<span class="text-info text-uppercase">
												Priority :  
											</span>
											<span class="text-secondary">
												${task.taskPriorityEnum.displayName}
											</span>
										</div>
										<div class="col-12 col-sm-6 mb-3">
											<span class="text-info text-uppercase">
												Current Status : 
											</span>
											<span class="text-secondary _j-currentstatus">
												${task.currentTaskStage.displayName} 
											</span>
										</div>
										<c:if
											test="${(taskDeliveryStageNewEnum eq  task.currentTaskStage)}">
											<div class="col-12 col-sm-6 mb-3 _jwctparent">
												<span class="">
													Want to cancel task?
												</span>
												<select class="d-inline-block _j-taskCancelSelect" data-existingTaskId="${task.id}">
													<option value="No">No</option>
													<option value="Yes">Yes</option>
												</select>
											</div>
										</c:if>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<div class="pl-3 _font-size-10 mt-3" id="noTaskMsg">
					No task added yet <br>
					Once added will be displayed here, <br>
					Click on create task button to add new task 
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div> 



<script id="newTask" type="text/x-jQuery-tmpl">
	<div class="card mt-3">
		<div class="card-header" id="taskhead\${id}">
			<h5 class="mb-0">
				<button class="btn btn-light w-100 text-left" data-toggle="collapse" data-target="#task\${id}" aria-expanded="true" aria-controls="task\${id}">
				 	\${title}
				</button>
			</h5>
		</div>
		
		<div id="task\${id}" class="collapse" aria-labelledby="taskhead\${id}" data-parent="#existingTaskListWrap">
			<div class="card-body">
				<div class="row _j-taskdetail">
					<div class="col-12 mb-3 text-right">
						<button type="button" class="btn btn-secondary _j-task" data-deliveryTaskId="\${id}">
							View History
						</button>
					</div>
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
							Want to cancel task?
						</span>
						<select class="d-inline-block _j-taskCancelSelect" data-existingTaskId="\${id}">
							<option value="No">No</option>
							<option value="Yes">Yes</option>
						</select>
					</div>
				</div>
			</div>
		</div>
	</div>
</script>







