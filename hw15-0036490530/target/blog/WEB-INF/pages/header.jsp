<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%
	boolean loggedIn = false;
	if (request.getSession().getAttribute("current.user.id") != null) {
		loggedIn = true;
	}
%>
<c:choose>
	<c:when test="<%=loggedIn%>">
		<%
			String firstName = (String) request.getSession().getAttribute("current.user.fn");
					String lastName = (String) request.getSession().getAttribute("current.user.ln");
		%>
		<p><%=firstName%>
			<%=lastName%>
			<a href=<%= request.getContextPath() + "/servleti/logout"%>>logout</a>
		</p>
	</c:when>
	<c:otherwise>
		<p>Not logged in</p>
	</c:otherwise>
</c:choose>