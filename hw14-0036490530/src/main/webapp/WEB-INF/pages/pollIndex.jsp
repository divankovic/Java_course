<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<meta charset="utf-8">

</head>
<body>
	<h1>Available polls</h1>
	<p>Click on the link of the poll to participate in voting.</p>
	
	<%
		String context = request.getContextPath().toString();
	%>
	
	<ul>
		<c:forEach var="poll" items="${polls}">

			<li><a href="poll-options?pollID=${poll.getId()}">${poll.getTitle()}</a></li>

		</c:forEach>
		
		
	</ul>
	
</body>
</html>