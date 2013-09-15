<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="localInclude.jsp" %>
 <script>
            $(document).ready(function(){
              
                    
           $(".integration-data-table").dataTable( {
			"bPaginate": false,
			"iDisplayLength": 25,
			"bLengthChange": false,
			"bFilter": false,
			"bSort": true,
			"bInfo": false,
			"bAutoWidth": false
		} );
           
           $("#treeViewDiv1").jstree({
            	  "json_data" : {
                       "data" : ${json}			  
					  },"themes" : {
			"theme" : "apple",
			"dots" : false,
			"icons" : false
		},
                    "plugins" : [ "themes", "json_data", "ui" ]
                });
                    
              $("#treeViewDiv2").jstree({
            	  "json_data" : {
                      "data":[
                          {
                              "data" : "Search engines",
                              "children" :[
                                           {"data":"Yahoo"},
                                           {"data":"Bing"},
                                           {"data":"Google", "children":[{"data":"Youtube"},{"data":"Gmail"},{"data":"Orkut"}]}
                                          ]
                          },
                          {
                              "data" : "Networking sites",
                              "children" :[
                                  {"data":"Facebook"},
                                  {"data":"Twitter"}
                              ]
                          }
                      ]
                  },"themes" : {
			"theme" : "apple",
			"dots" : false,
			"icons" : false
		},
                    "plugins" : [ "themes", "json_data", "ui" ]
                });
				});
                
			function sendorgunit() {
			var node=$("#treeViewDiv1").jstree("get_selected").text();
			alert(node);
		}
        </script>
    <div id="breadCrumbs">
<a href="integrationServerAdmin.form"><spring:message code="integration.return.serverAdministration"/></a>|
</div>
<h2><spring:message code="integration.general.locationsFor"/> <spring:message code="integration.serverAdmin"/> : ${server.serverName}</h2>

	    <div id=mainDiv>
	        <div id="treeViewDiv1">
	        </div>
			<a href="javascript:sendorgunit();">
			<button class="dhisbutton" >
                   Send into Org Units
                    </button>
					</a>
	        <div id="selectionDiv">
	        	<table id="table" class="integration-data-table display">
			<thead>
				<tr>
					<th><spring:message code="integration.general.dhisOrgUnits"/></th>
					<th><spring:message code="integration.general.openMRSLocations"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${serverItems}" var="serverItem" >
					<tr id="${serverItem.integrationServerId}" >
					<td id="sid${serverItem.integrationServerId}" >
							${serverItem.integrationServerId}
						</td>
						<td width="20%" nowrap id="sname${serverItem.integrationServerId}">
							${serverItem.serverName}
						</td>
					</tr>
				</c:forEach>	
			</tbody>
		</table>
	        </div>
			<button class="dhisbutton" >
				Add for the Org unit
                    </button>
	        <div id="treeViewDiv2">
	        </div>
	   </div>