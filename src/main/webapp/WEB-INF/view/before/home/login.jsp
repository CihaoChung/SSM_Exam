<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../common/header.jsp"%>
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
						<td><span>wadewhy在线考试系统</span></td>
					</tr>
				</tbody></table>
			</div>
            <table border="0" cellpadding="5" cellspacing="0" class="tm_login_table">
                <tbody><tr>
                    <th>用户名</th>
                    <td><input type="text" class="tm_txt" name="name" maxlength="20" value="" style="width:255px"></td>
                </tr>
                <tr>
                    <th>密 &nbsp; 码</th>
                    <td><input type="password" class="tm_txt" name="password" maxlength="20" value="" style="width:255px"></td>
                </tr>
				<tr>
					<div style="margin-top:10px">
					<th ><img src="${wadewhy}/resources/admin/login/images/cpacha.png" style="margin-top: 13px"></th>
					<td >
						<div style="margin-top:-3px">
						<input style="width:50%;" type="text" name="cpacha" id="cpacha" value="" placeholder="请输入验证码" onfocus="this.placeholder=&#39;&#39;" onblur="this.placeholder=&#39;请输入验证码&#39;">
						<img id="cpacha-img" title="点击切换验证码" style="" src="${wadewhy}/after/login/getCode.action"  onclick="this.src=this.src+'?'"  >
						</div>
					</td>

					</div>

				</tr>
                <tr>
                    <th></th>
                    <td>
						<div style="margin-top:10px">
							<button type="button" class="tm_btn tm_btn_primary" style="width:50%" onclick="tm.doLogin();">登录</button>
							<button type="button" class="tm_btn" onclick="tm.goRegister();" style="width:40%">注册</button>
						</div>
                    </td>
                </tr>

            </tbody></table>
        </div>
		
		<%@include file="../common/footer.jsp"%>
	</div>
	<script type="text/javascript">
	 //解决session过期跳转到登录页并跳出iframe框架（或者layui弹出层）
    $(document).ready(function () {
        if (window != top) {
            top.location.href = location.href;
        }
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
				window.location="${wadewhy}/before/sys/register.action";
			},
			doLogin : function(){
				var username = $("input[name='name']").val();
				var userpass = $("input[name='password']").val();
				var cpacha = $("input[name='cpacha']").val();
				if(baseutil.isEmpty(username)){
					alert('没有填写用户名');
					return;
				}
				if(baseutil.isEmpty(userpass)){
					alert('没有填写登录密码');
					return;
				}
				if(baseutil.isEmpty(cpacha)){
					alert('没有填写验证码');
					return;
				}
				$(".tm_btn_primary").text('登录.....');
				$.ajax({
					type: "POST",
					url: "${wadewhy}/before/home/login.action",
					dataType: "json",
					data: {"name":username, "password":userpass,"cpacha":cpacha},
					success: function(data){
						if(data.code == 200){
							window.location="${wadewhy}/before/sys/toIndex.action";
						}else{
							alert(data.msg);
							window.location.reload();
						}
					},
					error: function(){
						//$(".tm_btn_primary").text('登录');
						alert('系统忙，请稍后再试');
						window.location.reload();
					}
				}); 

			}
		};
	</script>
</body></html>