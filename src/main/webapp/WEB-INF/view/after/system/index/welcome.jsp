<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>欢迎页面</title>
</head>
<body>
<div title="欢迎使用" style="padding:20px;overflow:hidden; color:red; " >
	<p style="font-size: 50px; line-height: 60px; height: 60px;">${admin.username}</p>
	<p style="font-size: 25px; line-height: 30px; height: 30px;">欢迎使用wadewhy考试管理系统</p>
	<h1><a href="http://wadewhy.xyz">查看博客地址</a></h1>
  	<p>开发人员：【wadewhy】</p>
  	<p>开发周期：2020/03/14 --- 2020/03/24（共计10天）</p>
	<p>由于之前一直在使用Spring Boot，Spring Cloud，很久没有使用SSM框架来开发，
		因此花了10天左右的时间开发了此系统。本系统将开源并且继续维护，可在本博客最后端查看源代码链接，
		上传到本人github上。需要数据库文件的可留言或加好友寻求。并且写了一份详尽的开发文档。</p>
  	<hr />
  	<h2>系统环境</h2>
  	<p>系统环境：Windows</p>
	<p>开发工具：IDEA</p>
	<p>Java版本：JDK 1.8</p>
	<p>服务器：tomcat 8.5</p>
	<p>数据库：MySQL 5.7</p>
	<h3>
		系统采用技术： spring+springMVC+mybaits+EasyUI+jQuery+Ajax+面向接口编程+Shiro安全框架
		+mybatis_generator代码自动生成工具
	</h3>
	<h2>系统模块</h2>
	<p>菜单权限管理模块</p>
	<p>角色权限管理模块</p>
	<p>用户角色管理模块</p>
	<p>专业课程管理模块</p>
	<p>考试模板管理模块</p>
	<p>考试试题管理模块</p>
	<p>学生考试管理模块</p>
	<p>学生用户管理模块</p>
	<p>成绩统计管理模块</p>
	<p>系统日志管理模块</p>
	<p></p>
</div>
</body>
</html>