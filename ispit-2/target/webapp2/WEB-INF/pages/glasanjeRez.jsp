<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
 <head>
 		<meta charset="utf-8">
  		<link rel="stylesheet" href="styles.jsp">
 		<style type="text/css">
 			table.rez td{text-align:center;}		
 		</style>
 </head>
 <body>
  <a href="./glasanje">Band voting</a>
  
  <h1>Voting results</h1>
  <p>The results of the voting are shown in the table:</p>
  
  <table border="1" class="rez">
  	<thead><tr><th>Band</th><th>Total votes</th></tr></thead>
 	
 	<tbody>
		 <c:forEach var="band" items="${results}">

			<tr><td>${band.name}</td><td>${band.voteCount}</td></tr>

		</c:forEach>
 	</tbody>

  </table>
 
  <h2>Graphical results display</h2>
  <img alt="Pie-chart" src="./glasanje-grafika" width="600" height="400"/>
 
  <h2>Results in XLS format</h2>
  <p>Results in XLS format are available <a href="./glasanje-xls">here</a></p>

  <h2>Song examples of winning bands:</h2>
  
  <c:forEach var="band" items="${topBands}">
	<li><a href="${band.songUrl}" target="_blank">${band.name}</a></li>
  </c:forEach>
	 
 </body>
</html>