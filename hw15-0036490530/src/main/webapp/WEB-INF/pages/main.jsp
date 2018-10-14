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

	<c:if test="<%=!loggedIn%>">

		<h1>Login</h1>

		<form action="main" method="post">

			<div>
				<div>
					<span class="formLabel">Username</span><input type="text"
						name="username" value='<c:out value="${form.username}"/>'
						size="10">
				</div>
				<c:if test="${form.hasError('nick')}">
					<div class="error">
						<c:out value="${form.getError('nick')}" />
					</div>
				</c:if>
			</div>

			<div>
				<div>
					<span class="formLabel">password</span><input type="password"
						name="password" size="10">
				</div>
				<c:if test="${form.hasError('password')}">
					<div class="error">
						<c:out value="${form.getError('password')}" />
					</div>
				</c:if>
			</div>

			<div class="formControls">
				<span class="formLabel">&nbsp;</span> <input type="submit"
					value="Login">
			</div>

		</form>

		<p>
			Don't have an account?<a href="register"> Register here.</a>
		</p>

	</c:if>

	<c:if test="${!users.isEmpty()}">
		<h1>Registered authors</h1>
		<ul>
			<c:forEach var="user" items="${users}">

				<li><a href="author/${user.getNick()}">${user.getNick()}</a></li>

			</c:forEach>
		</ul>
	</c:if>
</body>
</html>
