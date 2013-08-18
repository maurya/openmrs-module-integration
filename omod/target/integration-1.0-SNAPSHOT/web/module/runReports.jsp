<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="localInclude.jsp" %>
<openmrs:require privilege="Manage Integration Servers" otherwise="/login.htm" redirect="/module/integration/integrationServerAdmin" />
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		
		$(".report-table").dataTable( {
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
<h2>Reports To Run</h2>

<div >
		   <table class="report-table display">
			<thead>
				<tr>
					<th>Report Template Name</th>
					<th>Code</th>
					<th>Periodicity</th>
					<th>Report Mapped To</th>
					<th>Last Updated</th>
					<th align="center" width="1%">Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${reports}" var="report" >
					<tr id="${report.reportTemplateId}">
						<td width="10%" id="name${report.reportTemplateId}">
							${report.reportTemplateName}
						</td>
						<td width="10%" id="code${report.reportTemplateId}">
							${report.reportTemplateCode}
						</td>
						<td width="10%" id="frequency${report.reportTemplateId}">
							${reportTemplate.frequency}
						</td>
						<td width="10%" id="mappedReport${report.reportTemplateId}">
							${report.mappedReportName}
						</td>
						<td width="10%" id="lastUpdated${report.reportTemplateId}">
							${report.lastUpdated}
						</td>
						<td align="center" nowrap>
							&nbsp;
							<a href=""><img src="<c:url value='/images/play.gif'/>" border="0"/></a>
							
						</td>
					</tr>
				</c:forEach>	
			</tbody>
			<tfoot>
			</tfoot>
		</table>
		</div>
		

<%@ include file="/WEB-INF/template/footer.jsp"%>