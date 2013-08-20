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
		$(".newdetails").click( function() {
			$('.neworedit').show();
			
		});
		$(".cancel").click( function() {
			$('.neworedit').hide();
			
		});
		});

	function confirmDelete(name) {
		if(confirm("Do you want to delete this server"))
		 {
			 $.post("${pageContext.request.contextPath}/module/integration/deleteServer.form",{serverName: name});	
			 location.reload();
	}
	}
	
	function editServer(id) {
	
			$("#id").val($.trim($("#sid"+id).html()));
			$("#servername").val($.trim($("#sname"+id).html()));
			$("#description").val($.trim($("#sdescription"+id).html()));
			$("#url").val($.trim($("#surl"+id).html()));
			$("#uname").val($.trim($("#suserName"+id).html()));
			$("#password").val($.trim($("#spassword"+id).html()));
			$("#emailurl").val($.trim($("#semail"+id).html()));
			$("#masterTemplate").val($.trim($("#smasterTemplate"+id).html()));

					$('.neworedit').show();
		}

</script>

<style>
	.small { font-size: x-small; }
</style>

<h2><spring:message code="integration.serverAdmin"/></h2>

<div >
		       			<table id="table" class="integration-data-table display">
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
					<tr id="${serverItem.integrationServerId}" >
					<td id="sid${serverItem.integrationServerId}" STYLE=display:NONE >
							${serverItem.integrationServerId}
						</td>
						<td width="20%" nowrap id="sname${serverItem.integrationServerId}">
							${serverItem.serverName}
						</td>
						<td width="20%" id="sdescription${serverItem.integrationServerId}">
							${serverItem.serverDescription}
						</td>
						<td width="20%" id="surl${serverItem.integrationServerId}">
							${serverItem.url}
						</td>
						<td id="suserName${serverItem.integrationServerId}" STYLE=display:NONE>
							${serverItem.userName}
						</td>
						<td id="spassword${serverItem.integrationServerId}" STYLE=display:NONE>
							${serverItem.password}
						</td>
						<td id="semail${serverItem.integrationServerId}" STYLE=display:NONE>
							${serverItem.emailorurl}
						</td>
						<td id="smasterTemplate${serverItem.integrationServerId}" STYLE=display:NONE>
							${serverItem.masterTemplate}
						</td>
						<td width="1%" align="center" nowrap >
						&nbsp;
							<a href="locationMapping.form?name=${serverItem.serverName}">
										<img width="20" height="20" src="${pageContext.request.contextPath}/moduleResources/integration/images/mapicon.png" border="0"/>	
									</a>
						&nbsp;
							<a href="manageReportTemplates.form?name=${serverItem.serverName}">
										<img width="20" height="20" src="${pageContext.request.contextPath}/moduleResources/integration/images/attributes.png" border="0"/>	
									</a>
							&nbsp;
							<a href="javascript:editServer('${serverItem.integrationServerId}');"><img src="<c:url value='/images/edit.gif'/>" border="0"/></a>
							&nbsp;
							<a href="javascript:confirmDelete('${serverItem.serverName}');"><img src="<c:url value='/images/trash.gif'/>" border="0"/></a>
						</td>
					</tr>
				</c:forEach>	
			</tbody>
			<tfoot>
			</tfoot>
		</table>
		 <div id="button" align="right">
                    <button id="addNew" class="newdetails">
                   Add New
                    </button>
            </div>
		</div>
		<div id="neworedit" class="neworedit" >
		<b class="boxHeader">
			
				
				Server	
				
				
			
		</b>
		<div class="box" >
				<form:form modelAttribute="integrationServer" method="post" id="detailsedit" action="saveIntegrationServer.form" >
					<table>
						<tbody>	
							<tr>
								<td>Name</td>
								<td>:</td>
								<td>
								<form:hidden path="integrationServerId" id="id"/>
								<form:input path="serverName" id="servername" size="40" /></td>
							</tr>
							<tr>
								<td>Description</td>
								<td>:</td>
								<td>
								<form:hidden path="masterTemplate" id="masterTemplate"/>
								<form:input path="serverDescription" id="description" size="40" /></td>
							</tr>
							<tr>
								<td>URL</td>
								<td>:</td>
								<td><form:input path="url" id="url" size="40" /></td>
							</tr>
							<tr>
								<td>User Name</td>
								<td>:</td>
								<td><form:input path="userName" id="uname" size="20" /></td>
							</tr>
							<tr>
								<td>Password</td>
								<td>:</td>
								<td><form:password path="password" id="password" size="20"/></td>
							</tr>
							<tr>
								<td>Transport</td>
								<td>:</td>
								<td>
									<select>
									  <option value="Email">Email</option>
									  <option value="Url">Url</option>
									</select>
									<form:input path="emailorurl" id="emailurl" size="20"/>
								</td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td><input type="submit" name="submit" value="Save"/> <input
								type="reset" value="Cancel" class="cancel">
								</td>
							</tr>
						</tbody>
					</table>
				</form:form>
		</div>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>