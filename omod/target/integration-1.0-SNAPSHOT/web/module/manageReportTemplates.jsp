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
		$('.neworedit').hide();
		$(".cancel").click( function() {
			$('.neworedit').hide();
			
		});

	} );

	function editReportTemplate(id) {
	
			$("#id").val(id);
			$("#reportName").val($.trim($("#name"+id).html()));
			$("#mappedReport").val($.trim($("#mappedReport"+id).html()));
					$('.neworedit').show();
		}
		function saveReportTemplate() {
	
			var mapped=$("#mappedReport").val();
		var idmap=$("#id").val();
				 $.post("${pageContext.request.contextPath}/module/integration/saveReportTemplateMapping.form",{mappedReport: mapped,id: idmap},function() {
		               //alert('got data');
		            }).error(function() {
		               // alert('Unable load Templates');
		            }).success(function() {
		                // alert('success');
		            }).complete(function() {
		               //alert('complete');
					   location.reload();
		            });
				
		}
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
					<tr id="${reportTemplate.reportTemplateId}">
						<td width="10%" id="name${reportTemplate.reportTemplateId}">
							${reportTemplate.reportTemplateName}
						</td>
						<td width="10%" id="code${reportTemplate.reportTemplateId}">
							${reportTemplate.reportTemplateCode}
						</td>
						<td width="10%" id="frequency${reportTemplate.reportTemplateId}">
							${reportTemplate.frequency}
						</td>
						<td width="10%" id="mappedReport${reportTemplate.reportTemplateId}">
							${reportTemplate.mappedReportName}
						</td>
						<td width="10%" id="check${reportTemplate.reportTemplateId}">
							<input type="checkbox" checked="checked">
						</td>
						<td width="10%" id="lastUpdated${reportTemplate.reportTemplateId}">
							${reportTemplate.lastUpdated}
						</td>
						<td align="center" nowrap>
						 <a href="showDataElements.form?reportTemplateId=${reportTemplate.reportTemplateId}">
						  <button >
                   Edit Data Elements
                    </button>
                    </a>
                     <a href="showOptions.form?reportTemplateId=${reportTemplate.reportTemplateId}">
                      <button >
                   Edit Options
                    </button>
                     </a>
							&nbsp;
							<a href="javascript:editReportTemplate('${reportTemplate.reportTemplateId}');"><img src="<c:url value='/images/edit.gif'/>" border="0"/></a>
							
						</td>
					</tr>
				</c:forEach>	
			</tbody>
			<tfoot>
			</tfoot>
		</table>
		</div>
		
			<div id="neworedit" class="neworedit" >
		<b class="boxHeader">
			
				
				Report Mapping	
				
				
			
		</b>
		<div class="box" >
				<form method="post" id="detailsedit" >
					<table>
						<tbody>	
							<tr>
								<td>Report Name</td>
								<td>:</td>
								<td>
								<input id="id" type="hidden"/>
								<input id="reportName" type="text" size="40" /></td>
							</tr>
							<tr>
								<td>Mapped Report</td>
								<td>:</td>
								<td>
								<input id="mappedReport" type="text" size="40" /></td>
							</tr>
							
						
							<tr>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td><a href="javascript:saveReportTemplate();"><input type="button" value="Save" /> </a><input
								type="reset" value="Cancel" class="cancel">
								</td>
							</tr>
						</tbody>
					</table>
				</form>
		</div>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>