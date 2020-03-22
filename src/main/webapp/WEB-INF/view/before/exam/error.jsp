<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>  
<html>  
<head>  
    <meta charset="UTF-8">  
    <meta name="robots" content="noindex,nofollow">  
    <title>出错了</title>  
    <style>  
        body{font-size: 14px;font-family: 'helvetica neue',tahoma,arial,'hiragino sans gb','microsoft yahei','Simsun',sans-serif; background-color:#fff; color:#808080;}  
        .wrap{margin:200px auto;width:510px;}  
        td{text-align:left; padding:2px 10px;}  
        td.header{font-size:22px; padding-bottom:10px; color:#000;}  
        td.check-info{padding-top:20px;}  
        a{color:#328ce5; text-decoration:none;}  
        a:hover{text-decoration:underline;}  
    </style>  
</head>  
<body>  
    <div class="wrap">  
        <table>  
            <tr>  
                <td rowspan="5" style=""><img src="../../resources/home/exam/images/error.jpg" alt="错误页面"></td>  
                <td class="header">很抱歉！当前操作出现错误</td>  
            </tr>  
            <tr><td>原因：${msg }</td></tr>  
            <tr><td class="check-info">点击前往首页<a href="../user/index">考生首页</a></td></tr>  
        </table>  
    </div>  
</body>  
</html> 