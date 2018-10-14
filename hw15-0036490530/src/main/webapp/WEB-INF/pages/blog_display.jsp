<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<meta charset="UTF-8">
	</head>
<body>
	
	<jsp:include page="header.jsp" />
	
	<a href="../${nick}">Blogs</a>
	
	
	<h1>
		<c:out value="${blog.title}" />
	</h1>
	<p>
		<c:out value="${blog.text}" />
	</p>
	
	<c:if test="${canModify}">
		<a href="edit?blogId=${blog.getId()}">Edit blog</a>
	</c:if>
	
	<c:if test="${!blog.comments.isEmpty()}">
		<ul>
			<c:forEach var="comment" items="${blog.getComments()}">
				<li><div style="font-weight: bold">
						<c:out value="${comment.userNick}" />
						<c:out value="${comment.postedOn}" />
					</div>
					<div style="padding-bottom=5px;">
						<c:out value="${comment.message}" />
					</div>
				</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<form action="${formAction}" method="post">

			<div>
				<span class="formLabel"></span><textarea name="text" required cols = "50" rows = "2"></textarea>
			</div>

			<div class="formControls">
				<span class="formLabel">&nbsp;</span> <input type="submit" name="action"
					value="comment">
			</div>

	</form>
		
</body>
</html>
