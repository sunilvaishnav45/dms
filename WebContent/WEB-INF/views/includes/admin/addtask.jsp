<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 

<tiles:importAttribute name="taskPriorityEnum"/>
<tiles:importAttribute name="taskDeliveryStageNewEnum"/>

<%--Add task Modal --%>
<form:form modelAttribute="dtoDeliveryTask" action="/task/save"> 
	<form:hidden path="email" value="${loggedInUser.email }"/>
	<div class="modal fade" id="addTaskModal" tabindex="-1" role="dialog" aria-labelledby="addTaskModal" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="addTaskModalLabel">Create New Task</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					  <span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
				  	<div class="row">
				  		<div class="col-12 mb-3">
					  		<div class="form-group">
							  <label for="title">Title : </label>
							  <form:textarea path="title" cssClass="form-control _resize-none" rows="2"/>
							</div>
						</div>
						<div class="col-md-6 col-12 mb-3">
							<div class="form-group">
							  <label for="sel1">Priority:</label>
							  <form:select cssClass="form-control" path="taskPriorityEnum">
							  	<c:forEach items="${taskPriorityEnum}" var="tpe">
							  		<form:option value="${tpe.displayName}">${tpe.displayName}</form:option>
							  	</c:forEach>
							  </form:select>
							</div>
						</div>
						<div class="col-md-6 col-12 mb-3">
						   <div class="form-group">
							  <label for="sel1">Task stage:</label>
							   <form:select cssClass="form-control" path="currentTaskStage">
									<form:option value="${taskDeliveryStageNewEnum.displayName }">
										${taskDeliveryStageNewEnum.displayName }
									</form:option>
								</form:select>
							</div>
						</div>
				  	</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="saveBtn" class="btn btn-primary">Save</button>
				  	<button type="button" id="closeBtn" class="btn btn-secondary" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
 </form:form>
<%--History Modal end--%>

<%--To show accepted task detail this modal will be used --%>
<button type="button" id="acceptedTaskModalBtn" class="d-none"
	data-toggle="modal" data-target="#acceptedTaskModal"></button>
<div class="modal fade" id="acceptedTaskModal" tabindex="-1" role="dialog" aria-labelledby="acceptedTaskModal" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="acceptedTaskModalLabel">Accepted Task</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				  <span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="mb-2">
					<div class="row" id="acceptedTaskDetails">
						<%--Accepted Task info will be  append here by templating--%>
					</div>
				</div>
			</div>
			<div class="modal-footer">
			  <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
<script id="acceptedTask" type="text/x-jQuery-tmpl">
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
		Accepted by : 
	</span>
	<span class="text-secondary">
		\${acceptedBy}
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
</script>

<%--To show action task detail this modal will be used --%>
<button type="button" id="actionTaskModalBtn" class="d-none"
	data-toggle="modal" data-target="#actionTaskModal"></button>
<div class="modal fade" id="actionTaskModal" tabindex="-1" role="dialog" aria-labelledby="actionTaskModal" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="actionTaskModalLabel">Action on Task</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				  <span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="mb-2">
					<div class="row" id="actionTaskDetails">
						<%--action Task info will be  append here by templating--%>
					</div>
				</div>
			</div>
			<div class="modal-footer">
			  <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
<script id="actionOnATask" type="text/x-jQuery-tmpl">
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
		Action by : 
	</span>
	<span class="text-secondary">
		\${actionby}
	</span>
</div>
<div class="col-12 col-sm-6 mb-3">
	<span class="text-info text-uppercase">
		Action :  
	</span>
	<span class="text-secondary">
		\${currentTaskStage}
	</span>
</div>
<div class="col-12 mb-3">
	<span class="text-info text-uppercase">
		Action taken date : 
	</span>
	<span class="text-secondary">
		\${addedTimeStamp}
	</span>
</div>
</script>


