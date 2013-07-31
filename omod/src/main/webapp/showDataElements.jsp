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
					<th>Option Sets</th>	
					<th>Last Updated</th>
					<th align="center" width="1%">Edit mappings</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${DataElementToCategoryOptionDictionary}" var="key" >
					<tr>
						<td width="10%">
							${key.key.dataElementName}
						</td>
						<td width="10%">
							${key.key.dataElementCode}
						</td>
						<td width="10%">
							${key.key.dataElementUid}
						</td>
						<td width="10%">
							${key.key.dataElementMappedObjectId}
						</td>
						<td width="20%">
							<c:forEach items="${key.value}" var="categoryOption" >
							<p>${categoryOption.name}</p>
							</c:forEach>
						</td>
						<td width="20%">
						<c:forEach items="${key.value}" var="categoryOption" >
							<c:forEach items="${categoryOption.options}" var="Option" >
							<p>${Option.setName}</p>
							</c:forEach>
							</c:forEach>
						</td>
						<td width="20%">
							${key.key.lastUpdated}
						</td>
						<td width="1%" align="center" nowrap>
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