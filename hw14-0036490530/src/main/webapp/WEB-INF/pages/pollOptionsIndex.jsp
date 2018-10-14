<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
</head>
<body>
	<a href="index.html">Available polls</a>
	<h1>${poll.getTitle()}</h1>
	<p>${poll.getMessage()}</p>

	<ul>
		<c:forEach var="pollOption" items="${pollOptions}">

			<li><a href="poll-voting?id=${pollOption.getId()}">${pollOption.getOptionTitle()}</a></li>

		</c:forEach>
	</ul>
</body>
</html>