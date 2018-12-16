<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 

<button type="button" id="taskHistoryModalBtn" class="d-none"
	data-toggle="modal" data-target="#taskHistoryModal"></button>
<%--History Modal --%>

<div class="modal fade" id="taskHistoryModal" tabindex="-1" role="dialog" aria-labelledby="taskHistoryModal" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="taskHistoryModalLabel">Task History</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				  <span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="mb-2">
					<div class="row">
						<div class="col-4 text-primary">
							Action By
						</div>
						<div class="col-4 text-primary">
							Status
						</div>
						<div class="col-4 text-primary">
							Date
						</div>
					</div>
				</div>
				<div class="_historydetail">
					<%--Histories will be appended here using js --%>	
				</div>
			</div>
			<div class="modal-footer">
			  <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
