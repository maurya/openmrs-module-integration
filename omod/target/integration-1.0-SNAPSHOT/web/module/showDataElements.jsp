<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="localHeader.jsp" %>
<%@ include file="localInclude.jsp" %>
<openmrs:require privilege="Manage Integration Servers" otherwise="/login.htm" redirect="/module/integration/integrationServerAdmin" />
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		
		$('.addOrEditPopup').dialog({
			autoOpen: false,
			modal: true,
			title: 'Map Data Element',
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
			var id=this.id.replace("cancel","");
			$('#addOrEditPopup'+id.trim()).dialog('close');	
			
		});
	} );

	function editDataElement(id) {

			$("#id"+id).val(id);
			$("#DataElementName"+id).val($.trim($("#name"+id).html()));
			$('#addOrEditPopup'+id).dialog('open');
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

</script>
<h2><spring:message code="integration.dhis.reportTemplate"/> : ${reportTemplate.reportTemplateName}</h2>
	
		<div >
		       			<table class="integration-data-table display">
			<thead>
				<tr>
					<th><spring:message code="integration.dhis.dataElement"/></th>
					<th><spring:message code="integration.general.code"/></th>
					<th><spring:message code="integration.general.mappedTo"/></th>
					<th><spring:message code="integration.dhis.categoryCombo"/></th>
					<th><spring:message code="integration.general.lastUpdated"/></th>
					<th align="center" width="1%"><spring:message code="integration.general.lastUpdated"/><spring:message code="integration.general.editMapping"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${DataElementToCategoryComboDictionary}" var="element" >
					<tr id="${element.key.dataElementId}">
						<td width="10%" id="name${element.key.dataElementId}" >
							${element.key.dataElementName}
						</td>
						<td width="10%" id="code${element.key.dataElementId}">
							${element.key.dataElementCode}
						</td>
						<td width="10%" id="mappedobjectid${element.key.dataElementId}">
							${uuidToCohortDefinitionMap[element.key.cohortDefinitionUuid]}
						</td>
						<td width="20%">
						${element.value.name}
						</td>
						<td width="20%">
							${key.key.lastUpdated}
						</td>
						<td width="1%" align="center" nowrap>
							&nbsp;
							<a href="javascript:editDataElement('${element.key.dataElementId}');"><img src="<c:url value='/images/edit.gif'/>" border="0" title='<spring:message code="integration.tooltips.mapDataElement"/>'/></a>
					<div id="addOrEditPopup${element.key.dataElementId}" class="addOrEditPopup">
						<openmrs:portlet url="mappingCohort.portlet" id="mappingCohort${element.key.dataElementId}" moduleId="integration" parameters="type=DataElement|mappedCohort=${element.key.cohortDefinitionUuid}|portletId=${element.key.dataElementId}" />
					</div>
						</td>
					</tr>
					
				</c:forEach>
			</tbody>
			<tfoot>
			</tfoot>
		</table>
		</div>
		
<%@ include file="/WEB-INF/template/footer.jsp"%>