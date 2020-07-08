<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../common/user_header.jsp"%>
<c:set var="wadewhy" value="${pageContext.request.contextPath}"/>
<style>
		h2{font-size:14px; margin:20px 0 10px 0;}
		.tm_param_list a{color:#000}
		.tm_param_list a:hover{color:#f00}

		.tm_blocker{float:left; width:50%;min-width:450px}
		.tm_blocker2{float:left; width:800px;}
</style>
<body>
	
    <div class="tm_main" style="min-width:1000px">
    	
        
        <div class="tm_container">
        	<div class="tm_navtitle">
            	<h1>欢迎使用</h1>
                <span>欢迎使用【wadewhy】在线考试系统</span>
            </div>
        </div>



			<script type="text/javascript">
				var tm = {
					startExam : function(e){
						if(!tmCheckBrowserSupport()){
							alert("抱歉，您的浏览器不被支持，如需继续使用，请更换为：Chrome、Firefox、360极速浏览器。");
							return false;
						}
						var tr = $("#"+e);
						var eid = tr.attr("data-key");
						var html = [];
						html.push('<div style="margin:20px">');
						html.push('	<p style="line-height:20px">确定进入试卷并开始考试吗？</p>');
						
						html.push('	<table style="margin:0 auto; width:350px" border="0" cellpadding="0" cellspacing="0">');
						html.push('	<tr>');
						html.push('		<td width="80"><img src="${pageContext.request.contextPath}/resources/home/images/paper_pencil.png" width="60" /></td>');
						html.push('		<td><p><b>考试名称</b>：'+tr.find("td").eq(0).text()+'<p>');
						html.push('			<p><b>考试时长</b>：'+tr.find("td").eq(2).text()+'<p>');
						html.push('			<p><b>卷面总分</b>：'+tr.find("td").eq(4).text()+'<p>');
						html.push('			<p><b>及格分数</b>：'+tr.find("td").eq(5).text()+'<p>');
						html.push('		</td>');
						html.push('	</tr>');
						html.push('</table>');

						html.push('<p style="text-align:center; margin-top:30px">');
						html.push('<button class="confir-exam tm_btn tm_btn_primary" type="button" onclick="tm.joinExam(\''+eid+'\')">确定</button>');
						html.push('</p>');

						html.push('</div>');

						layer.open({
						  type: 1,
						  title: '开始考试',
						  shadeClose: true,
						  shade: 0.8,
						  area: ['450px', '310px'],
						  content: html.join("")
						}); 

						return false;
					},
					joinExam : function(eid){
						$(".confir-exam").text('请稍等...');
						$(".confir-exam").attr("disabled", true);
						$.ajax({
							type: "POST",
							url: "${pageContext.request.contextPath}/before/exam/start_exam.action",
							dataType: "json",
							data: {"examId":eid},
							success: function(data){
								if(data.type == 'success'){
									top.window.location="${pageContext.request.contextPath}/before/exam/examing.action?examId="+eid;
								}else{
									alert(data.msg);
									//$(".tm_btn_primary").text('提交');
									//return;
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



			<div class="tm_container">
				<div class="tm_blocker2">
					<h2>进行中的考试</h2>
					<table width="100%" cellpadding="10" border="0" class="tm_table_list">
						<thead>
							<tr>
								<th>考试名称</th>
								<th>时间设定</th>
								<th>考试时长</th>
								<th>考试科目</th>
								<th>卷面总分</th>
								<th>及格分数</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							
								
									<c:if test="${empty examList}">
									<tr>
										<td colspan="7">没有进行中的考试</td>
									</tr>
									</c:if>
							<c:forEach items="${examList }" var = "exam">
								<tr id="tr-${exam.id }" data-key="${exam.id }">
										<td >${exam.name }</td>
										<td ><fmt:formatDate value="${exam.starttime }" pattern="yyyy-MM-dd HH:mm:ss"/>---<fmt:formatDate value="${exam.endtime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td >${exam.avaliabletime }分钟</td>
										<td >${subject.name }</td>
										<td >${exam.totalscore }</td>
										<td >${exam.passscore }</td>
										<td ><button class="tm_btn tm_btn_primary" onclick="tm.startExam('tr-${exam.id }');">开始考试</button></td>
								</tr>
							</c:forEach>	
							
						</tbody>
					</table>
				</div>
			</div>

			<div class="tm_container">
				<div class="tm_blocker2">
					<h2>参加过的考试</h2>
					<table width="100%" cellpadding="10" border="0" class="tm_table_list">
						<thead>
							<tr>
								<th>考试名称</th>
								<th>考试时长</th>
								<th>考试耗时</th>
								<th>考试时间</th>
								<th>试卷得分</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							
									<c:if test="${empty historyList}">
										<tr>
											<td colspan="6">你还没有考过试!</td>
									</tr>
									</c:if>
								
									<c:forEach items="${historyList }" var = "history">
									<tr>
										<td>${history.exam.name}</td>
										<td><span class="tm_label">${history.exam.avaliabletime}</span> 分钟</td>
										<td><span class="tm_label">${history.usetime}</span> 分钟</td>
										<td>
											<fmt:formatDate value="${history.startexamtime }" pattern="yyyy-MM-dd HH:mm:ss"/>
											<br/><fmt:formatDate value="${history.endexamtime }" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td>
											<span class="tm_label">${history.score}</span>
											<c:if test="${history.exam.passscore > history.score }">
											<font color="red"><b>不及格</b></font>
											</c:if>
										</td>
										<td>
													<a href="${pageContext.request.contextPath}/before/student/review_exam.action?examId=${history.exam.id }&examPaperId=${history.id}" target="_blank" class="tm_btn tm_btn_primary" style="text-decoration:none;color:white;" >回顾试卷</a>
										</td>
									</tr>
									</c:forEach>
									
									
								
							
						</tbody>
					</table>
				</div>
			</div>

			<div class="tm_container"></div>

    </div>
	
	<p>&nbsp;</p>

</body>
</html>