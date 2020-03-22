<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../common/user_header.jsp"%>
<c:set var="wadewhy" value="${pageContext.request.contextPath}"/>
  
<body>
	<div class="tm_main">
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1>修改密码</h1>
                <span>在下面的表单中修改您的登录密码</span>
            </div>
        </div>
        
        <br/>
        <div class="tm_container">
			<form method="post" id="form_user_form">
        	<table width="100%" cellpadding="5" border="0" class="tm_table_form">
            	<tbody>
                    <tr>
                        <th width="120">用户名 : </th>
                        <td>${student.name}</td>
                    </tr>
					<tr>
                        <th>旧密码 : </th>
                        <td>
							<input type="password" name="old_password" id="old_password" class="validate[required] tm_txt" size="50" maxlength="30" />
							<span class="tm_required">*</span>
						</td>
                    </tr>
					<tr>
                        <th>新密码 : </th>
                        <td>
							<input type="password" id="new_password" name="new_password" class="validate[required] tm_txt" size="50" maxlength="30" />
							<span class="tm_required">*</span>

							<div id="tm_level" class="pw-strength">
								<div class="pw-bar"></div>
								<div class="pw-bar-on"></div>
								<div class="pw-txt">
									<span>弱</span>
									<span>中</span>
									<span>强</span>
								</div>
							</div>

						</td>
                    </tr>
					<tr>
						<th>确认新密码 : </th>
						<td>
							<input type="password" class="validate[required,equals[new_password]] tm_txt" name="new_password2" size="50" maxlength="30" value="" />
							<span class="tm_required">*</span>
						</td>
					</tr>
                   
                </tbody>
                
                <tfoot>
                	<tr>
                    	<th></th>
                        <td>
                        	<button class="tm_btn tm_btn_primary" type="button" onclick="tmProfile.doUpdate();">提交</button>
                        </td>
                    </tr>
                </tfoot>
            </table>

			</form>
        </div>
        
        
    </div>
<script type="text/javascript">
		$(document).ready(function() { 
			tm_bindPasswordLevelChecker("new_password");
		});

		var tmProfile = {
			doUpdate : function(){
				var formcheck = $("#form_user_form").validationEngine('validate');
				if(formcheck){
					var wcm = window.confirm('确定要修改密码吗？');
					if(!wcm){
						return;
					}
					
					$.ajax({
						type: "POST",
						url: "${wadewhy}/before/student/update_password.action",
						dataType:'json',
						data:{password:$("#new_password").val(),oldPassword:$("#old_password").val()},
						success: function(data){
							if(data.type == 'success'){
                                top.location.href = "before/sys/toLogin.action"
							}else{
								alert(data.msg);
							}
						},
						error : function(){
							//top.location.href = "home/login";
							alert('网络错误');
						}
					});

				}else{
					return false;
				}
			}
		};
	</script>
</body>
</html>