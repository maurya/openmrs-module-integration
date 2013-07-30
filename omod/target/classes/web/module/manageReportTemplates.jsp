<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="localInclude.jsp" %>
<openmrs:require privilege="Manage Integration Servers" otherwise="/login.htm" redirect="/module/integration/integrationServerAdmin" />
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		
		$(".integration-data-table").dataTable( {
			"bPaginate": true,
			"iDisplayLength": 25,
			"bLengthChange": false,
			"bFilter": true,
			"bSort": true,
			"bInfo": true,
			"bAutoWidth": false
		} );

	} );

</script>
<h2><spring:message code="${server.serverName}"/></h2>

<div >
		       			<table class="integration-data-table display">
			<thead>
				<tr>
					<th>Report Template Name</th>
					<th>Code</th>
					<th>Periodicity</th>
					<th>Report Mapped To</th>
					<th>Attributes Mapped</th>
					<th>Last Updated</th>
					<th align="center" width="1%">Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${reportTemplates}" var="reportTemplate" >
					<tr>
						<td width="20%">
							${reportTemplate.reportTemplateName}
						</td>
						<td width="20%">
							${reportTemplate.reportTemplateCode}
						</td>
						<td width="20%">
							${reportTemplate.frequency}
						</td>
						<td width="20%">
							${reportTemplate.mappedReportName}
						</td>
						<td width="20%">
							<input type="checkbox" checked="checked">
						</td>
						<td width="20%">
							${reportTemplate.lastUpdated}
						</td>
						<td width="1%" align="center" nowrap>
								&nbsp;
							<a href="showDataElementsAndOptions.form?reportTemplateId=${reportTemplate.reportTemplateId}">
										<img width="20" height="20" src="${pageContext.request.contextPath}/moduleResources/integration/images/attributes.png" border="0"/>	
									</a>
							&nbsp;
							<a href=""><img src="<c:url value='/images/edit.gif'/>" border="0"/></a>
						</td>
					</tr>
				</c:forEach>	
			</tbody>
			<tfoot>
			</tfoot>
		</table>
		</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>