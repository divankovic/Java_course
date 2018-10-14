<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	boolean loggedIn = false;
	if (request.getSession().getAttribute("current.user.id") != null) {
		loggedIn = true;
	}
%>

<html>
<head>
	<meta charset="UTF-8">
<style type="text/css">
	.error {
		font-size: 0.9em;
		color: #FF0000;
		padding-left: 110px;
	}

	.formLabel {
		display: inline-block;
		width: 100px;
		font-weight: bold;
		text-align: right;
		padding-right: 10px;
	}

	.formControls {
		margin-top: 10px;
	}
	</style>
</head>

<body>
	<jsp:include page="header.jsp" />
	
	<a href="../<%= request.getSession().getAttribute("current.user.nick")%>">Blogs</a>

		<form action="${formAction}" method="post">
			<div>
			<div>
				<span class="formLabel">Title</span><input type="text" name="title" size="30"
													 value='<c:out value ="${form.title}"/>'>
			</div>
			<c:if test="${form.hasError('title')}">
					<div class="error">
						<c:out value="${form.getError('title')}" />
					</div>
			</c:if>
			</div>
			
			<div>
			<div>
				<span class="formLabel"></span><textarea name="content" cols = "70" rows = "10"
				>${form.content}</textarea>
			</div>
			<c:if test="${form.hasError('content')}">
					<div class="error">
						<c:out value="${form.getError('content')}" />
					</div>
			</c:if>
			</div>
			
			
			<div class="formControls">
				<span class="formLabel">&nbsp;</span> <input type="submit" name="action"
					value="${postAction}">
			</div>

		</form>
</body>
</html>
