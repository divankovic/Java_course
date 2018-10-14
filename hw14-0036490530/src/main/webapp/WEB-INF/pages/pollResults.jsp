<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
 <head>
 		<meta charset="utf-8">
 		<style type="text/css">
 			table.rez td{text-align:center;}		
 		</style>
 </head>
 <body>
  <a href="poll-options?pollID=${poll.getId()}">${poll.getTitle()}</a>
  
  <h1>Results of ${poll.getTitle()}</h1>
  <p>The results of the voting are shown in the table:</p>
  
  <table border="1" class="rez">
  	<thead><tr><th>Option</th><th>Total votes</th></tr></thead>
 	
 	<tbody>
		 <c:forEach var="option" items="${results}">

			<tr><td>${option.getOptionTitle()}</td><td>${option.getVotesCount()}</td></tr>

		</c:forEach>
 	</tbody>

  </table>
  
  <h2>Graphical results display</h2>
  <img alt="Pie-chart" src = "poll-graphics?pollID=${poll.getId()}" width="600" height="400"/>
 
   
  <h2>Results in XLS format</h2>
  <p>Results in XLS format are available <a href="poll-xls?pollID=${poll.getId()}">here</a></p>

  <h2>Videos of the winners:</h2>
   
  <c:forEach var="option" items="${topOptions}">
	<li><a href="${option.getOptionLink()}" target="_blank">${option.getOptionTitle()}</a></li>
  </c:forEach>
	 
 </body>
</html>