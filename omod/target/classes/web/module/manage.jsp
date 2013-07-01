<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<p>Hello ${user.systemId}!</p>
<p>These are the Id's of male patients as stored in the database : ${patients}</p>
<p>This is the count of male patients : ${count}</p>

<%@ include file="/WEB-INF/template/footer.jsp"%>