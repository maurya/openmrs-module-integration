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

	function editDataElement(id) {
	
			$("#id").val(id);
			$("#dataElementName").val($.trim($("#name"+id).html()));
			$("#mappedDataElement").val($.trim($("#mappedobjectid"+id).html()));
					$('.neworedit').show();
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
<h2>Report Template : ${reportTemplate.reportTemplateName}</h2>
	
		<div >
		       			<table class="integration-data-table display">
			<thead>
				<tr>
					<th>Data Element Name</th>
					<th>Code</th>
					<th>uid</th>
					<th>Mapped Object Id</th>
					<th>Category Options</th>
					<th>Last Updated</th>
					<th align="center" width="1%">Edit mappings</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${DataElementToCategoryOptionDictionary}" var="key" >
					<tr id="${key.key.dataElementId}">
						<td width="10%" id="name${key.key.dataElementId}" >
							${key.key.dataElementName}
						</td>
						<td width="10%" id="code${key.key.dataElementId}">
							${key.key.dataElementCode}
						</td>
						<td width="10%" id="uid${key.key.dataElementId}">
							${key.key.dataElementUid}
						</td>
						<td width="10%" id="mappedobjectid${key.key.dataElementId}">
							${key.key.cohortDefinitionUuid}
						</td>
						<td width="20%">
							<c:forEach items="${key.value}" var="categoryOption" >
							<p>${categoryOption.name}</p>
							</c:forEach>
						</td>
						<td width="20%">
							${key.key.lastUpdated}
						</td>
						<td width="1%" align="center" nowrap>
							&nbsp;
							<a href="javascript:editDataElement('${key.key.dataElementId}');"><img src="<c:url value='/images/edit.gif'/>" border="0"/></a>
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
			
				
				Data Element Mapping
				
				
			
		</b>
		<div class="box" >
				<form method="post" id="detailsedit" >
					<table>
						<tbody>	
							<tr>
								<td>Data Element</td>
								<td>:</td>
								<td>
								<input id="id" type="hidden"/>
								<input id="dataElementName" type="text" size="40" /></td>
							</tr>
							<tr>
								<td>Mapped DataElement</td>
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
								<td><a href="javascript:saveDataElement();"><input type="button" value="Save" /> </a><input
								type="reset" value="Cancel" class="cancel">
								</td>
							</tr>
						</tbody>
					</table>
				</form>
		</div>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>