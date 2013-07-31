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
		
		$('.neworedit').hide();
		$("#addNew").click( function() {
			$('.neworedit').show();
			
		});
		});

	function confirmDelete(name) {
		if(confirm("Do you want to delete this server"))
		 {
			$("#"+name).remove();
			 $.post(openmrsContextPath + "/module/integration/deleteServer.form",{serverName: name});	
	}
	}
	
	function editServer(name) {
		$.post("${pageContext.request.contextPath}/module/integration/getServerDetails.form",{serverName: name}, function(data) {			
			$("#servername").val(data.serverName);
			$("#description").val(data.serverDescription);
			$("#url").val(data.url);
			$("#uname").val(data.userName);
			$("#password").val(data.password);
			$("#emailurl").val(data.email);
			$('.neworedit').show();
			 	            });
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
					<tr id="${serverItem.serverName}">
						<td width="20%" nowrap>
							${serverItem.serverName}
						</td>
						<td width="20%">
							${serverItem.serverDescription}
						</td>
						<td width="20%">
							${serverItem.url}
						</td>
						<td width="1%" align="center" nowrap >
						&nbsp;
							<a href="manageReportTemplates.form?name=${serverItem.serverName}">
										<img width="20" height="20" src="${pageContext.request.contextPath}/moduleResources/integration/images/attributes.png" border="0"/>	
									</a>
							&nbsp;
							<a href="javascript:editServer('${serverItem.serverName}');"><img src="<c:url value='/images/edit.gif'/>" border="0"/></a>
							&nbsp;
							<a href="javascript:confirmDelete('${serverItem.serverName}');"><img src="<c:url value='/images/trash.gif'/>" border="0"/></a>
						</td>
					</tr>
				</c:forEach>	
			</tbody>
			<tfoot>
			</tfoot>
		</table>
		<button id="addNew" class="newdetails">Add New</button>
		</div>
		<div id="neworedit" class="neworedit" >
		<b class="boxHeader">
			
				
				Server	
				
				
			
		</b>
		<div class="box" >
		<form id="detailsedit">
		<table><tbody>	
<tr>
<td>Name</td>
<td>:</td>
<td><input id="servername" type="text" size="40"></td>
</tr>
<tr>
<td>Description</td>
<td>:</td>
<td><input id="description" type="text"size="40"></td>
</tr>
<tr>
<td>URL</td>
<td>:</td>
<td><input id="url" type="text" size="40"></td>
</tr>
<tr>
<td>User Name</td>
<td>:</td>
<td><input id="uname" type="text" size="20"></td>
</tr>
<tr>
<td>Password</td>
<td>:</td>
<td><input id="password" type="password" size="20"></td>
</tr>
<tr>
<td>Transport</td>
<td>:</td>
<td>
<select>
  <option value="Email">Email</option>
  <option value="Url">Url</option>
</select>
<input id="emailurl" type="text" size="20"></td>
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
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>