<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 

<tiles:importAttribute name="highPriorityTask" ignore="true"/>
<tiles:importAttribute name="totalCompletedTask"/>
<c:if test="${totalCompletedTask lt 3}">
<div class="row" id="hpt">
	<c:choose>
		<c:when test="${not empty highPriorityTask}">
			<c:set var="task" value="${highPriorityTask }"></c:set>
			<div class="col-12">
				<div class="card mb-3">
					<div class="card-header" id="taskhead${task.id}">
						<h5 class="mb-0">
							<button class="btn btn-warning w-100 h-100 text-left" data-toggle="collapse" data-target="#task${task.id}" aria-expanded="true" aria-controls="task${task.id}">
							 	High Priority task
							</button>
						</h5>
					</div>
					
					<div id="task${task.id}" class="collapse" aria-labelledby="taskhead${task.id}" data-parent="#hpt">
						<div class="card-body">
							<div class="row _j-taskdetail">
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
								<div class="col-12 col-sm-6 mb-3 _jwctparent">
									<span class="">
										Want to accept task?
									</span>
									<select id="acceptTask" class="d-inline-block" data-existingTaskId="${task.id}">
										<option value="No">No</option>
										<option value="Yes">Yes</option>
									</select>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="col-12 text-secondary _font-size-10" id="noHighpriorityTaskMsg">
				No High priority task yet<br>
				Once added, You can take action
			</div>
		</c:otherwise>
	</c:choose>
</div>
</c:if>


<script id="newHighPriorityTask" type="text/x-jQuery-tmpl">
<div class="col-12">
	<div class="card-header" id="taskhead\${id}">
		<h5 class="mb-0">
			<button class="btn btn-warning w-100 h-100 text-left" data-toggle="collapse" data-target="#task\${id}" aria-expanded="true" aria-controls="task\${id}">
			 	High Priority task
			</button>
		</h5>
	</div>
	
	<div id="task\${id}" class="collapse" aria-labelledby="taskhead\${id}" data-parent="#hpt">
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
						Want to accept task?
					</span>
					<select id="acceptTask" class="d-inline-block" data-existingTaskId="\${id}">
						<option value="No">No</option>
						<option value="Yes">Yes</option>
					</select>
				</div>
			</div>
		</div>
	</div>
</div>
</script>
<script id="noHighPriorityTaskMsg" type="text/x-jQuery-tmpl">
<div class="col-12 text-secondary _font-size-10" id="noHighpriorityTaskMsg">
	No High priority task yet<br>
	Once added, You can take action
</div>
</script>