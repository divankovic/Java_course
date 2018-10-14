<%@ page session = "true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String color = (String)session.getAttribute("pickedBgCol");
	if(color==null || color.isEmpty()){
		color = "WHITE";
	}
%>

body {
    background-color:<%=color%>;
}