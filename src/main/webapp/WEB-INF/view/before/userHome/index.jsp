<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../common/user_header.jsp"%>
<c:set var="wadewhy" value="${pageContext.request.contextPath}"/>
<frameset rows="40,*" frameborder="no" border="0" framespacing="0">
	<frame src="${wadewhy}/head.html" noresize="noresize" frameborder="no" name="top" scrolling="no" marginwidth="0" marginheight="0" target="main">
	<frameset cols="200,*" id="mainframe" frameborder="no" border="0" framespacing="0">
		<frame src="${wadewhy}/menu.html" name="menu" id="menu" marginwidth="0" marginheight="0" frameborder="0" scrolling="no" target="main">
		<frame src="${wadewhy}/before/student/welcome.action" name="main" id="main" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto" target="_self">
	</frameset>
</frameset>

<noframes>
	&lt;body&gt;您的浏览器不支持框架。&lt;/body&gt;
</noframes>


</html>