---
title: 基于SSM考试系统
author: 钟子豪
top: true
coverImg: true
cover: https://cdn.jsdelivr.net/gh/Wadewhy/blogimg@master/bgimg/spring1.jpg
top_img: https://cdn.jsdelivr.net/gh/Wadewhy/blogimg@master/bgimg/spring1.jpg
toc: true
mathjax: false
categories: 项目系列
tags:
  - 项目经验 系列
password:
abbrlink: 11722
date: 2020-03-22 21:05:31
abstract: 该文章已加密, 请输入密码查看。
message: 该文章已加密, 请输入密码查看。
wrong_pass_message: 密码不正确，请重新输入！
wrong_hash_message: 文章不能被校验, 不过您还是能看看解密后的内容！
---

# 基于SSM考试系统

## 前言

由于之前一直在使用`Spring Boot`，`Spring Cloud`，很久没有使用SSM框架来开发，因此花了10天左右的时间开发了此系统。本系统将开源并且继续维护，可在本博客最后端查看源代码链接，上传到本人github上。需要数据库文件的可留言或加好友寻求。并且写了一份详尽的开发文档。

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322213127.png)

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322213202.png)

## 技术

SSM+EasyUI+jQuery+Ajax+面向接口编程+Shiro安全框架 +mybatis_generator代码自动生成工具+Hutool工具库等。

## 模块

### 菜单权限管理模块

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322212524.png)

> 本模块是该后台系统的核心，通过菜单管理能完成系统的菜单和权限的控制，利用安全框架，拦截，验证以及授权功能

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322213423.png)	

### 角色权限管理模块

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322213913.png)

> 该模块给每个角色赋予相应的权限，没有权限的角色不能访问该功能，强制访问抛异常，统一异常拦截处理，跳转404

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322214026.png)	

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322214145.png)	

### 用户角色管理模块

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322214328.png)

> 该模块主要是后台用户的模块，支持头像，密码md5加密，随机加密盐算法，使得密码不可破，超级管理员拥有用户添加的权限，可给用户添加权限，支持增删改查，模糊查询，分页查询等功能。给用户分配角色

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322214645.png)	

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322214731.png)

### 专业课程管理模块

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322214822.png)

> 专业课程管理，在之后的学生注册时悬着相应的专业，然后试题管理员出完试题后，前台界面消息提醒学生考试，支持同一学生选择多个专业，双学位。基本的增删改查，模糊分页查询

### 考试模板管理模块

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322215142.png)

> 考试模板管理模块，比如今天要考试，生成考试模板，然后从题库随机选择相应数量的题型，保存在考试模板生成表中，前台学生点击考试，进行考试。

### 考试试题管理模板

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322215522.png)

> 考试试题，支持Excle直接导入也支持后台导入，支持查看原题，修改选项，删除试题等功能，如下图

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322215844.png)	

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322215911.png)	

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322220008.png)	

### 学生用户管理模板

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322220152.png)

> 前台学生注册，后台用户查询，修改考生，删除考生，重置密码，加密算法加密

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322220326.png)	

### 成绩统计管理模块

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322222322.png)

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322222357.png)

> 查询每次考试的成绩统计

### 学生答题管理模块

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322222805.png)

### 日志管理系统模块

记录登录的记录

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322222825.png)

## 登录界面

### 前台登录界面

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322222901.png)

### 后台登录界面

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322222933.png)

## 考试前台

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322223025.png)

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322223040.png)

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322225258.png)

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322225313.png)

## 开始考试

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322223056.png)	

## 考试界面

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322223141.png)	![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322223201.png)

## 查看考试记录

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322223300.png)

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322223322.png)

![](https://gitee.com/wadewhy/blog_img/raw/master/static/20200322223338.png)

# [点击查看项目源代码](https://github.com/Wadewhy/SSM_Exam.git)
