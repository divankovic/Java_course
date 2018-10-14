<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="styles.jsp">
</head>
<body>
	<a href="index.jsp">Home</a>
	<h1>Band voting</h1>
	<p>Click on the link of your favorite band to vote!</p>

	<ul>
		<c:forEach var="band" items="${bands}">

			<li><a href="glasanje-glasaj?id=${band.id}">${band.name}</a></li>

		</c:forEach>
	</ul>
</body>
</html>