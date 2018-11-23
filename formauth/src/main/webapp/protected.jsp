<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<h2>Protected page!</h2>
<p>This page is only accessible by users with <em>poweruser</em> role</p>

<p>Name of current user: <strong><c:out value="${ pageContext.request.remoteUser }"/></strong></p>

<p><a href="logoff.jsp">Log off</a></p>

</body>
</html>
