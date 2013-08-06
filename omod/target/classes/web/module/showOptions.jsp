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
					<th>Option Set</th>
					<th>Code</th>
					<th>uid</th>
					<th>Category Option</th>
					<th>Category Option Type</th>	
					<th>Last Updated</th>
					<th align="center" width="1%">Edit mappings</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${OptionList}" var="option" >
					<tr>
						<td width="10%">
							${option.setName}
						</td>
						<td width="10%">
							${option.setCode}
						</td>
						<td width="10%">
							${option.uuid}
						</td>
						<td width="20%">
						<c:forEach items="${option.categoryOptions}" var="categoryOption" >
							<p>${categoryOption.name}</p>
							</c:forEach>
						</td>
						<td width="20%">
						<c:forEach items="${option.categoryOptions}" var="categoryOption" >
							<p>${categoryOption.comboName}</p>
							</c:forEach>
						</td>
						<td width="20%">
							${option.lastUpdated}
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
		
		<div >
		       			<table class="integration-data-table display">
			<thead>
				<tr>
					<th>Option Name</th>
					<th>Code</th>
					<th>uid</th>
					<th>Mapped Object Id</th>
					<th>Option Set Name</th>
					<th>Option Sets</th>	
					<th>Last Updated</th>
					<th align="center" width="1%">Edit mappings</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${OptionList}" var="option" >
					<tr>
						<td width="10%">
							${option.name}
						</td>
						<td width="10%">
							${option.code}
						</td>
						<td width="10%">
							${option.uuid}
						</td>
						<td width="10%">
							${option.mappedObjectId}
						</td>
						<td width="20%">
						${option.setName}
						</td>
						<td width="20%">
						${option.setCode}
						</td>
						<td width="20%">
							${option.lastUpdated}
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