<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018-02-09
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>您没有访问该页面的权限，请先登录！</title>
    <link href="/static/semantic/semantic.css" rel="stylesheet" type="text/css">
    <script src="/static/js/jquery-3.3.1.min.js"></script>
    <script src="/static/semantic/semantic.min.js"></script>
</head>
<body>
<div class="ui page dimmer">
    <div class="content">
        <div class="content">您没有访问该页面的权限，请先登录！</div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        $('.ui.page.dimmer').dimmer('show');
    });
</script>
</body>
</html>
