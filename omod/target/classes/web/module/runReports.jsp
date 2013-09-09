<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="localHeader.jsp" %>
<%@ include file="localInclude.jsp" %>
<openmrs:require privilege="Manage Integration Servers" otherwise="/login.htm" redirect="/module/integration/integrationServerAdmin" />
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		
		$(".report-table").dataTable( {
			"bPaginate": false,
			"iDisplayLength": 25,
			"bLengthChange": false,
			"bFilter": false,
			"bSort": true,
			"bInfo": true,
			"bAutoWidth": false
		} );
	} );

</script>
<h2><spring:message code="integration.header.reportsToRun"/></h2>

<div >
		   <table class="report-table display">
			<thead>
				<tr>
					<th><spring:message code="integration.general.name"/></th>
					<th><spring:message code="integration.general.code"/></th>
					<th><spring:message code="integration.general.frequency"/></th>
					<th><spring:message code="integration.general.reportMappedTo"/></th>
					<th align="center" width="1%"><spring:message code="integration.general.actions"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${reports}" var="report" >
					<tr id="${report.id}">
						<td width="10%" id="name${report.id}">
							${report.name}
						</td>
						<td width="10%" id="code${report.id}">
							${report.code}
						</td>
						<td width="10%" id="frequency${report.id}">
							${report.frequency}
						</td>
						<td width="10%" id="mappedReport${report.id}">
							${report.mappedReportUuid}
						</td>
						<td align="center" nowrap>
							&nbsp;
							<a href=""><img src="<c:url value='/images/play.gif'/>" border="0" title='<spring:message code="integration.runReports"/>'/></a>
							
						</td>
					</tr>
				</c:forEach>	
			</tbody>
			<tfoot>
			</tfoot>
		</table>
		</div>
		

<%@ include file="/WEB-INF/template/footer.jsp"%>