<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String serverURL = "http://app.actiz.com:8280/webtest";
	request.setAttribute("uploadSingleURL", serverURL+"/upload/s3");
	request.setAttribute("uploadURL", serverURL+"/upload/ss3");
	request.setAttribute("uploadURL4Json", serverURL+"/upload/ss3?dataType=json");
	request.setAttribute("uploadURL4Commons", serverURL+"/upload/cf");
%>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>