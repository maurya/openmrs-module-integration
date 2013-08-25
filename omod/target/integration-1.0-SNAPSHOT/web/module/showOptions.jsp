<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="localInclude.jsp" %>
<openmrs:require privilege="Manage Integration Servers" otherwise="/login.htm" redirect="/module/integration/integrationServerAdmin" />
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#addOrEditPopup').dialog({
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
			$('#addOrEditPopup').dialog('close');	
			
		});
	} );

	function editOption(id) {
	
			$("#id").val(id);
			$("#optionName").val($.trim($("#name"+id).html()));
			$("#mappedOption").val($.trim($("#mapped"+id).html()));
			$('#addOrEditPopup').dialog('open');
		}
		function saveOption() {
	
			var mapped=$("#mappedOption").val();
		var idmap=$("#id").val();
				 $.post("${pageContext.request.contextPath}/module/integration/saveOptionsSetMapping.form",{mappedOption: mapped,id: idmap},function() {
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
							<p><label id="mapped${option.id}">${option.cohortdefUuid}</label></p>
							</c:forEach>
						</td>	
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
			</tfoot>
		</table>
		</div>
		<div id="addOrEditPopup">
					<b class="boxHeader"><spring:message code="integration.dhis.option"/> <spring:message code="integration.Mapping"/></b>
					<div class="box">
					<form method="post" id="detailsedit" >
					<table>
						<tbody>	
							<tr>
								<td><spring:message code="integration.dhis.option"/></td>
								<td>:</td>
								<td>
								<input id="id" type="hidden"/>
								<input id="optionName" type="text" size="40" /></td>
							</tr>
							<tr>
								<td><spring:message code="integration.general.mappedTo"/></td>
								<td>:</td>
								<td>
								<input id="mappedOption" type="text" size="40" /></td>
							</tr>
							
						
							<tr>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td><a href="javascript:saveOption();"><input type="button" value='<spring:message code="integration.button.save"/>' /> </a><input
								type="reset" value='<spring:message code="integration.button.cancel"/>' class="cancel">
								</td>
							</tr>
						</tbody>
					</table>
				</form>
					</div>					
		</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>