<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="localInclude.jsp" %>
<!-- <openmrs:require privilege="Manage Integration Servers" otherwise="/login.htm" redirect="/module/integration/integrationServerAdmin" /> -->
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
		
		$('#neworedit').load(function() {
			$('#neworedit').attr("display","none");
			});
		 
		$(".editdetails").click(function (showdetails){
			$('#name').text(${serverItem});
			$('#description').text(${serverItem});
			$('#url').text(${serverItem});
			$('#username').text(${serverItem});
			$('#password').text(${serverItem});
			$('#email').text(${serverItem});
			$('#neworedit').removeAttr("display");
		});
		} );

	function confirmDelete(name) {
		if (confirm("Are you sure you want to delete " + name + "?")) {
			document.location.href = '${pageContext.request.contextPath}/module/integration/purgeServer.form?name=' + name;
		}
	}

</script>

<style>
	.small { font-size: x-small; }
</style>

<h2><spring:message code="integration.serverAdmin"/></h2>

<div >
		       			<table class="integration-data-table display">
			<thead>
				<tr>
					<th>Name</th>
					<th>Description</th>
					<th>URL</th>
					<th align="center" width="1%">Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${serverItems}" var="serverItem" >
					<tr>
						<td width="20%" nowrap>
							${serverItem}
						</td>
						<td width="20%">
							${serverItem}
						</td>
						<td width="20%">
							${serverItem}
						</td>
						<td width="1%" align="center" nowrap>
						&nbsp;
							<a href="manageDatasets.form?name=${serverItem}">
										<img width="20" height="20" src="${pageContext.request.contextPath}/moduleResources/integration/images/attributes.png" border="0"/>	
									</a>
							&nbsp;
							<a href="#" class="editdetails"><img src="<c:url value='/images/edit.gif'/>" border="0"/></a>
							&nbsp;
							<a href="javascript:confirmDelete('${serverItem}');"><img src="<c:url value='/images/trash.gif'/>" border="0"/></a>
						</td>
					</tr>
				</c:forEach>	
			</tbody>
			<tfoot>
			</tfoot>
		</table>
		<button id="New" class="newdetails">Add New</button>
		</div>
		<div id="neworedit" >
		<form id="detailsedit">
		<table><tbody>
<tr>
<td>Name</td>
<td>:</td>
<td><input id="name" type="text" name="name" size="20"></td>
</tr>
<tr>
<td>Description</td>
<td>:</td>
<td><input id="description" type="text" name="description" size="20"></td>
</tr>
<tr>
<td>URL</td>
<td>:</td>
<td><input id="url" type="text" name="url" size="20"></td>
</tr>
<tr>
<td>User Name</td>
<td>:</td>
<td><input id="uname" type="text" name="uname" size="20"></td>
</tr>
<tr>
<td>Password</td>
<td>:</td>
<td><input id="password" type="password" name="password" size="20"></td>
</tr>
<tr>
<td>EmailID</td>
<td>:</td>
<td><input id="email" type="text" name="email" size="20"></td>
</tr>
<tr>
<td></td>
<td></td>
<td></td>
</tr>
<tr>
<td></td>
<td></td>
<td><input type="submit" name="submit" value="Save"> <input
type="reset" value="Cancel"></td>
</tr>
</tbody>
</table>
</form>
		</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>