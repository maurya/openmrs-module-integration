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
<h2>Server Name : <spring:message code="${server.serverName}"/></h2>

<div >
 <div id="button" align="right">
                    <button >
                    Update Templates
                    </button>
            </div>
			<br/>

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
						<td width="10%">
							${reportTemplate.reportTemplateName}
						</td>
						<td width="10%">
							${reportTemplate.reportTemplateCode}
						</td>
						<td width="10%">
							${reportTemplate.frequency}
						</td>
						<td width="10%">
							${reportTemplate.mappedReportName}
						</td>
						<td width="10%">
							<input type="checkbox" checked="checked">
						</td>
						<td width="10%">
							${reportTemplate.lastUpdated}
						</td>
						<td align="center" nowrap>
						 <a href="showDataElements.form?reportTemplateId=${reportTemplate.reportTemplateId}">
						  <button >
                   Edit Data Templates
                    </button>
                    </a>
                     <a href="showOptions.form?reportTemplateId=${reportTemplate.reportTemplateId}">
                      <button >
                   Edit Optionsets
                    </button>
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