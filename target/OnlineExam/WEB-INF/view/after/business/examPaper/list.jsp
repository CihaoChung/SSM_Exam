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
            <select id="search-exam" class="easyui-combobox" panelHeight="auto" style="width:120px">
            	<option value="-1">全部</option>
            	<c:forEach items="${examList}" var="exam">
	            	<option value="${exam.id}">${exam.name}</option>
            	</c:forEach>
            </select>
            <label>所属考生:</label>
            <select id="search-student" class="easyui-combobox" panelHeight="auto" style="width:120px">
            	<option value="-1">全部</option>
            	<c:forEach items="${studentList}" var="student">
	            	<option value="${student.id}">${student.name}</option>
            	</c:forEach>
            </select>
            <label>试卷状态:</label>
            <select id="search-status" class="easyui-combobox" panelHeight="auto" style="width:120px">
            	<option value="-1">全部</option>
            	<option value="0">未考</option>
            	<option value="1">已考</option>
            </select>
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>

<!-- 修改窗口 -->
<div id="edit-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
	<form id="edit-form" method="post">
        <input type="hidden" name="id" id="edit-id">
        <table>
            <tr>
                <td align="right">所属考试:</td>
                <td>
                	<select id="edit-examId" name="examid" class="easyui-combobox easyui-validatebox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择试卷科目'">
		                <c:forEach items="${examList}" var="exam">
			            	<option value="${exam.id}">${exam.name}</option>
		            	</c:forEach>
		            </select>
                </td>
            </tr>
            <tr>
                <td align="right">所属学生:</td>
                <td>
                	<select id="edit-studentId" name="studentid" class="easyui-combobox easyui-validatebox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择试卷科目'">
		                <c:forEach items="${studentList}" var="student">
			            	<option value="${student.id}">${student.name}</option>
		            	</c:forEach>
		            </select>
                </td>
            </tr>
        </table>
    </form>
</div>
<%@include file="../../common/footer.jsp"%>

<!-- End of easyui-dialog -->
<script type="text/javascript">
	
	
	
	
	function edit(){
		var validate = $("#edit-form").form("validate");
		if(!validate){
			$.messager.alert("消息提醒","请检查你输入的数据!","warning");
			return;
		}
		var data = $("#edit-form").serialize();
		$.ajax({
			url:'${pageContext.request.contextPath}/after/exampaper/edit.action',
			dataType:'json',
			type:'post',
			data:data,
			success:function(data){
				if(data.type == 'success'){
					$.messager.alert('信息提示','修改成功！','info');
					$('#edit-dialog').dialog('close');
					$('#data-datagrid').datagrid('reload');
				}else{
					$.messager.alert('信息提示',data.msg,'warning');
				}
			}
		});
	}
	
	/**
	* 删除记录
	*/
	function remove(){
		$.messager.confirm('信息提示','确定要删除该记录？', function(result){
			if(result){
				var item = $('#data-datagrid').datagrid('getSelected');
				if(item == null || item.length == 0){
					$.messager.alert('信息提示','请选择要删除的数据！','info');
					return;
				}
				$.ajax({
					url:'${pageContext.request.contextPath}/after/exampaper/delete.action',
					dataType:'json',
					type:'post',
					data:{id:item.id},
					success:function(data){
						if(data.type == 'success'){
							$.messager.alert('信息提示','删除成功！','info');
							$('#data-datagrid').datagrid('reload');
						}else{
							$.messager.alert('信息提示',data.msg,'warning');
						}
					}
				});
			}	
		});
	}
	
	/*
	编辑
	*/
	function openEdit(){
		//$('#add-form').form('clear');
		var item = $('#data-datagrid').datagrid('getSelected');
		if(item == null || item.length == 0){
			$.messager.alert('信息提示','请选择要编辑的数据！','info');
			return;
		}
		$('#edit-dialog').dialog({
			closed: false,
			modal:true,
            title: "编辑试卷信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler:edit
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#edit-dialog').dialog('close');                    
                }
            }],
            onBeforeOpen:function(){
            	//$("#add-form input").val('');
            	$("#edit-id").val(item.id);
            	$("#edit-examId").combobox('setValue',item.examid);
            	$("#edit-studentId").combobox('setValue',item.studentid);
            }
        });
	}
	
	
	
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
		var status = $("#search-status").combobox('getValue');
		if(status != -1){
			option.status = status;
		}
		$('#data-datagrid').datagrid('reload',option);
	});
	
	function add0(m){return m<10?'0'+m:m }
	function format(shijianchuo){
	//shijianchuo是整数，否则要parseInt转换
		var time = new Date(shijianchuo);
		var y = time.getFullYear();
		var m = time.getMonth()+1;
		var d = time.getDate();
		var h = time.getHours();
		var mm = time.getMinutes();
		var s = time.getSeconds();
		return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
	}
	
	/** 
	* 载入数据
	*/
	$('#data-datagrid').datagrid({
		url:'${pageContext.request.contextPath}/after/exampaper/list.action',
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
				for(var i=0;i<examList.length;i++){
					if(examList[i].value == value)return examList[i].text;
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
			{ field:'status',title:'试卷状态',width:200,formatter:function(value,index,row){
				if(value == 0) return '未考';
				return '已考';
			}},
			{ field:'startexamtime',title:'开始考试时间',width:200,formatter:function(value,index,row){
				if(value == '' || value == null)return '';
				return format(value);
			}},
			{ field:'endexamtime',title:'结束考试时间',width:200,formatter:function(value,index,row){
				if(value == '' || value == null)return '';
				return format(value);
			}},
			{ field:'usetime',title:'考试用时',width:200,formatter:function(value,index,row){
				return value + '分钟';
			}},
			{ field:'totalscore',title:'试卷总分',width:200},
			{ field:'score',title:'试卷得分',width:200},
			{ field:'createtime',title:'添加时间',width:200,formatter:function(value,index,row){
				return format(value);
			}},
		]]
	});
</script>