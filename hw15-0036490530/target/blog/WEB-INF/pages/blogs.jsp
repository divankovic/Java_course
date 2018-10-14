<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
	<head>
		<meta charset="UTF-8">
	</head>
<body>
  <jsp:include page="header.jsp" />
  
  <a href="../main">Home</a>
  
  <c:choose>
	<c:when test="${!blogs.isEmpty()}">
		<h1>
			<c:out value="${nick}" />'s blogs
		</h1>
		<ul>
			<c:forEach var="blog" items="${blogs}">

				<li><a href="${nick}/${blog.getId()}">${blog.getTitle()}</a></li>

			</c:forEach>
		</ul>
	</c:when>
	<c:otherwise>
		<c:choose>
		<c:when test="${canAdd}">
			<h1>
				My blogs
			</h1>
		</c:when><c:otherwise>
			<h1>
				User <c:out value="${nick}" /> hasn't created a blog yet.
			</h1>
		</c:otherwise>
		</c:choose>
	</c:otherwise>
  </c:choose>
  
  <c:if test="${canAdd}">
	 <a href="${nick}/new">Add new blog</a>
  </c:if>

</body>
</html>
