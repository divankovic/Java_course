<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<meta charset="utf-8">
		<head>
  			<link rel="stylesheet" href="styles.jsp">
 		</head>
	</head>
	<body>
		<a href="index.jsp">Home</a>
		<h1>Trigonometric function values</h1>	
  		
		<table>
			<thead>
				<tr><th>Number</th><th>sin</th><th>cos</th></tr>
			</thead>

			<tbody>
				<c:forEach var="item" items ="${values}">
					<tr><td>${item.number}</td><td>${item.sinValue}</td><td>${item.cosValue}</td></tr>
				</c:forEach>
			
			</tbody>
		
		</table>
	</body>
</html>