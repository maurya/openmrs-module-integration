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

	function confirmDelete(name) {
		if (confirm("Are you sure you want to delete " + name + "?")) {
			document.location.href = '${pageContext.request.contextPath}/module/integration/purgeServer.form?name=' + name;
		}
	}

</script>
<h2><spring:message code="${serverName}"/></h2>

<div >
		       			<table class="integration-data-table display">
			<thead>
				<tr>
					<th>Server Name</th>
					<th>Dataset Name</th>
					<th>Code</th>
					<th>Description</th>
					<th>Mapped To</th>
					<th>Attributes Mapped</th>
					<th align="center" width="1%">Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${serverName}" var="serverItem" >
					<tr>
						<td width="20%" nowrap>
							${serverName}
						</td>
						<td width="20%">
							${serverName}
						</td>
						<td width="20%">
							${serverName}
						</td>
						<td width="20%">
							${serverName}
						</td>
						<td width="20%">
							${serverName}
						</td>
						<td width="20%">
							<input type="checkbox" checked="checked">
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