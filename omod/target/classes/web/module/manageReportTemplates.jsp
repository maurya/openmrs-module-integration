<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="localInclude.jsp" %>
<%@ include file="localHeader.jsp" %>
<openmrs:require privilege="Manage Integration Servers" otherwise="/login.htm" redirect="/module/integration/integrationServerAdmin" />
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		
		$('#addOrEditPopup').dialog({
			autoOpen: false,
			modal: true,
			title: 'Add Or Edit Report Template',
			width: '90%'
		});
		
		$(".integration-data-table").dataTable( {
			"bPaginate": false,
			"iDisplayLength": 25,
			"bLengthChange": false,
			"bFilter": false,
			"bSort": true,
			"bInfo": true,
			"bAutoWidth": false
		} );
		$(".cancel").click( function() {
			$('#addOrEditPopup').dialog('close');	
			
		});

	} );

	function editReportTemplate(id) {
	
			$("#id").val(id);
			$("#reportName").val($.trim($("#name"+id).html()));
			$("#mappedReport").val($.trim($("#mappedReport"+id).html()));
			$('#addOrEditPopup').dialog('open');
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
<h2><spring:message code="integration.serverAdmin"/> : ${server.serverName}</h2>

<div >
			<br/>

		       			<table class="integration-data-table display">
			<thead>
				<tr>
					<th><spring:message code="integration.general.name"/></th>
					<th><spring:message code="integration.general.code"/></th>
					<th><spring:message code="integration.general.frequency"/></th>
					<th><spring:message code="integration.general.baseCohort"/></th>
					<th><spring:message code="integration.general.reportMappedTo"/></th>
					<th><spring:message code="integration.general.validMappings"/></th>
					<th><spring:message code="integration.general.lastUpdated"/></th>
					<th align="center" width="1%"><spring:message code="integration.general.actions"/></th>
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
							${reportTemplate.mappedReportUuid}
						</td>
						<td width="10%" id="mappedReport${reportTemplate.reportTemplateId}">
							${reportTemplate.mappedReportUuid}
						</td>
						<td width="10%" id="check${reportTemplate.reportTemplateId}">
							<input type="checkbox" checked="checked">
						</td>
						<td width="10%" id="lastUpdated${reportTemplate.reportTemplateId}">
							${reportTemplate.lastUpdated}
						</td>
						<td align="center" nowrap>
							<a href="javascript:editReportTemplate('${reportTemplate.reportTemplateId}');"> <button >
                  <spring:message code="integration.button.editReports"/>
                    </button>
                    </a>
							
						
						
						 <a href="showDataElements.form?reportTemplateId=${reportTemplate.reportTemplateId}">
						  <button >
                   <spring:message code="integration.button.mapDataElement"/>
                    </button>
                    </a>
                     <a href="showOptions.form?reportTemplateId=${reportTemplate.reportTemplateId}">
                      <button >
                   <spring:message code="integration.button.mapOptionSets"/>
                    </button>
                     </a>
                     </td>
										</tr>
				</c:forEach>	
			</tbody>
			<tfoot>
			</tfoot>
		</table>
		</div>
		
		<div id="addOrEditPopup">
					<b class="boxHeader"><spring:message code="integration.reportMapping"/></b>
					<div class="box">
					<form method="post" id="detailsedit" >
					<table>
						<tbody>	
							<tr>
								<td><spring:message code="integration.general.name"/></td>
								<td>:</td>
								<td>
								<input id="id" type="hidden"/>
								<input id="reportName" type="text" size="40" /></td>
							</tr>
							<tr>
								<td><spring:message code="integration.general.reportMappedTo"/></td>
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
								<td><a href="javascript:saveReportTemplate();"><input type="button" value='<spring:message code="integration.button.save"/>' /> </a><input
								type="reset" value='<spring:message code="integration.button.cancel"/>' class="cancel">
								</td>
							</tr>
						</tbody>
					</table>
				</form>
					</div>
					</div>
			<div >
			<br/>

		       			<table class="integration-data-table display">
			<thead>
				<tr>
					<th><spring:message code="integration.dhis.dataElement"/></th>
					<th><spring:message code="integration.dhis.categoryCombo"/></th>
					<th><spring:message code="integration.dhis.optionSet"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${CategoryComboToDataElementDictionary}" var="element" >
					<tr id="${element.key.id}">
					
						<td width="10%" id="dataElementCollection${element.key.id}" >
							<c:forEach items="${element.value}" var="dataElement" >
							<p id="dataElement${element.key.id}">${dataElement.dataElementName}</p>
							</c:forEach>
						</td>
						<td width="10%" id="categoryCombo${element.key.id}">
							${element.key.name}
						</td>
						<td width="10%" id="optionSets${element.key.id}">
							<c:forEach items="${element.key.optionSets}" var="optionSet" >
							<p>${optionSet.name}</p>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>	
			</tbody>
			<tfoot>
			</tfoot>
		</table>
		</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>