<%@ page import="java.util.Random" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String[] colors = {"#FFFF00","#FF0000","#800080","#008000","#000080","#000000"};
	Random random = new Random();
	int idx = random.nextInt()%colors.length;
	if(idx<0) idx*=-1;
	String color = colors[idx];
%>


<html>
 <head>
 		<meta charset="utf-8">
  		<link rel="stylesheet" href="../styles.jsp">
 </head>
 <body>
  	<a href="../index.jsp">Home</a>
 
 	<p>
 		<font color=<%=color %>>
 		The best thing about a Boolean is even if you are wrong,
 		you are only off by a bit.</font>
 	</p>
 
 </body>
</html>