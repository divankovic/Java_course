<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">

</head>
<body>
	<h1>Available polls</h1>
	<p>Click on the link of the poll to participate in voting.</p>
	
	<ul>
		<c:forEach var="poll" items="${polls}">

			<li><a href="/servleti/poll-options?pollID=${poll.getId()}">${poll.getTitle()}</a></li>

		</c:forEach>
	</ul>
</body>
</html>