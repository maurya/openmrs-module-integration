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
<h2>Hi</h2>

<div >
		       			<table class="integration-data-table display">
			<thead>
				<tr>
					<th>Data Element Name</th>
					<th>Code</th>
					<th>uid</th>
					<th>Category Combo's</th>
					<th>Mapped Object Id</th>
					<th>Last Updated</th>
					<th align="center" width="1%">Edit mappings</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${Keys}" var="key" >
					<tr>
						<td width="20%">
							${key}
						</td>
						<td width="20%">
							${key.uid}
						</td>
						<td width="20%">
<!-- 							<c:forEach items="${DataElementToCategoryOptionDictionary.get(${key})}" var="categoryOption" >
 							${categoryOption.Name} 
							</c:forEach>	 -->
						</td>
						<td width="20%">
							${key.dataElementMappedObjectId}
						</td>
						<td width="20%">
							<input type="checkbox" checked="checked">
						</td>
						<td width="20%">
							${key.lastUpdated}
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