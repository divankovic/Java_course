<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
	<head>
		<meta charset="utf-8">
		<link rel="stylesheet" href="styles.jsp">
  	</head>
	<body>
		<ul>
			<li><a href="colors.jsp">Background color chooser</a></li>
			<li><a href="trigonometric?a=0&b=90">Trigonometric values</a></li>
			<li><a href="stories/funny.jsp">Funny</a>
			<li><a href="report.jsp">OS usage report</a>
			<li><a href="powers?a=1&b=100&n=3">Powers xls</a>
			<li><a href="appinfo.jsp">App info</a>			
			<li><a href="glasanje">Band voting</a>			
		</ul>
		
		<form action="trigonometric" method="GET">
 			Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
 			Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
 			<input type="submit" value="Tabeliraj">
 			<input type="reset" value="Reset">
		</form>

	</body>
</html>