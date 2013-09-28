<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="localInclude.jsp" %>
<openmrs:require privilege="View Server" otherwise="/login.htm" redirect="/module/integration/integrationServerAdmin.form" />
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		
		$('#addOrEditPopup').dialog({
			autoOpen: false,
			modal: true,
			title: 'Add Or Edit Server',
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
		
		$(".newdetails").click( function() {
			$('#addOrEditPopup').dialog('open');
		});
		
		$(".cancel").click( function() {
			$('#addOrEditPopup').dialog('close');		
		});
		});

	function confirmDelete(name) {
		if(confirm('<spring:message code="integration.confirm.serverDeletion"/>'))
		 {
			 $.post("${pageContext.request.contextPath}/module/integration/deleteServer.form",{serverName: name});	
			 location.reload();
	}
		
	function updateServer(name) {
		if(confirm('<spring:message code="integration.confirm.serverUpdate"/>'))
		{
			 $.post("${pageContext.request.contextPath}/module/integration/updateServer.form?serverName"+name);	
			 location.reload();
		}
	}
		
	function testConnection(name) {
			 $.post("${pageContext.request.contextPath}/module/integration/testConnection.form?serverName="+name);	
			 location.reload();
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
			$("#transportType").val($.trim($("#stransportType"+id).html()));
					$('#addOrEditPopup').dialog('open');
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
					<th><spring:message code="integration.general.name"/></th>
					<th><spring:message code="integration.general.description"/></th>
					<th><spring:message code="integration.general.url"/></th>
					<th><spring:message code="integration.general.lastUpdated"/></th>
					<th align="center" width="1%"><spring:message code="integration.general.actions"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${serverItems}" var="serverItem" >
					<tr id="${serverItem.id}" >
					<td id="sid${serverItem.id}" STYLE=display:NONE >
							${serverItem.id}
						</td>
						<td width="20%" nowrap id="sname${serverItem.id}">
							${serverItem.serverName}
						</td>
						<td width="20%" id="sdescription${serverItem.id}">
							${serverItem.serverDescription}
						</td>
						<td width="20%" id="surl${serverItem.id}">
							${serverItem.url}
						</td>
						<td width="10%" id="lastUpdated${serverItem.id}">
							${serverItem.lastUpdated}
						</td>
						<td id="suserName${serverItem.id}" STYLE=display:NONE>
							${serverItem.userName}
						</td>
						<td id="spassword${serverItem.id}" STYLE=display:NONE>
							${serverItem.password}
						</td>
						<td id="semail${serverItem.id}" STYLE=display:NONE>
							${serverItem.emailorurl}
						</td>
						<td id="smasterTemplate${serverItem.id}" STYLE=display:NONE>
							${serverItem.masterTemplate}
						</td>
						<td id="stransportType${serverItem.transportType}" STYLE=display:NONE>
							${serverItem.transportType}
						</td>
						<td width="1%" align="center" nowrap >
						&nbsp;
						<openmrs:hasPrivilege privilege="Manage Locations">
							<a href="locationMapping.form?name=${serverItem.serverName}">
							</openmrs:hasPrivilege>
										<img width="20" height="20" src="${pageContext.request.contextPath}/moduleResources/integration/images/mapicon.png" border="0" title='<spring:message code="integration.tooltips.locationMapping"/>'/>	
									<openmrs:hasPrivilege privilege="Manage Locations">
									</a>
									</openmrs:hasPrivilege>
						&nbsp;
						<openmrs:hasPrivilege privilege="Manage Report Templates">
							<a href="manageReportTemplates.form?name=${serverItem.serverName}">
							</openmrs:hasPrivilege>
										<img width="20" height="20" src="${pageContext.request.contextPath}/moduleResources/integration/images/attributes.png" border="0" title='<spring:message code="integration.tooltips.viewReportTemplates"/>'/>	
									<openmrs:hasPrivilege privilege="Manage Report Templates">
									</a>
									</openmrs:hasPrivilege>
							&nbsp;
							<openmrs:hasPrivilege privilege="Manage Server">
							<a href="javascript:editServer('${serverItem.id}');">
							</openmrs:hasPrivilege>
							<img src="<c:url value='/images/edit.gif'/>" border="0" title='<spring:message code="integration.tooltips.editServer"/>'/>
							<openmrs:hasPrivilege privilege="Manage Server">
							</a>
							</openmrs:hasPrivilege>
							&nbsp;
							<openmrs:hasPrivilege privilege="Manage Server">
							<a href="javascript:confirmDelete('${serverItem.serverName}');">
							</openmrs:hasPrivilege>
							<img src="<c:url value='/images/trash.gif'/>" border="0" title='<spring:message code="integration.tooltips.deleteServer"/>'/>
							<openmrs:hasPrivilege privilege="Manage Server">
							</a>
							</openmrs:hasPrivilege>
							&nbsp;
							<openmrs:hasPrivilege privilege="Manage Server">
							<a href="javascript:testConnection'${serverItem.serverName}');">
							</openmrs:hasPrivilege>
										<img width="20" height="20" src="${pageContext.request.contextPath}/moduleResources/integration/images/lightning-icon.png" border="0" title='<spring:message code="integration.tooltips.testServerConnection"/>'/>	
									<openmrs:hasPrivilege privilege="Manage Server">
									</a>
									</openmrs:hasPrivilege>
									
							&nbsp;
							<openmrs:hasPrivilege privilege="Manage Server">
							<a href="javascript:updateServerData'${serverItem.serverName}');">
							</openmrs:hasPrivilege>
										<img width="20" height="20" src="${pageContext.request.contextPath}/moduleResources/integration/images/Updateicon.png" border="0" title='<spring:message code="integration.tooltips.updateServerData"/>'/>	
									<openmrs:hasPrivilege privilege="Manage Server">
									</a>
									</openmrs:hasPrivilege>
									
						</td>
					</tr>
				</c:forEach>	
			</tbody>
		</table>
		<div id="addOrEditPopup">
					
					<div>
						<form:form modelAttribute="integrationServer" method="post" id="detailsedit" action="saveIntegrationServer.form" >
					<table>
						<tbody>	
							<tr>
								<td><spring:message code="integration.general.name"/></td>
								<td>:</td>
								<td>
								<form:hidden path="id" id="id"/>
								<form:input path="serverName" id="servername" size="40" /></td>
							</tr>
							<tr>
								<td><spring:message code="integration.general.description"/></td>
								<td>:</td>
								<td>
								<form:hidden path="masterTemplate" id="masterTemplate"/>
								<form:input path="serverDescription" id="description" size="40" /></td>
							</tr>
							<tr>
								<td><spring:message code="integration.general.url"/></td>
								<td>:</td>
								<td><form:input path="url" id="url" size="40" /></td>
							</tr>
							<tr>
								<td><spring:message code="integration.general.userName"/></td>
								<td>:</td>
								<td><form:input path="userName" id="uname" size="20" /></td>
							</tr>
							<tr>
								<td><spring:message code="integration.general.password"/></td>
								<td>:</td>
								<td><form:password path="password" id="password" size="20"/></td>
							</tr>
							<tr>
								<td><spring:message code="integration.general.transport"/></td>
								<td>:</td>
								<td>
									<form:select path="transportType" id="transportType">
									  <option value="Email"><spring:message code="integration.general.email"/></option>
									  <option value="Url"><spring:message code="integration.general.url"/></option>
									 </form:select>
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
								<td><input type="submit" name="submit" value='<spring:message code="integration.button.save"/>'/> <input
								type="reset" value='<spring:message code="integration.button.cancel"/>' class="cancel">
								</td>
							</tr>
						</tbody>
					</table>
				</form:form>
					</div>				
				</div>
		 <div id="button" align="right">
                    <button id="addNew" class="newdetails">
                   <spring:message code="integration.button.addNew"/>
                    </button>
            </div>
		</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>