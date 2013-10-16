<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />	

<script type="text/javascript"	src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		var timer = setInterval(function() {
			$.ajax({
				url: 'dhisApi.portlet?operation=STATUS',
				success: function() {
					if(${model.done}) {
						clearInterval(timer);
					}
				}
			});
		}, 3000);
	});
</script>

	<div>
		<div >
			<form method="get" id="apiresultdisplay" >
				<h2>
					<c:if test="empty ${model.done}">
						<spring:message code="integration.ApiPortlet.PleaseWait" />
					</c:if>
					<c:if test="!empty ${model.done}">
						<c:if test="!${model.done}">
							<spring:message code="integration.ApiPortlet.PleaseWait" />
						</c:if>
						<c:if test="${model.done}">
							<c:if test="${attributes.apiresult.error}">
								<img width="20" height="20" src="${pageContext.request.contextPath}/moduleResources/integration/images/error.gif" border="0"'/>	
							</c:if>
							<spring:message code="${attributes.apiresult.status}" />
						</c:if>
					</c:if>
				</h2>
				
				<c:if test="!empty ${attributes.apiresult.changes}">
					<div id="updateresult">
						<table>
							<tbody>
								<th>
									<td><spring:message code="integration.ApiPortlet.ChangeType" /></td>
									<td><spring:message code="integration.ApiPortlet.Object" /></td>
									<td><spring:message code="integration.ApiPortlet.UID" /></td>
									<td><spring:message code="integration.ApiPortlet.Name" /></td>
									<td><spring:message code="integration.ApiPortlet.Code" /></td>
									<td><spring:message code="integration.ApiPortlet.OldName" /></td>
									<td><spring:message code="integration.ApiPortlet.OldCode" /></td>
									<td><spring:message code="integration.ApiPortlet.Revision" /></td>
								</th>
								<c:forEach items="${attributes.apiresult.changes}" var="change">
										<tr>
	 									<td>${change.changeType}</td>
	 									<td>${change.objClass}</td>
	 									<td>${change.uid}</td>
	 									<td>${change.name}</td>
	 									<td>${change.code}</td>
	 									<td>${change.oldName}</td>
	 									<td>${change.oldCode}</td>
	 									<td>${change.newFreq}</td>
	 								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				
				<c:if test="!empty ${attributes.apiresult.removed}">
					<div id="orgunitresult">
						<table>
							<tbody>
								<tr>
									<th><spring:message code="integration.ApiPortlet.DeletedOrgUnits" /></th>
								</tr>
								<c:forEach items="${attributes.apiresult.removed}" var="orgunit">
									<tr><td>orgunit.name</td></tr>
								</c:forEach>
							</tbody>
						</table>
					</div>	
				</c:if>
				
				<c:if test="!empty ${attributes.apiresult.summary}">
					<div id="summary">
						<table>
							<thead>
								<spring:message code="integration.ApiPortlet.SummaryStatus" />
								&nbsp;:&nbsp
								${attributes.apiresult.summary.status}
								&nbsp;:&nbsp
								${attributes.apiresult.summary.description}
							</thead>
							<tbody>
								<tr>
									<td><spring:message code="integration.ApiPortlet.DataValueCount" /></td>
									<td>${attributes.apiresult.summary.dataValueCount}</td>
								</tr>
								<tr>
									<td><spring:message code="integration.ApiPortlet.Conflicts" /></td>
									<td>${attributes.apiresult.summary.conflicts}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</c:if>
				
				<c:if test="!${model.done}">
					<input 
						id="cancel${model.extraClass}${model.portletId}"
						type="reset" 
						value='<spring:message code="integration.button.cancel"/>' 
						class="cancel${model.extraClass}">
					/>
				</c:if>
				<c:if test="${model.done}">
					<input 
						id="${model.portletId}" 
						type="button" 
						value='<spring:message code="integration.button.OK"/>' 
					/>
				</c:if>
			</form>
		</div>					
	</div>
	