<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
 <head>
 		<meta charset="utf-8">
  		<link rel="stylesheet" href="styles.jsp">
 </head>
 <body>
  <a href="index.jsp">Home</a>
  <ul>
   <li><a href="setcolor?pickedBgCol=WHITE">WHITE</a></li>
   <li><a href="setcolor?pickedBgCol=RED">RED</a></li>
   <li><a href="setcolor?pickedBgCol=LAWNGREEN">GREEN</a></li>
   <li><a href="setcolor?pickedBgCol=CYAN">CYAN</a></li>
  </ul>
 </body>
</html>