<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:set var="wadewhy" value="${pageContext.request.contextPath}"/>
<!-- saved from url=(0051)http://demo1.mycodes.net/denglu/HTML5_yonghudenglu/ -->
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>wadewhy考试管理后台</title>
    <meta name="description" content="particles.js is a lightweight JavaScript library for creating particles.">
    <meta name="author" content="Vincent Garreau">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" media="screen" href="${wadewhy}/resources/admin/login/css/style.css">
    <link rel="stylesheet" type="text/css" href="${wadewhy}/resources/admin/login/css/reset.css">
    <link rel="stylesheet" type="text/css" href="${wadewhy}/resources/admin/easyui/easyui/1.3.4/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${wadewhy}/resources/admin/easyui/easyui/1.3.4/themes/icon.css" />
    <script type="text/javascript" src="${wadewhy}/resources/admin/easyui/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${wadewhy}/resources/admin/easyui/easyui/1.3.4/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${wadewhy}/resources/admin/easyui/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
<body>

<div id="particles-js">

    <div class="login" style="display: block;">
        <div class="login-top">
            登录页面

        </div>
        <div class="login-center clearfix">
            <div class="login-center-img"><img src="${wadewhy}/resources/admin/login/images/name.png"></div>
            <div class="login-center-input">
                <input type="text" name="name" id="name" value="" placeholder="请输入您的用户名" onfocus="this.placeholder=&#39;&#39;" onblur="this.placeholder=&#39;请输入您的用户名&#39;">
                <div class="login-center-input-text">用户名</div>
            </div>
        </div>
        <div class="login-center clearfix">
            <div class="login-center-img"><img src="${wadewhy}/resources/admin/login/images/password.png"></div>
            <div class="login-center-input">
                <input type="password" name="pwd" id="pwd" value="" placeholder="请输入您的密码" onfocus="this.placeholder=&#39;&#39;" onblur="this.placeholder=&#39;请输入您的密码&#39;">
                <div class="login-center-input-text">密码</div>
            </div>
        </div>
        <div class="login-center clearfix">
            <div class="login-center-img"><img src="${wadewhy}/resources/admin/login/images/cpacha.png"></div>
            <div class="login-center-input">
                <input style="width:50%;" type="text" name="cpacha" id="cpacha" value="" placeholder="请输入验证码" onfocus="this.placeholder=&#39;&#39;" onblur="this.placeholder=&#39;请输入验证码&#39;">
                <div class="login-center-input-text">验证码</div>
                <img id="cpacha-img" title="点击切换验证码" style="cursor:pointer;" src="${wadewhy}/after/login/getCode.action" width="110px" height="30px" onclick="this.src=this.src+'?'">
            </div>
        </div>
        <div class="login-button">
            登录
        </div>
    </div>
    <div class="sk-rotating-plane"></div>
    <canvas class="particles-js-canvas-el" width="1147" height="952" style="width: 100%; height: 100%;"></canvas></div>

<!-- scripts -->
<script src="${wadewhy}/resources/admin/login/js/particles.min.js"></script>
<script src="${wadewhy}/resources/admin/login/js/app.js"></script>
<script type="text/javascript">
    //解决session过期跳转到登录页并跳出iframe框架（或者layui弹出层）
    $(document).ready(function () {
        if (window != top) {
            top.location.href = location.href;
        }
    });
    function hasClass(elem, cls) {
        cls = cls || '';
        if (cls.replace(/\s/g, '').length == 0) return false; //当cls没有参数时，返回false
        return new RegExp(' ' + cls + ' ').test(' ' + elem.className + ' ');
    }

    function addClass(ele, cls) {
        if (!hasClass(ele, cls)) {
            ele.className = ele.className == '' ? cls : ele.className + ' ' + cls;
        }
    }

    function removeClass(ele, cls) {
        if (hasClass(ele, cls)) {
            var newClass = ' ' + ele.className.replace(/[\t\r\n]/g, '') + ' ';
            while (newClass.indexOf(' ' + cls + ' ') >= 0) {
                newClass = newClass.replace(' ' + cls + ' ', ' ');
            }
            ele.className = newClass.replace(/^\s+|\s+$/g, '');
        }
    }


    document.querySelector(".login-button").onclick = function(){
        var name = $("#name").val();
        var pwd = $("#pwd").val();
        var cpacha = $("#cpacha").val();
        if(name == '' || name == 'undefined'){
                $.messager.alert('提示信息','用户名不能为空!','error');
            return;
        }
        if(pwd == '' || pwd == 'undefined'){
            $.messager.alert('提示信息','密码不能为空!','error');
            return;
        }
        if(cpacha == '' || cpacha == 'undefined'){
            $.messager.alert('提示信息','验证码不能为空!','error');
            return;
        }
        addClass(document.querySelector(".login"), "active")
        addClass(document.querySelector(".sk-rotating-plane"), "active")
        document.querySelector(".login").style.display = "none"
        function showMsg(msg){
            $.messager.show({
                title:'登录消息',
                msg:msg,
                showType:'slide',
                timeout:1500,
                style:{
                    right:'',
                    bottom:''
                }
            });
        }
        $.ajax({
            url:'${wadewhy}/after/login/login.action',
            data:{name:name,pwd:pwd,cpacha:cpacha},
            type:'post',
            dataType:'json',
            success:function(data){
                if(data.code == 200){
                    $.messager.alert('登录消息', data.msg ,'info', function (fn) {
                        //由sys控制器跳转到/system/index/index.html页面
                        location.href = "${wadewhy}/after/sys/index.action";
                    });
                    // showMsg(data.msg);

                } else{
                    removeClass(document.querySelector(".login"), "active");
                    removeClass(document.querySelector(".sk-rotating-plane"), "active");
                    document.querySelector(".login").style.display = "block";
                    showMsg(data.msg);
                    //验证码错误或密码错误，重新刷新验证码
                    $.get("/after/login/getCode.action",function () {
                        $("#cpacha-img").attr("src","${wadewhy}/after/login/getCode.action");
                    })
                }
            }
        });

    }
</script>
</body></html>