<%@ page import="java.text.SimpleDateFormat,java.util.Date"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="styles.jsp">
</head>
<body>
	<a href="index.jsp">Home</a>

	<%
		ServletContext sc = request.getServletContext();
		long start = (Long) sc.getAttribute("start");
		long current = System.currentTimeMillis();

		long elapsed = current-start;
		
		long seconds = elapsed / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;
		
		String elapsedTime = String.format("%d days %d hours %d minutes %d seconds", days, hours%24, minutes%60, seconds%60);
	%>
	<p>
		The application has been running for <%=elapsedTime%>
	</p>
</body>
</html>