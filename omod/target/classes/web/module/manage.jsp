<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<form method="POST" >
<p>Hello ${user.systemId}!</p>
<p>These are the Id's of male patients as stored in the database : ${patients}</p>
<p>This is the count of male patients : ${count}</p>
<input type="submit" value="Search"/>
</form>
<%@ include file="/WEB-INF/template/footer.jsp"%>
