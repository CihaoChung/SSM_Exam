<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="wadewhy" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>${title }</title>
<link href="${wadewhy}/resources/home/exam/css/main.css" rel="stylesheet" type="text/css" />
<link href="${wadewhy}/resources/home/exam/css/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${wadewhy}/resources/home/exam/css/test.css" rel="stylesheet" type="text/css" />
<style>
.hasBeenAnswer {
	background: #5d9cec;
	color:#fff;
}
</style>

</head>
<body>
<div class="main">
	<!--nr start-->
	<div class="test_main">
		<div class="nr_left">
			<div class="test" id="options">
				<form action="" method="post">
					<div class="test_title">
						<p class="test_time">
							<img style="float:left;margin-top:15px;margin-left:10px;" src="${wadewhy}/resources/home/exam/images/time.png" width="16px"><b class="alt-1">${hour }:${minitute }:${second }</b>
						</p>
						<font><input type="button" onclick="submitExam()" name="test_jiaojuan" value="交卷"></font>
					</div>
						<c:if test="${exam.singlequestionnum !=0 }">
						<div class="test_content">
							<div class="test_content_title">
								<h2>单选题</h2>
								<p>
									<span>共</span><i class="content_lit">${exam.singlequestionnum }</i><span>题，</span><span>合计</span><i class="content_fs">${singleScore * exam.singlequestionnum}</i><span>分</span>
								</p>
							</div>
						</div>
						<div class="test_content_nr">
							<ul>
								<c:forEach items="${singleQuestionList}" var="sq" varStatus="sqids">
									<c:set var="index" value="${sqids.count}"/>
									<c:forEach items="${sq}" var="entry" varStatus="i" begin="${index-1}" end="${index-1}">

										<li id="qu_${singleQuestion }_${entry.key.question.id}" data-key="${entry.key.id}">
											<div class="test_content_nr_tt">
												<i>${sqids.index+1}</i><span>(${entry.key.question.score}分)</span><font>${entry.key.question.title }</font>
											</div>
											<div class="test_content_nr_main">
												<ul>
													<c:forEach items="${entry.value}" var="op" varStatus="i">
														<li class="option" data-type="single" data-value="${op.selectoption}">

															<input type="radio" class="radioOrCheck" name="answer${entry.key.id}"
																   id="${singleQuestion }_answer_${entry.key.id}_option_1"
															/>
															<%--<p>${singleQuestion }_answer_${entry.key.id}_option_1</p>--%>
															<label for="${singleQuestion }_answer_${entry.key.id}_option_1">
																	${op.selectoption}
																<p class="ue" style="display: inline;">${op.optionanswer }</p>
															</label>
														</li>
													</c:forEach>

												</ul>
											</div>
										</li>
									</c:forEach>

								</c:forEach>
							</ul>
						</div>
					</c:if>
						<c:if test="${exam.muiltquestionnum !=0 }">
						<div class="test_content">
							<div class="test_content_title">
								<h2>多选题</h2>
								<p>
									<span>共</span><i class="content_lit">${exam.muiltquestionnum }</i><span>题，</span><span>合计</span><i class="content_fs">${exam.muiltquestionnum * muiltScore}</i><span>分</span>
								</p>
							</div>
						</div>
						<div class="test_content_nr">
							<ul>
								<c:forEach items="${muiltQuestionList}" var="mq" varStatus="mqids">
								<c:set var="index" value="${mqids.count}"/>
									<c:forEach items="${mq}" var="entry" varStatus="i" begin="${index-1}" end="${index-1}">
										<li id="qu_${muiltQuestion }_${entry.key.question.id}" data-key="${entry.key.id}">
											<div class="test_content_nr_tt">
												<i>${mqids.index + exam.singlequestionnum + 1 }</i><span>(${entry.key.question.score}分)</span><font>${entry.key.question.title }</font>
											</div>

											<div class="test_content_nr_main">
												<ul>
													<c:forEach items="${entry.value}" var="op" varStatus="i">
														<li class="option" data-type="muilt" data-value="${op.selectoption}">


															<input type="checkbox" class="radioOrCheck" name="answer1"
																   id="${muiltQuestion }_answer_${entry.key.id}_option_1" value="${op.selectoption}"
															/>

															<label for="${muiltQuestion }_answer_${entry.key.id}_option_1">
																	${op.selectoption}
																<p class="ue" style="display: inline;">${op.optionanswer}</p>
															</label>
														</li>
													</c:forEach>

												</ul>
											</div>
										</li>
									</c:forEach>

									</c:forEach>
							</ul>
						</div>
					</c:if>
					
					<c:if test="${exam.chargequestionnum !=0 }">
						<div class="test_content">
							<div class="test_content_title">
								<h2>判断题</h2>
								<p>
									<span>共</span><i class="content_lit">${exam.chargequestionnum }</i><span>题，</span><span>合计</span><i class="content_fs">${chargeScore * exam.chargequestionnum}</i><span>分</span>
								</p>
							</div>
						</div>
						<div class="test_content_nr">
							<ul>
								<c:forEach items="${chargeQuestionList}" var="cq" varStatus="cqids">
								<c:set var="index" value="${cqids.count}"/>
									<c:forEach items="${cq}" var="entry" varStatus="i" begin="${index-1}" end="${index-1}">
										<li id="qu_${chargeQuestion }_${entry.key.question.id}" data-key="${entry.key.id}">
											<div class="test_content_nr_tt">
												<i>${cqids.index+exam.singlequestionnum+exam.muiltquestionnum+1}</i><span>(${entry.key.question.score}分)</span><font>${entry.key.question.title }</font>
											</div>

											<div class="test_content_nr_main">
												<ul>
													<c:forEach items="${entry.value}" var="op" varStatus="i">
														<li class="option" data-type="charge" data-value="${op.selectoption}">

															<input type="radio" class="radioOrCheck" name="answer${entry.key.id}"
																   id="${chargeQuestion }_answer_${entry.key.id}_option_1"
															/>
															<label for="${chargeQuestion }_answer_${entry.key.id}_option_1">
																	${op.selectoption}
																<p class="ue" style="display: inline;">${op.optionanswer}</p>
															</label>
														</li>
													</c:forEach>
												</ul>
											</div>
										</li>
									</c:forEach>

								</c:forEach>
							</ul>
						</div>
					</c:if>
					<c:if test="${exam.writequestionnum !=0 }">
						<div class="test_content">
							<div class="test_content_title">
								<h2>简答题</h2>
								<p>
									<span>共</span><i class="content_lit">${exam.writequestionnum }</i><span>题，</span><span>合计</span><i class="content_fs">${writerScore * exam.writequestionnum}</i><span>分</span>
								</p>
							</div>
						</div>
						<div class="test_content_nr">
							<ul>
								<c:forEach items="${writerQuestionList}" var="wq" varStatus="wqids">
								<c:set var="index" value="${wqids.count}"/>
									<c:forEach items="${wq}" var="entry" varStatus="i" begin="${index-1}" end="${index-1}">
										<li id="qu_${writerQuestion }_${entry.key.question.id}" data-key="${entry.key.id}">
											<div class="test_content_nr_tt">
												<i>${cqids.index+exam.singlequestionnum+exam.muiltquestionnum+exam.writequestionnum+1}</i><span>(${entry.key.question.score}分)</span><font>${entry.key.question.title }</font>
											</div>
											<div class="test_content_nr_main" id="writerQui">
												<ul>
													<input type="text" name="${i.index}" value="${entry.key.question.id}" hidden="hidden">
													<li class="option" data-type="write" data-value="">
														<textarea id="${i.index}" name="condition" class="easyui-validatebox" data-options="required:true" style="width: 300px; height: 100px;"></textarea>
													</li>
												</ul>
											</div>
										</li>
									</c:forEach>

								</c:forEach>
							</ul>
						</div>
					</c:if>
					
				</form>
			</div>

		</div>
		<div class="nr_right">
			<div class="nr_rt_main">
				<div class="rt_nr1">
					<div class="rt_nr1_title">
						<h1>
							<span style="font-size:18px;">答题卡</span>
						</h1>
						<p class="test_time">
							<img style="float:left;margin-top:15px;margin-left:10px;" src="${wadewhy}/resources/home/exam/images/time.png" width="16px">
							<b class="alt-1">${hour }:${minitute }:${second }</b>
						</p>
					</div>
					<c:if test="${exam.singlequestionnum !=0 }">
						<div class="rt_content">
							<div class="rt_content_tt">
								<h2>单选题</h2>
								<p>
									<span>共</span><i class="content_lit">${exam.singlequestionnum }</i><span>题</span>
								</p>
							</div>
							<div class="rt_content_nr answerSheet">
								<ul>
									<c:forEach items="${singleQuestionList}" var="ssq" varStatus="ssqids">
										<c:set var="index" value="${ssqids.count}"/>
										<%--${ssqids.index  }--%>
										<c:forEach items="${ssq}" var="entry" varStatus="i"  begin="${index-1}" end="${index-1}">
										<li><a href="#qu_${singleQuestion}_${entry.key.question.id}">${ssqids.index + 1 }</a></li>
										</c:forEach>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>
						<c:if test="${exam.muiltquestionnum !=0 }">
						<div class="rt_content">
							<div class="rt_content_tt">
								<h2>多选题</h2>
								<p>
									<span>共</span><i class="content_lit">${exam.muiltquestionnum }</i><span>题</span>
								</p>
							</div>
							<div class="rt_content_nr answerSheet">
								<ul>
									<c:forEach items="${muiltQuestionList}" var="mmq" varStatus="mmqids">
										<c:set var="index" value="${mmqids.count}"/>
										<c:forEach items="${mmq}" var="entry" varStatus="i" begin="${index-1}" end="${index-1}">
										<li><a href="#qu_${muiltQuestion }_${entry.key.question.id }">${mmqids.index + exam.singlequestionnum + 1 }</a></li>
										</c:forEach>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>
					<c:if test="${exam.chargequestionnum !=0 }">
						<div class="rt_content">
							<div class="rt_content_tt">
								<h2>判断题</h2>
								<p>
									<span>共</span><i class="content_lit">${exam.chargequestionnum }</i><span>题</span>
								</p>
							</div>
							<div class="rt_content_nr answerSheet">
								<ul>
									<c:forEach items="${chargeQuestionList}" var="ccq" varStatus="ccqids">
										<c:set var="index" value="${ccqids.count}"/>
										<c:forEach items="${ccq}" var="entry" varStatus="i" begin="${index-1}" end="${index-1}">
										<li><a href="#qu_${chargeQuestion}_${entry.key.question.id}">${ccqids.index+exam.singlequestionnum+exam.muiltquestionnum+1}</a></li>
										</c:forEach>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>
					<c:if test="${exam.writequestionnum !=0 }">
						<div class="rt_content">
							<div class="rt_content_tt">
								<h2>问答题</h2>
								<p>
									<span>共</span><i class="content_lit">${exam.writequestionnum }</i><span>题</span>
								</p>
							</div>
							<div class="rt_content_nr answerSheet">
								<ul>
									<c:forEach items="${writerQuestionList}" var="wqq" varStatus="wqqids">
										<c:set var="index" value="${wqqids.count}"/>
										<c:forEach items="${wqq}" var="entry" varStatus="i" begin="${index-1}" end="${index-1}">
											<li><a href="#qu_${writerQuestion}_${entry.key.question.id}">${wqqids.index+exam.singlequestionnum+exam.muiltquestionnum+exam.chargequestionnum+1}</a></li>
										</c:forEach>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>
				</div>

			</div>
		</div>
	</div>
	<!--nr end-->
	<div class="foot"></div>
</div>

<script src="${wadewhy}/resources/home/exam/js/jquery-1.11.3.min.js"></script>
<script src="${wadewhy}/resources/home/exam/js/jquery.easy-pie-chart.js"></script>
<!--时间js-->
<script src="${wadewhy}/resources/home/exam/js/jquery.countdown.js"></script>
<script>
	window.jQuery(function($) {
		"use strict";
		
		$('time').countDown({
			with_separators : false
		});
		$('.alt-1').countDown({
			css_class : 'countdown-alt-1'
		});
		$('.alt-2').countDown({
			css_class : 'countdown-alt-2'
		});
		$('.alt-3').countDown({
			css_class : 'countdown-alt-3'
		});
	});

	/**
	 * 自动提交选择
	 */
	$(function() {
		$('li.option input').click(function() {
			var examId = $(this).closest('.test_content_nr_main').closest('li').attr('id'); // 得到题目ID
			var cardLi = $('a[href=#' + examId + ']'); // 根据题目ID找到对应答题卡
			// 设置已答题
			if(!cardLi.hasClass('hasBeenAnswer')){
				cardLi.addClass('hasBeenAnswer');
			}
			var examArr = examId.split('_');
			var questionId = examArr[2];
			var submitExamId = ${exam.id};
			var examPaperId = ${examPaper.id};
			var examPaperAnswerId = $("#"+examId).attr("data-key");
			//console.log(questionId+'--'+submitExamId+'--'+examPaperId);
			var selectedAnswer = $(this).closest("li.option").attr("data-value");
			if($(this).closest("li.option").attr("data-type") == 'muilt'){
				//如果是多选
				selectedAnswer = '';
				var checkedBox = $(this).closest("li.option").parent("ul").find("input[type='checkbox']:checked");
				for(var i=0;i<checkedBox.length;i++){
					selectedAnswer += $(checkedBox[i]).val();
				}
				//console.log(checkedBox)
			}
			//console.log(selectedAnswer)

			// console.log(selectedAnswer);
			//提交答案
			$.ajax({
				type: "POST",
				url: "${wadewhy}/before/exam/submit_answer.action",
				dataType: "json",
				data: {"examid":submitExamId,"exampaperid":examPaperId,"questionid":questionId,"answer":selectedAnswer,"id":examPaperAnswerId},
				success: function(data){
					if(data.type == 'success'){
						//top.window.location="../exam/examing?examId="+eid;
					}else{
						alert(data.msg);
						//$(".tm_btn_primary").text('提交');
						//return;
						//window.location.reload();
					}
				},
				error: function(){
					//$(".tm_btn_primary").text('登录');
					alert('提交');
					window.location.reload();
				}
			});
		});
	});
var autoSubmit = false;
//当考试时间结束后自动提交试卷
function autoSubmitExam(){
	if(autoSubmit)return;
	var dataOption=[];
	for (var i = 0; i < ${exam.writequestionnum }; i++) {
		var writerQuestions = new Object();
		var num = "#"+i;
		writerQuestions.questionid = $("input[name="+i+"]").val();
		writerQuestions.answer = $(num).val();
		dataOption.push(writerQuestions);
	}
	var  json_str = JSON.stringify(dataOption);
	console.log(json_str);
	//提交试卷
	$.ajax({
		type: "POST",
		url: "${wadewhy}/before/exam/submit_exam.action",
		dataType: "json",
		data: {"examId":'${exam.id}',"examPaperId":'${examPaper.id}',"questionString":json_str},
		success: function(data){
			if(data.type == 'success'){
				alert('考试时间到，考试成绩已出，请到会员中心查看');
				window.location="${wadewhy}/before/sys/index.action";
			}else{
				alert(data.msg);
				//$(".tm_btn_primary").text('提交');
				//return;
				//window.location.reload();
			}
		},
		beforeSend:function(){
			autoSubmit = true;
		},
		error: function(){
			//$(".tm_btn_primary").text('登录');
			alert('系统忙，请稍后再试');
			window.location.reload();
		}
	});	
}
function submitExam(){
	var wcm = window.confirm('提交后将不能再进行考试，确认提交？');
	if(!wcm){
		return;
	}
	var dataOption=[];
	for (var i = 0; i < ${exam.writequestionnum }; i++) {
		var writerQuestions = new Object();
		var num = "#"+i;
		writerQuestions.questionid = $("input[name="+i+"]").val();
		writerQuestions.answer = $(num).val();
		dataOption.push(writerQuestions);
	}
	var  json_str = JSON.stringify(dataOption);
	console.log(json_str);
	//提交试卷
	$.ajax({
		type: "POST",
		url: "${wadewhy}/before/exam/submit_exam.action",
		dataType: "json",
		data: {"examId":'${exam.id}',"examPaperId":'${examPaper.id}',"questionString":json_str},
		success: function(data){
			if(data.type == 'success'){
				alert('考试成绩已出，请到会员中心查看');
				window.location="../user/index";
			}else{
				alert(data.msg);
				//$(".tm_btn_primary").text('提交');
				//return;
				//window.location.reload();
			}
		},
		error: function(){
			//$(".tm_btn_primary").text('登录');
			alert('系统忙，请稍后再试');
			window.location.reload();
		}
	});
}
</script>

<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
<p>【wadewhy】在线考试系统</p>
<p><a href="http://wadewhy.xyz" target="_blank">【wadewhy】</a></p>
</div>
</body>
</html>