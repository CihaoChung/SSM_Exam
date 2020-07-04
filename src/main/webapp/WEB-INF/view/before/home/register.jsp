<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--<%@include file="../../after/common/header.jsp" %>--%>
<%@include file="../common/header.jsp" %>
<style>
		.tm_login_body{
			background:url('${wadewhy}/resources/home/images/001.jpg');
			background-size:cover;
			-moz-background-size:cover;
			background-repeat:no-repeat;
		}
		.tm_login_container{ width:500px; margin:200px auto; clear:both}
		.tm_login_title{
			height:80px;
			margin:10px 0 15px 0;
			background:#fff;
			text-align:center;
			border-bottom:solid 1px #eee;
		}
		.tm_login_title img{
			height:50px;
		}
		.tm_login_title span{
			font-size:22px; 
			line-height:80px;
			font-family:'Microsoft Yahei',Tahoma, Geneva, 'Simsun';
		}
		.tm_login_form{ 
			width:100%; 
			height:320px;
			clear:both; 
			-moz-border-radius:8px;
			-webkit-border-radius:8px;
			border-radius:8px;
			padding:1px;
			background:#fff;
		}
		.tm_login_table{ width:400px; margin:20px auto;}
		.tm_login_table tr th{ width:100px;}
		.tm_login_table tr td{ width:300px; text-align:left}

		.tm_login_title_table{ width:400px; margin:0px auto;}
		.tm_login_title_table tr th{ width:110px;}
		.tm_login_title_table tr td{ width:310px; text-align:left}
		
		.tm_login_foot{ width:100%; line-height:20px; text-align:center; clear:both; margin:20px 0}
		
		
		html { overflow: hidden; } 
		body { overflow: hidden; } 

		.layui-layer-btn{text-align:center !important;}
	</style>
<body class="tm_login_body">

	<div class="tm_login_container">
    	<div class="tm_login_form">
			<div class="tm_login_title">
				<table border="0" cellpadding="0" cellspacing="0" class="tm_login_title_table">
					<tbody>
					<tr>
						<th><img src="${wadewhy}/resources/home/images/logo_min.png" align="absmiddle"></th>
						<td><span>wadewhy考试系统注册页面</span></td>
					</tr>
				</tbody></table>
			</div>
				<form id="add-form" method="post">
					<table>
						<tr>
							<td align="right">考生账号:</td>
							<td><input type="text" id="add-name" name="name" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考生账号'" ></td>
						</tr>
						<tr>
							<td align="right">所属学科:</td>
							<td>
								<select name="subjectid" class="easyui-combobox" panelHeight="auto" style="width:268px" data-options="editable:false,required:true, missingMessage:'请选择学科'">
									<c:forEach items="${subjectList }" var="subject">
										<option value="${subject.id }">${subject.name }</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">考生密码:</td>
							<td><input type="password" id="add-password" name="password" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考生密码'" ></td>
						</tr>
						<tr>
							<td align="right">考生姓名:</td>
							<td><input type="text" id="add-truename" name="truename" class="wu-text" ></td>
						</tr>
						<tr>
							<td align="right">考生手机:</td>
							<td><input type="text" id="add-tel" name="tel" class="wu-text" ></td>
						</tr>
					</table>
				</form>
				<div style="margin-top:10px">
					<button type="button" class="tm_btn tm_btn_primary" style="width:50%" onclick="doLogin();">确定</button>
					<button type="button" class="tm_btn" onclick="tm.goRegister();" style="width:40%">重置</button>
				</div>
        </div>
		
		<%@include file="../common/footer.jsp"%>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){ 
			checkBrowser();
		}); 

		function getBrowserInfo(){
			try{
				var Sys = {};
				var ua = navigator.userAgent.toLowerCase();
				console.log(ua);
				var re =/(msie|trident|firefox|chrome|opera|version).*?([\d.]+)/;
				var m = ua.match(re);
				Sys.browser = m[1].replace(/version/, "'safari");
				Sys.ver = m[2];
				return Sys;
			}catch(e){}
		}

		function checkBrowser(){
			var tmBrowser = getBrowserInfo();
			var isSupportedBrowser = false;
			if(tmBrowser){
				if(tmBrowser.browser == "firefox" || tmBrowser.browser == "chrome"){
					isSupportedBrowser = true;
				}
			}
			if(!isSupportedBrowser){
				layer.open({
					title:"浏览器提示", 
					content:"为达到最佳使用效果，请使用Chrome、FireFox、或360极速浏览器访问系统。",
					btnAlign: "c"
				});
			}
		}


		var tm = {
			doReset : function(){
				$("input[name='name']").val('');
				$("input[name='password']").val('');
			},
			goRegister : function(){
				document.getElementById("add-form").reset();
			}

		};
		  function doLogin(){
			console.log("111111");
			var validate = $("#add-form").form("validate");
			console.log(validate);
			if(!validate){
				$.messager.alert("消息提醒","请检查你输入的数据!","warning");
				return;
			}
			var data = $("#add-form").serialize();
			console.log(data);
			$.ajax({
				url:'${wadewhy}/after/student/add.action',
				dataType:'json',
				type:'post',
				data:data,
				success:function(data){
					if(data.type == 'success'){
						$.messager.alert('信息提示','注册成功！','info');
						window.location="${wadewhy}/before/sys/toLogin.action";
					}else{
						$.messager.alert('信息提示',data.msg,'warning');
					}
				}
			});
		}

	</script>
</body>
</html>