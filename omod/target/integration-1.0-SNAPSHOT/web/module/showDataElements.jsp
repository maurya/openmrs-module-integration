<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="localInclude.jsp" %>
<openmrs:require privilege="Manage Integration Servers" otherwise="/login.htm" redirect="/module/integration/integrationServerAdmin" />
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		
		$('#addOrEditPopup').dialog({
			autoOpen: false,
			modal: true,
			title: 'Map Data Element',
			width: '90%'
		});
		
		$(".integration-data-table").dataTable( {
			"bPaginate": true,
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

	function editDataElement(id) {
	
			$("#id").val(id);
			$("#dataElementName").val($.trim($("#name"+id).html()));
			$("#mappedDataElement").val($.trim($("#mappedobjectid"+id).html()));
			$('#addOrEditPopup').dialog('open');
		}
		function saveDataElement() {
	
			var mapped=$("#mappedDataElement").val();
		var idmap=$("#id").val();
				 $.post("${pageContext.request.contextPath}/module/integration/saveDataElementMapping.form",{mappedDataElement: mapped,id: idmap},function() {
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
							${element.key.cohortDefinitionUuid}
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
						</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
			</tfoot>
		</table>
		</div>
		<div id="addOrEditPopup">
					<b class="boxHeader"><spring:message code="integration.dhis.dataElement"/> <spring:message code="integration.Mapping"/></b>
					<div class="box">
					<form method="post" id="detailsedit" >
					<table>
						<tbody>	
							<tr>
								<td><spring:message code="integration.dhis.dataElement"/></td>
								<td>:</td>
								<td>
								<input id="id" type="hidden"/>
								<input id="dataElementName" type="text" size="40" /></td>
							</tr>
							<tr>
								<td><spring:message code="integration.general.mappedTo"/></td>
								<td>:</td>
								<td>
								<input id="mappedDataElement" type="text" size="40" /></td>
							</tr>
							
						
							<tr>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td><a href="javascript:saveDataElement();"><input type="button" value='<spring:message code="integration.button.save"/>' /> </a><input
								type="reset" value='<spring:message code="integration.button.cancel"/>' class="cancel">
								</td>
							</tr>
						</tbody>
					</table>
				</form>
					</div>					
		</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>