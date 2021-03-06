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
			title: 'Map Option',
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
			$('#addOrEditPopup'+id).dialog('close');	
			
		});

	} );

	function editOption(id) {
	
			$("#id"+id).val(id);
			$("#OptionName"+id).val($.trim($("#name"+id).html()));
			$('#addOrEditPopup'+id).dialog('open');
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
<h2><spring:message code="integration.header.optionSetsReport"/> : ${reportTemplate.reportTemplateName}</h2>
	
		<div >
		       			<table class="integration-data-table display">
			<thead>
				<tr>
					<th><spring:message code="integration.dhis.optionSet"/></th>
					<th><spring:message code="integration.general.code"/></th>
					<th><spring:message code="integration.general.mappedTo"/></th>
					<th><spring:message code="integration.dhis.options"/></th>
					<th><spring:message code="integration.general.mappedTo"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${OptionSetList}" var="optionset" >
					<tr>
						<td width="10%">
							${optionset.name}
						</td>
						<td width="10%">
							${optionset.code}
						</td>
						<td width="10%">
							${optionset.uid}
						</td>
						<td width="20%">
						<c:forEach items="${optionset.options}" var="option" >
							<p><label id="name${option.id}">${option.name}</label><a href="javascript:editOption('${option.id}');"><img src="<c:url value='/images/edit.gif'/>" border="0" title='<spring:message code="integration.tooltips.mapOption"/>'/></a></p>
							</c:forEach>
						</td>
						<td>
						<c:forEach items="${optionset.options}" var="option" >
							<p><label id="mapped${option.id}">${uuidToCohortDefinitionMap[option.cohortdefUuid]}</label></p>
							<div id="addOrEditPopup${option.id}" class="addOrEditPopup">
						<openmrs:portlet url="mappingCohort.portlet" id="mappingCohort${option.id}" moduleId="integration" parameters="mappedCohort=${option.cohortdefUuid}|type=Option|portletId=${option.id}" />
							</div>
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