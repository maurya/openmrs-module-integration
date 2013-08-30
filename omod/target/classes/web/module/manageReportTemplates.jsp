<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="localInclude.jsp" %>
<%@ include file="localHeader.jsp" %>
<openmrs:require privilege="Manage Integration Servers" otherwise="/login.htm" redirect="/module/integration/integrationServerAdmin" />
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		
		$('#editReportTemplateMapping').dialog({
			autoOpen: false,
			modal: true,
			title: 'Mapping Cohorts',
			width: '90%'
		});
		
		$('.addOrEditPopup').dialog({
			autoOpen: false,
			modal: true,
			title: 'Mapping Cohorts',
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
			$('#editReportTemplateMapping').dialog('close');	
			
		});

		$(".cancelde").click( function() {
			var id=this.id.replace("cancelde","");
			$('#addOrEditPopupde'+id.trim()).dialog('close');	
			
		});
		$(".cancelo").click( function() {
			var id=this.id.replace("cancelo","");
			$('#addOrEditPopupo'+id.trim()).dialog('close');	
			
		});
	} );

	function editReportTemplate(id) {
	
			$("#id").val(id);
			$("#reportName").val($.trim($("#name"+id).html()));
			$("#mappedReport").val($.trim($("#mappedReport"+id).html()));
			$('#editReportTemplateMapping').dialog('open');
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
		
		function editDataElement(id) {

			$("#id"+id).val(id);
			$("#DataElementName"+id).val($.trim($("#dataElement"+id).html()));
			$('#addOrEditPopupde'+id).dialog('open');
		}
		function saveDataElement(id) {
	
			var uuid=$("#cohorts"+id).val();
				 $.post("${pageContext.request.contextPath}/module/integration/saveDataElementMapping.form",{uuid: uuid,id: id},function() {
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
		function editOption(id) {
			
			$("#id"+id).val(id);
			$("#OptionName"+id).val($.trim($("#option"+id).html()));
			$('#addOrEditPopupo'+id).dialog('open');
		}
		function saveOption(id) {
	
			var uuid=$("#cohorts"+id).val();
				 $.post("${pageContext.request.contextPath}/module/integration/saveOptionsSetMapping.form",{uuid: uuid,id: id},function() {
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
						<td width="10%" id="baseCohort${reportTemplate.reportTemplateId}">
						${uuidToReportDefinitionMap[reportTemplate.mappedReportUuid].baseCohortDefinition}
						</td>
						<td width="10%" id="mappedReport${reportTemplate.reportTemplateId}">
							${uuidToReportDefinitionMap[reportTemplate.mappedReportUuid]}
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
		
		<div id="editReportTemplateMapping">
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
								<a href="javascript:editDataElement('${dataElement.dataElementId}');"><p id="dataElement${dataElement.dataElementId}">${dataElement.dataElementName}</p></a>
								<div id="addOrEditPopupde${dataElement.dataElementId}" class="addOrEditPopup">
								<openmrs:portlet url="mappingCohort.portlet" id="mappingCohort${dataElement.dataElementId}" moduleId="integration" parameters="type=DataElement|mappedCohort=${dataElement.cohortDefinitionUuid}|portletId=${dataElement.dataElementId}|extraClass=de" />
								</div>
							</c:forEach>
						</td>
						<td width="10%" id="categoryCombo${element.key.id}">
							${element.key.name}
						</td>
						<td width="10%" id="optionSets${element.key.id}">
							<c:forEach items="${element.key.optionSets}" var="optionSet" >	
							<c:forEach items="${optionSet.options}" var="option" >
							<a href="javascript:editOption('${option.id}');"><p id="option${option.id}">${option.name}</p></a>
							<div id="addOrEditPopupo${option.id}" class="addOrEditPopup">
							<openmrs:portlet url="mappingCohort.portlet" id="mappingCohort${option.id}" moduleId="integration" parameters="mappedCohort=${option.cohortdefUuid}|type=Option|portletId=${option.id}|extraClass=o" />
							</div>
							</c:forEach>
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