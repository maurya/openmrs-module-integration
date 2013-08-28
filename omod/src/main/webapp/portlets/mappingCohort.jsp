<%@ include file="/WEB-INF/template/include.jsp"%>	
		<div>
					<b class="boxHeader"><spring:message code="integration.Mapping"/> ${model.type} </b>
					<div class="box">
					<form method="post" id="detailsedit" >
					<table>
						<tbody>	
							<tr>
								<td>${model.type}</td>
								<td>:</td>
								<td>
								<input id="id" type="hidden" value="${model.portletId}"/>
								<input id="${model.type}name${model.portletId}" type="text" size="40"/></td>
							</tr>
							<tr>
								<td><spring:message code="integration.general.mappedTo"/></td>
								<td>:</td>
								<td>
								<select name='cohorts${model.portletId}'>
   								 		<c:forEach items="${model.cohorts}" var="cohort">
          						  		<option id="${cohort.uuid}" value="${cohort}">${cohort}</option>
   							 			</c:forEach>
								</select>
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
								<td><a href="javascript:save${model.type}(${model.portletId});"><input id="${model.portletId}" type="button" value='<spring:message code="integration.button.save"/>' /> </a><input id="cancel${model.portletId}"
								type="reset" value='<spring:message code="integration.button.cancel"/>' class="cancel">
								</td>
							</tr>
						</tbody>
					</table>
				</form>
					</div>					
		</div>