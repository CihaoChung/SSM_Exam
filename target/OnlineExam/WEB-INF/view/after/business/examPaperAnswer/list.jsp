<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../../common/header.jsp"%>
<div class="easyui-layout" data-options="fit:true">
    <!-- Begin of toolbar -->
    <div id="wu-toolbar">
        <div class="wu-toolbar-button">
            <%@include file="../../common/menus.jsp"%>
        </div>
        <div class="wu-toolbar-search">
            <label>所属考试:</label>
            <select id="search-exam" class="easyui-combobox" panelHeight="200px" style="width:120px">
            	<option value="-1">全部</option>
            	<c:forEach items="${examList}" var="exam">
	            	<option value="${exam.id}">${exam.name}</option>
            	</c:forEach>
            </select>
            <label>所属考生:</label>
            <select id="search-student" class="easyui-combobox" panelHeight="200px" style="width:120px">
            	<option value="-1">全部</option>
            	<c:forEach items="${studentList}" var="student">
	            	<option value="${student.id}">${student.name}</option>
            	</c:forEach>
            </select>
            <label>所属试题:</label>
            <select id="search-question" class="easyui-combobox" panelHeight="300px" style="width:120px">
            	<option value="-1">全部</option>
            	<c:forEach items="${questionList}" var="question">
	            	<option value="${question.id}">${question.title}</option>
            	</c:forEach>
            </select>
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>


<%@include file="../../common/footer.jsp"%>

<!-- End of easyui-dialog -->
<script type="text/javascript">

	//搜索按钮监听
	$("#search-btn").click(function(){
		var option = {};
		var examId = $("#search-exam").combobox('getValue');
		if(examId != -1){
			option.examId = examId;
		}
		var studentId = $("#search-student").combobox('getValue');
		if(studentId != -1){
			option.studentId = studentId;
		}
		var questionId = $("#search-question").combobox('getValue');
		if(questionId != -1){
			option.questionId = questionId;
		}
		$('#data-datagrid').datagrid('reload',option);
	});
	
	
	
	/** 
	* 载入数据
	*/
	$('#data-datagrid').datagrid({
		url:'${wadewhy}/after/examPaperAnswer/list.action',
		rownumbers:true,
		singleSelect:true,
		pageSize:20,           
		pagination:true,
		multiSort:true,
		fitColumns:true,
		idField:'id',
	    treeField:'name',
	    nowrap:false,
		fit:true,
		columns:[[
			{ field:'chk',checkbox:true},
			{ field:'examid',title:'所属考试',width:180,formatter:function(value,index,row){
				var examList = $("#search-exam").combobox("getData");
				console.log(examList);
				console.log(value);
				for(var i=0;i<examList.length;i++){
					if(examList[i].value == value)return examList[i].text;
				}
				return value;
			}},
			{ field:'exampaperid',title:'试卷ID',width:200},
			{ field:'questionid',title:'所属试题',width:200,formatter:function(value,index,row){
				var questionList = $("#search-question").combobox("getData");
				for(var i=0;i<questionList.length;i++){
					if(questionList[i].value == value)return questionList[i].text;
				}
				return value;
			}},
			{ field:'studentid',title:'所属考生',width:180,formatter:function(value,index,row){
				var studentList = $("#search-student").combobox("getData");
				for(var i=0;i<studentList.length;i++){
					if(studentList[i].value == value)return studentList[i].text;
				}
				return value;
			}},
			{ field:'answer',title:'提交答案',width:200},
			{ field:'iscorrect',title:'是否正确',width:200,formatter:function(value,index,row){
				if(value == 0) return '<font color=red>错误</font>';
				return '正确';
			}},
		]]
	});
</script>