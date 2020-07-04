<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../../common/header.jsp" %>
<div class="easyui-layout" data-options="fit:true">
    <!-- Begin of toolbar -->
    <div id="wu-toolbar">
        <div class="wu-toolbar-button">
            <%@include file="../../common/menus.jsp" %>
        </div>
        <div class="wu-toolbar-search">
            <label>试题题目:</label><input id="search-title" class="wu-text" style="width:100px">
            <label>试题类型:</label>
            <select id="search-question-type" class="easyui-combobox" panelHeight="auto" style="width:120px">
                <option value="-1">全部</option>
                <option value="0">单选</option>
                <option value="1">多选</option>
                <option value="2">判断</option>
            </select>
            <label>试题科目:</label>
            <input id="search-subject" name="subjectid" class="easyui-combobox easyui-validatebox"
                   panelHeight="auto">
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>

    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<%--询问添加的题型--%>
<div id="questionType-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'"
     style="width:420px; padding:10px;">
    <tr>
        <td align="right">想要添加的题型:</td>
        <td>
            <select id="CheckquestionType"  panelHeight="auto"
                    style="width:268px" data-options="required:true, missingMessage:'请选择试题类型'">
                <option value="0" >单选题</option>
                <option value="1">多选题</option>
                <option value="2">判断题</option>
                <option value="3">问答题</option>
            </select>
        </td>
    </tr>
</div>
<!-- Begin of easyui-dialog -->
<div id="add-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'"
     style="width:420px; padding:10px;">
    <form id="add-form" method="post">
        <input type="text" id="add-option" name="options" class="wu-text easyui-validatebox" hidden="hidden">
        <table>
            <tr>
                <td align="right">试题题目:</td>
                <td><input type="text" id="add-title" name="title" class="wu-text easyui-validatebox"
                           data-options="required:true, missingMessage:'请填写试题题目'"></td>
            </tr>
            <tr hidden="hidden">
                <td align="right">试题类型:</td>
                <td><input type="text" id="add-type" name="type" class="wu-text easyui-validatebox" readonly="readonly" ></td>
            </tr>
            <tr>
                <td align="right">所属科目:</td>
                <td>
                       <input id="add-subjectId" name="subjectid" class="easyui-combobox easyui-validatebox"
                              panelHeight="auto" style="width:268px"
                              data-options="required:true,missingMessage:'请选择考试科目'">
                </td>
            </tr>
        </table>
        <table id="ex">
            <tr >
                <td align="right">试题选项A:</td>
                <td><input type="text" id="A" name="A" class="wu-text easyui-validatebox"
                           data-options="required:true, missingMessage:'请填写选项A'"></td>
            </tr>
            <tr >
                <td align="right">试题选项B:</td>
                <td><input type="text" id="B" name="B" class="wu-text easyui-validatebox"
                           data-options="required:true, missingMessage:'请填写选项B'"></td>
            </tr>
        </table>
        <table>
            <tr>
                <td align="right">正确答案:</td>
                <td>
                    <input type="text" id="add-answer" name="answer" class="wu-text easyui-validatebox"
                           data-options="required:true, missingMessage:'请填写正确答案'"></td>
            </tr>
        </table>

    </form>
</div>
<!-- 修改窗口 -->
<div id="edit-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'"
     style="width:450px; padding:10px;">
    <form id="edit-form" method="post">
        <input type="hidden" name="id" id="edit-id">
        <input type="text" id="edit-option" name="options" class="wu-text easyui-validatebox" hidden="hidden">
        <table>
            <tr>
                <td align="right">试题题目:</td>
                <td><input type="text" id="edit-title" name="title" class="wu-text easyui-validatebox"
                           data-options="required:true, missingMessage:'请填写试题题目'"></td>
            </tr>
            <tr>
                <td align="right">所属科目:</td>
                <td>
                    <input id="edit-subjectId" name="subjectid" class="easyui-combobox easyui-validatebox"
                           panelHeight="auto" style="width:268px"
                           data-options="required:true,missingMessage:'请选择考试科目'">
                </td>
            </tr>
            <tr>
                <td align="right">所属类型:</td>
                <td>
                    <select id="edit-questionType" name="type" class="easyui-combobox easyui-validatebox"
                            panelHeight="auto" style="width:268px"
                            data-options="required:true, missingMessage:'请选择试题类型'">
                        <option value="0">单选</option>
                        <option value="1">多选</option>
                        <option value="2">判断</option>
                        <option value="3">简答题</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">正确答案:</td>
                <td><input type="text" id="edit-answer" name="answer" class="wu-text easyui-validatebox"
                           data-options="required:true, missingMessage:'请填写正确答案'"></td>
            </tr>
        </table>
    </form>
</div>
<!-- 导入文件窗口 -->
<div id="import-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'"
     style="width:500px; padding:10px;">
    <table>
        <tr>
            <td align="right">选择文件:</td>
            <td><input type="text" id="import-filename" name="filename" class="wu-text easyui-validatebox"
                       readonly="readonly" data-options="required:true, missingMessage:'请选择文件'"></td>
            <td><a onclick="uploadFile()" href="javascript:void(0)" id="select-file-btn" class="easyui-linkbutton"
                   iconCls="icon-upload">选择文件</a></td>
        </tr>
        <tr>
            <td align="right">所属科目:</td>
            <td>
                <input id="import-subjectId" name="subjectid" class="easyui-combobox easyui-validatebox"
                       panelHeight="auto" style="width:268px"
                       data-options="required:true,missingMessage:'请选择考试科目'">
            </td>
            <td></td>
        </tr>
    </table>
</div>
<div id="process-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-upload',title:'正在上传文件'"
     style="width:450px; padding:10px;">
    <div id="p" class="easyui-progressbar" style="width:400px;" data-options="text:'正在上传中...'"></div>
</div>
<input type="file" id="excel-file" style="display:none;" onchange="selected()">
<!-- 加载题目详情的弹窗 -->
<div id="Showquestion-dialog" style="width:320px;height:450px; padding:10px;">
    <p id="questionTile" style="color: #9e81ff; font-weight: bolder; font-size: large" ></p>
    <p id="questionScore" style="color: #ff57a7; font-weight: bolder; font-size: large" ></p>
    <table id="question-datagrid"></table>
    <p id="questionans" style="color:red; font-weight: bolder;font-size: large"></p>
</div>
<%@include file="../../common/footer.jsp" %>

<!-- End of easyui-dialog -->
<script type="text/javascript">

    //上传图片
    function start() {
        var value = $('#p').progressbar('getValue');
        if (value < 100) {
            value += Math.floor(Math.random() * 10);
            $('#p').progressbar('setValue', value);
        } else {
            $('#p').progressbar('setValue', 0)
        }
    };

    function selected() {
        $("#import-filename").val($("#excel-file").val());
    }

    function upload() {
        if ($("#excel-file").val() == '') return;
        var formData = new FormData();
        formData.append('excelFile', document.getElementById('excel-file').files[0]);
        // alert($("#import-subjectId").val());
        formData.append('subjectid', $("#import-subjectId").val());
        $("#process-dialog").dialog('open');
        console.log(formData);
        var interval = setInterval(start, 200);
        $.ajax({
            url: '${wadewhy}/after/question/upload_file.action',
            type: 'post',
            data: formData,
            contentType: false,
            processData: false,
            success: function (data) {
                clearInterval(interval);
                $("#process-dialog").dialog('close');
                if (data.type == 'success') {
                    $('#import-dialog').dialog('close');
                    $('#data-datagrid').datagrid('reload');
                    $.messager.alert("消息提醒", data.msg, "info");
                } else {
                    $.messager.alert("消息提醒", data.msg, "warning");
                }
            },
            error: function (data) {
                clearInterval(interval);
                $("#process-dialog").dialog('close');
                $.messager.alert("消息提醒", "上传失败!", "warning");
            }
        });
    }

    function uploadFile() {
        $("#excel-file").click();
    }
    /**
     *  异步查询科目信息
     */
    $('#search-subject').combobox({
        url:"${wadewhy}/after/question/findSubject.action",
        valueField:'id',
        textField:'name',
        panelheight: "200"
    });
    $('#search-subject').combobox({
        onSelect:function (record) {
            $('#search-subject').attr("value",record.id);
            // alert($("#add-subjectId").val());
        }
    });
    $('#edit-subjectId').combobox({
        url:"${wadewhy}/after/question/findSubject.action",
        valueField:'id',
        textField:'name',
        panelheight: "200"
    });
    $('#edit-subjectId').combobox({
        onSelect:function (record) {
            $('#edit-subjectId').attr("value",record.id);
            // alert($("#add-subjectId").val());
        }
    });
    $('#add-subjectId').combobox({
            url:"${wadewhy}/after/question/findSubject.action",
            valueField:'id',
            textField:'name',
            panelheight: "200"
        });
    $('#add-subjectId').combobox({
            onSelect:function (record) {
               $('#add-subjectId').attr("value",record.id);
            }
        });
    $('#import-subjectId').combobox({
        url:"${wadewhy}/after/question/findSubject.action",
        valueField:'id',
        textField:'name',
        panelheight: "200"
    });
    $('#import-subjectId').combobox({
        onSelect:function (record) {
            $('#import-subjectId').attr("value",record.id);
            // alert($("#import-subjectId").val());
        }
    });


     /**
     *  添加记录
     */
    function add(type) {
        if (type != "简答题") {
            var dataOption = [];
            //拼接option
            var txt = $('#ex').find(':text');
            for (var i = 0; i < txt.length; i++) {
                var optionList = new Object();
                //获取input的name值
                optionList.option = txt.eq(i).attr("name");
                //获取input的value值
                optionList.ans = txt.eq(i).val();
                dataOption.push(optionList);
            }
            //json化数据
            var json_str = JSON.stringify(dataOption);
            $("#add-option").val(json_str);
        }
        var validate = $("#add-form").form("validate");
        if (!validate) {
            $.messager.alert("消息提醒", "请检查你输入的数据!", "warning");
            return;
        }
        var data = $("#add-form").serialize();
        $.ajax({
            url: '${wadewhy}/after/question/add.action',
            dataType: 'json',
            type: 'post',
            data: data,
            success: function (data) {
                if (data.code == 200) {
                    $.messager.alert('信息提示', '添加成功！', 'info');
                    //清除表单
                    $('#add-form').form('clear');
                    $('#add-dialog').dialog('close');
                    $('#data-datagrid').datagrid('reload');
                } else {
                    //清除表单
                    $('#add-form').form('clear');
                    $.messager.alert('信息提示', data.msg, 'warning');
                }
            }
        });
    }

    var CheckQueType = null;
    /**
     * Name 打开添加窗口
     */
    function openAdd() {
        $('#questionType-dialog').dialog({
            closed: false,
            modal: true,
            title: "选中题目类型",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    //获得选中的题型
                    CheckQueType = $("#CheckquestionType option:selected").val();
                    $('#questionType-dialog').dialog('close');
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#questionType-dialog').dialog('close');
                }
            }],
            onClose: function () {
                //给type赋值
                $('#add-type').attr("value",CheckQueType);
                CheckType();
            }
        });
    }

    //弹出添加层，按不同的题目类型
    function CheckType() {
        //清除表格的option
        var long = document.getElementById("ex").rows.length;
        while (long>2){
            //如果个数大于3删除最后一个
            $('#ex tr:last' ).remove();
            long--;
        }
        var num =0;
        var type = null;
        if (CheckQueType==2){//判断题
            type = "非问答题";
            $("#ex").show();
            $('#add-dialog').dialog({
                closed: false,
                modal: true,
                resizable:true,
                title: "添加判断题",
                buttons: [{
                    text: '确定',
                    iconCls: 'icon-ok',
                    handler:function () {
                        add(type)
                    }
                }, {
                    text: '取消',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        $('#add-dialog').dialog('close');
                    }
                }],
                toolbar: [{
                    text:'添加选项',
                    iconCls:'icon-add',
                    handler:function(){
                        var long = document.getElementById("ex").rows.length;
                        //获取table中tr的个数，我的想保留2行，当大于2时可以删除，小于6时不能删除
                        if (long <3) {
                            num++;
                            var addname ='';
                            var op=String.fromCodePoint(66+num);
                            addname += '<tr>';
                            addname +='<td align="right">'+'试题选项'+op+':</td>';
                            addname+='<td><input type="text" id="attrA" name="attrA" class="wu-text easyui-validatebox" data-options="missingMessage:"对或错""></td>';
                            addname+=' </tr>';
                            $("#ex").append(addname);
                            //给id和name赋值
                            $("#attrA").attr("name",op);
                            $("#attrA").attr("id",op);

                        }else{
                            alert("不能再添加了！");
                        }
                    }
                },'-',{
                    text:'删除最后项',
                    iconCls:'icon-cross',
                    handler:function(){
                        var long = document.getElementById("ex").rows.length;
                        //获取table中tr的个数，我的想保留2行，当大于2时可以删除，小于6时不能删除
                        if (long > 2) {
                            //判断一下table中tr的个数
                            var nodes = document.getElementById("ex").childNodes[0].childNodes;
                            document.getElementById("ex").deleteRow(nodes.length - 1);
                            num--;
                            //删除table中的最后一个tr
                        } else {
                            alert("不能再删除了！")
                        }
                    }
                }],
                //打开窗口之前
                onBeforeOpen: function () {

                }
            });
        }
            if (CheckQueType == 0||CheckQueType==1) {//如果是选择题,判断
                $("#ex").show();
                type = "非问答题";
                $('#add-dialog').dialog({
                    closed: false,
                    modal: true,
                    resizable:true,
                    title: "添加选择题",
                    buttons: [{
                        text: '确定',
                        iconCls: 'icon-ok',
                        handler:function () {
                            add(type)
                        }
                    }, {
                        text: '取消',
                        iconCls: 'icon-cancel',
                        handler: function () {
                            $('#add-dialog').dialog('close');
                        }
                    }],
                    toolbar: [{
                        text:'添加选项',
                        iconCls:'icon-add',
                        handler:function(){
                            num++;
                            var addname ='';
                            var op=String.fromCodePoint(66+num);
                            addname += '<tr>';
                            addname +='<td align="right">'+'试题选项'+op+':</td>';
                            addname+='<td><input type="text" id="attrA" name="attrA" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:\'请填写选项A\'"></td>';
                            addname+=' </tr>';
                            $("#ex").append(addname);
                            //给id和name赋值
                            $("#attrA").attr("name",op);
                            $("#attrA").attr("id",op);
                        }
                    },'-',{
                        text:'删除最后项',
                        iconCls:'icon-cross',
                        handler:function(){
                            var long = document.getElementById("ex").rows.length;
                            //获取table中tr的个数，我的想保留2行，当大于2时可以删除，小于6时不能删除
                            if (long > 2) {
                                //判断一下table中tr的个数
                                var nodes = document.getElementById("ex").childNodes[0].childNodes;
                                document.getElementById("ex").deleteRow(nodes.length - 1);
                                num--;
                                //删除table中的最后一个tr
                            } else {
                                alert("不能再删除了！")
                            }
                        }
                    }],
                    //关闭窗口后
                    onClose: function () {

                    }
                });
            }
            if (CheckQueType == 3) {//如果是问答题
                type = "简答题";
                //隐藏
                $("#ex").hide();
                $('#add-dialog').dialog({
                    closed: false,
                    modal: true,
                    title: "添加试题信息",
                    buttons: [{
                        text: '确定',
                        iconCls: 'icon-ok',
                        handler: function (){
                            //避免没有值而拦截
                            $("#A").attr("value","随便值");
                            $("#B").attr("value","随便值");
                            add(type)
                        }
                    }, {
                        text: '取消',
                        iconCls: 'icon-cancel',
                        handler: function () {
                            $('#add-dialog').dialog('close');
                        }
                    }],
                    onBeforeOpen: function () {
                        // CheckQueType=null;
                    }
                });
            }
    }

    function edit(id,subjectid) {
        if ($("#edit-subjectId").val()==''){
            $("#edit-subjectId").attr("value",subjectid);
        }
        var validate = $("#edit-form").form("validate");
        if (!validate) {
            $.messager.alert("消息提醒", "请检查你输入的数据!", "warning");
            return;
        }
        var data = $("#edit-form").serialize();
        $.ajax({
            url: '${wadewhy}/after/question/edit.action',
            dataType: 'json',
            type: 'post',
            data: data,
            success: function (data) {
                if (data.code == 200) {
                    $.messager.confirm('消息提示', '修改完成', function(r) {
                        $("#edit-name").val('');
                        $("#edit-remark").val('');
                        document.getElementById("edit-form").reset();
                        $('#edit-dialog').dialog('close');
                        $('#data-datagrid').datagrid('reload');
                    });
                    selectQuestion(id);
                } else {
                    $.messager.alert('信息提示', data.msg, 'warning');
                }
            }
        });
    }

    /**
     * 删除记录
     */
    function remove() {
        $.messager.confirm('信息提示', '确定要删除该记录？', function (result) {
            if (result) {
                var item = $('#data-datagrid').datagrid('getSelected');
                if (item == null || item.length == 0) {
                    $.messager.alert('信息提示', '请选择要删除的数据！', 'info');
                    return;
                }
                $.ajax({
                    url: '${wadewhy}/after/question/delete.action',
                    dataType: 'json',
                    type: 'post',
                    data: {id: item.id},
                    success: function (data) {
                        if (data.code ==200) {
                            $.messager.alert('信息提示', '删除成功！', 'info');
                            $('#data-datagrid').datagrid('reload');
                        } else {
                            $.messager.alert('信息提示', data.msg, 'warning');
                        }
                    }
                });
            }
        });
    }

    /*
    编辑
    */
    function openEdit() {
        //$('#add-form').form('clear');
        var item = $('#data-datagrid').datagrid('getSelected');
        if (item == null || item.length == 0) {
            $.messager.alert('信息提示', '请选择要编辑的数据！', 'info');
            return;
        }
        $('#edit-dialog').dialog({
            closed: false,
            modal: true,
            title: "编辑试题信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    edit(item.id,item.subjectid);
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#edit-dialog').dialog('close');
                }
            }],
            onBeforeOpen: function () {
                //$("#add-form input").val('');
                $("#edit-id").val(item.id);
                $("#edit-title").val(item.title);
                $("#edit-answer").val(item.answer);
                $("#edit-questionType").combobox('setValue', item.type);
                $('#edit-subjectId').combobox("setValue", item.subjectid);
            }
        });
    }

    function openImport() {
        //$('#add-form').form('clear');
        $('#import-dialog').dialog({
            closed: false,
            modal: true,
            title: "导入考试信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: upload
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#import-dialog').dialog('close');
                }
            }],
            onBeforeOpen: function () {
                //$("#add-form input").val('');
            }
        });
    }


    //搜索按钮监听
    $("#search-btn").click(function () {
        var option = {title: $("#search-title").val()};
        var questionType = $("#search-question-type").combobox('getValue');
        var subjectid = $("#search-subject").val();
        if (questionType != -1) {
            option.questionType = questionType;
        }
        if (subjectid != -1) {
            option.subjectid = subjectid;
        }
        $('#data-datagrid').datagrid('reload', option);
    });

    function add0(m) {
        return m < 10 ? '0' + m : m
    }

    function format(shijianchuo) {
        //shijianchuo是整数，否则要parseInt转换
        var time = new Date(shijianchuo);
        var y = time.getFullYear();
        var m = time.getMonth() + 1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
    }

    //打开题目详情
    function selectQuestion(questionId) {
        //清除表格的option
        var long = document.getElementById("question-datagrid").rows.length;
        while (long>0){
            //如果个数大于3删除最后一个
            $('#question-datagrid tr:last' ).remove();
            long--;
        }
        $('#Showquestion-dialog').dialog({
            closed: false,
            //不显示右上边的关闭
            closable: true,
            modal: true,
            resizable: true,
            title: "查看修改选项",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    //更新选项
                    var dataOption = [];
                    //拼接option
                    var txt = $('#question-datagrid').find(':text');
                    for (var i = 0; i < txt.length; i++) {
                        var optionList = new Object();
                        //获取input的name值
                        optionList.option = txt.eq(i).attr("id");
                        //获取input的value值
                        optionList.ans = txt.eq(i).val();
                        dataOption.push(optionList);
                    }
                    //json化数据
                    var json_str = JSON.stringify(dataOption);
                    $("#edit-option").val(json_str);
                    var item = $('#data-datagrid').datagrid('getSelected');
                    $("#edit-title").val(item.title);
                    $("#edit-answer").val(item.answer);
                    var data = $("#edit-form").serialize();
                    //修改题目
                    $.ajax({
                        url: '${wadewhy}/after/question/edit.action?questionId='+questionId,
                        dataType: 'json',
                        type: 'post',
                        data: data,
                        success: function (data) {
                            if (data.code == 200) {
                                $.messager.alert('信息提示', '如若你修改了选项，确保选项和答案对应', 'info');
                                //清除表格
                                var long = document.getElementById("question-datagrid").rows.length;
                                while (long>0){
                                    //如果个数大于3删除最后一个
                                    $('#question-datagrid tr:last' ).remove();
                                    long--;
                                }
                                $('#Showquestion-dialog').dialog('close');
                                $('#data-datagrid').datagrid('reload');
                            } else {
                                //清除表单
                                $.messager.alert('信息提示', data.msg, 'warning');
                            }
                        }
                    });
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#Showquestion-dialog').dialog('close');
                }
            }
            ],
            //打开前
            onBeforeOpen: function (data) {
                        $.ajax({
                            url: '${wadewhy}/after/question/findQuestionById.action?questionId=' + questionId,
                            type: "post",
                            dataType: 'json',
                            async: false,
                            success: function (data) {
                                var question_optionList= data.question_optionList;
                                var question = data.question;
                                $("#questionTile").html("题目："+question.title);
                                $("#questionScore").html("分值："+question.score);
                                for(var i=0;i<question_optionList.length;i++){
                                    var addname='';
                                    addname += '<tr>';
                                    addname +='<td align="right">'+question_optionList[i].selectoption+':'+'</td>';
                                    addname+='<td><input type="text" id="attrA" name="attrA" class="wu-text easyui-validatebox"  style="font-weight: bolder "></td><br>';
                                    addname+=' </tr>';
                                    $("#question-datagrid").append(addname);
                                    //给id和name赋值
                                    $("#attrA").attr("value",question_optionList[i].optionanswer);
                                    $("#attrA").attr("id",question_optionList[i].selectoption);
                                }
                                $("#questionans").html("您修改的答案为："+question.answer+"确保和选项能对应");
                            }
                        });
                    },
            //关闭前
            onBeforeClose:function () {
                //清除表格的option
                var long = document.getElementById("question-datagrid").rows.length;
                while (long>0){
                    //如果个数大于3删除最后一个
                    $('#question-datagrid tr:last' ).remove();
                    long--;
                }
            }
        });
    }


    /**
     * 载入数据
     */
    $('#data-datagrid').datagrid({
        url: '${wadewhy}/after/question/list.action',
        rownumbers: true,
        singleSelect: true,
        pageSize: 20,
        pagination: true,
        multiSort: true,
        fitColumns: true,
        idField: 'id',
        treeField: 'name',
        nowrap: false,
        fit: true,
        columns: [[
            {field: 'chk', checkbox: true},
            {field: 'title', title: '试题题目', width: 300, sortable: true},
            {
                field: 'subjectid', title: '所属学科', width: 100, formatter: function (value, index, row) {
                    var subjectList = $("#add-subjectId").combobox("getData");
                    for (var i = 0; i < subjectList.length; i++) {
                        if (subjectList[i].id == value) return subjectList[i].name;
                    }
                    return value;
                }
            },
            {field: 'score', title: '分值', width: 50, sortable: true},
            {
                field: 'type', title: '试题类型', width: 100, formatter: function (value, index, row) {
                    switch (value) {
                        case 0:
                            return '单选题';
                        case 1:
                            return '多选题';
                        case 2:
                            return '判断题';
                        case 3:
                            return '简答题'
                        default:
                            return value;
                    }
                }
            },
            {field: 'answer', title: '正确答案', width: 80},
            {
                field: 'createTime', title: '添加时间', width: 200, formatter: function (value, index, row) {
                    return format(value);
                }
            },
            {
                field: 'icon', title: '权限操作', width: 100, formatter: function (value, row, index) {
                    var test = '<a class="authority-edit" onclick="selectQuestion(' + row.id + ')"></a>'
                    return test;
                }
            },
        ]],
        onLoadSuccess: function (data) {
            $('.authority-edit').linkbutton({text: '查看或者修改', plain: true, iconCls: 'icon-zoom-in'});
        }
    });
</script>