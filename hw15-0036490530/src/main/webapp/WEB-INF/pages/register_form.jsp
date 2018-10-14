<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Register</title>
		
		<style type="text/css">
		.error {
		   font-family: fantasy;
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
	
		<a href="main">Home</a>
  
		<h1>
			Register
		</h1>

		<form action="register" method="post">
		
		<div>
		 <div>
		  <span class="formLabel">First name</span><input type="text" name="firstName" value='<c:out value="${form.firstName}"/>' size="10">
		 </div>
		 <c:if test="${form.hasError('firstName')}">
		 <div class="error"><c:out value="${form.getError('firstName')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Last name</span><input type="text" name="lastName" value='<c:out value="${form.lastName}"/>' size="20">
		 </div>
		 <c:if test="${form.hasError('lastName')}">
		 <div class="error"><c:out value="${form.getError('lastName')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Email</span><input type="text" name="email" value='<c:out value="${form.email}"/>' size="20">
		 </div>
		 <c:if test="${form.hasError('email')}">
		 <div class="error"><c:out value="${form.getError('email')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Username</span><input type="text" name="nick" value='<c:out value="${form.nick}"/>' size="10">
		 </div>
		 <c:if test="${form.hasError('nick')}">
		 <div class="error"><c:out value="${form.getError('nick')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Password</span><input type="password" name="password" value='<c:out value="${form.password}"/>' size="10">
		 </div>
		 <c:if test="${form.hasError('password')}">
		 <div class="error"><c:out value="${form.getError('password')}"/></div>
		 </c:if>
		</div>
		
		
		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="Register" value="Register">
		</div>
		
		</form>

	</body>
</html>
