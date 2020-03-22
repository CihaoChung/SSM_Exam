<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:forEach items="${thirdMenuList}" var="thirdMenu">
   <a href="#" class="easyui-linkbutton" iconCls="${thirdMenu.icon }" onclick="${thirdMenu.href}" plain="true">${thirdMenu.title }</a>
</c:forEach>