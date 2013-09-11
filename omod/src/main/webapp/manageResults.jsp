<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="localHeader.jsp" %>
<%@ include file="localInclude.jsp" %>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
	$(".integration-data-table").dataTable( {
			"bPaginate": false,
			"iDisplayLength": 25,
			"bLengthChange": false,
			"bFilter": false,
			"bSort": true,
			"bInfo": true,
			"bAutoWidth": false
		} );	
		});
	function showErrorDetails(uuid) {
		showReportingDialog({
			title: '<spring:message code="integration.errorDetails"/>',
			url: '${pageContext.request.contextPath}/module/reporting/reports/viewErrorDetails.form?uuid='+uuid
		});
	}
	function showResult(uid,location,startdate) {
			 $.post("${pageContext.request.contextPath}/module/integration/viewResult.form",{uid: uid,location: location,startDate: startdate});
	}
	
</script>
<div>
	<table id="integration-data-table" class="integration-data-table" width="99%" style="padding:3px;">
			<thead>
				<tr>
					<th style="display:none"></th>
					<th><spring:message code="integration.dhis.reportTemplate"/></th>
					<th><spring:message code="integration.dhis.parameters"/></th>
					<th><spring:message code="integration.dhis.requestedOn"/></th>
					<th><spring:message code="integration.dhis.status"/></th>
					<th style="text-align:center;"><spring:message code="integration.general.actions"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="r" items="${history}">
					<tr valign="baseline">
						<td style="display:none"><openmrs:formatDate date="${r.requestDate}" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<a href="reportHistoryOpen.form?uuid=${r.uuid}">
								${r.reportDefinition.parameterizable.name}
							</a>
						</td>
						<td style="white-space:nowrap;">
							<table class="small">
								<c:forEach var="p" items="${r.reportDefinition.parameterizable.parameters}">
									<tr>
										<td>${p.label}:</td>
										<td>${r.reportDefinition.parameterMappings[p.name]}</td>
									</tr>
								</c:forEach>
							</table>
		 				</td>
						<td style="white-space:nowrap;">
							<openmrs:formatDate date="${r.requestDate}" format="dd/MMM/yyyy HH:mm"/><br/>
							<small>
								<rpt:format object="${r.requestedBy}"/>
							</small>
						</td>
						<td style="white-space:nowrap;">
							<spring:message code="reporting.status.${r.status}"/>
						</td>
						<td style="text-align:center; vertical-align:middle;">
							<c:choose>
								<c:when test="${r.status == 'FAILED'}">
									<a href="javascript:showErrorDetails('${r.uuid}');">
										<img src='<c:url value="/images/error.gif"/>' border="0" width="16" height="16"/><br/>
										<small><spring:message code="reporting.viewError"/></small>
									</a>
								</c:when>
								<c:otherwise>
									<a href="javascript:showResult('kk','location','startdate');">
										<button><spring:message code="integration.button.viewReport"/></button>
									</a>
										<a href="viewReport.form?uuid=${r.uuid}">
										<button><spring:message code="integration.button.sendReport"/></button>
									</a>
										<a href="viewReport.form?uuid=${r.uuid}">
										<button><spring:message code="integration.button.deleteReport"/></button>
									</a>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>