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
            <label>考试列表:</label>
             <select id="search-exam" class="easyui-combobox" panelHeight="auto" style="width:120px">
            	<option value="-1">全部</option>
            	<c:forEach items="${examList}" var="exam">
	            	<option value="${exam.id}">${exam.name}</option>
            	</c:forEach>
             </select>
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
    
    <div class="easyui-accordion" style="width:950px;height:660px;">
		<div title="成绩统计图表展示" data-options="iconCls:'icon-ok'" style="overflow:auto;padding:10px;">
			 <div id="main" style="width: 880px;height:560px;"></div>	
		</div>
	</div>
    
    
</div>
<%@include file="../../common/footer.jsp"%>
<!-- End of easyui-dialog -->
<script src="${pageContext.request.contextPath}/resources/admin/easyui/js/echarts.min.js"></script>
<script type="text/javascript">
	
//基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));

// 指定图表的配置项和数据




$("#search-btn").click(function(){
	var examId = $("#search-exam").combobox('getValue');
	if(examId == -1){
		$.messager.alert('信息提示','请选择要统计的考试！','info');
		return;
	}
	$.ajax({
		type: "POST",
		url: "${pageContext.request.contextPath}/after/stats/get_stats.action",
		dataType: "json",
		data: {"examId":examId},
		success: function(data){
			if(data.type == 'success'){
				var option = {
						tooltip: {
					        trigger: 'axis',
					        axisPointer: {
					            type: 'cross',
					            crossStyle: {
					                color: '#999'
					            }
					        }
					    },
					    xAxis: {
					        type: 'category',
					        data: data.studentList
					    },
					    yAxis: {
					        type: 'value'
					    },
					    series: [{
					        data: data.studentScore,
					        type: 'line'
					    }]
					};
				// 使用刚指定的配置项和数据显示图表。
				myChart.setOption(option);
			}else{
				alert(data.msg);
			}
		},
		error: function(){
			//$(".tm_btn_primary").text('登录');
			alert('系统忙，请稍后再试');
			window.location.reload();
		}
	}); 
});
	
</script>