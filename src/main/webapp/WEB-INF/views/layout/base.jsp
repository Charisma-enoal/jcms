<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018-03-26
  Time: 14:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>JCMS系统管理</title>
    <link href="/static/semantic/semantic.css" rel="stylesheet" type="text/css">
    <link href="/static/plugins/pacejs/pace.css" rel="stylesheet" type="text/css">
    <link href="/static/plugins/ztree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
    <%--<link href="/static/semantic/dataTables.semanticui.min.css" rel="stylesheet">--%>
    <link href="/static/css/main.css" rel="stylesheet" type="text/css">
    <link href="/static/css/extra_colors.css" rel="stylesheet" type="text/css">
    <link href="/static/plugins/ionicons/css/ionicons.min.css" rel="stylesheet" type="text/css">

    <link href="/static/plugins/fullcalendar/fullcalendar.min.css" rel="stylesheet"/>
    <link href="/static/plugins/fullcalendar/fullcalendar.print.min.css" rel="stylesheet" media="print"/>
</head>
<body>
<div id="contextWrap">
    <div class="ui sidebar vertical left menu overlay borderless visible sidemenu grey inverted" style="transition-duration: 0.1s; overflow: hidden; outline: none;">
        <a class="item logo">
            <img src="/static/img/index.png">
        </a>
        <t:insertAttribute name="menu"/>

        <t:insertAttribute name="footer"/>
    </div>

    <a class="item hiddenCollapse"></a>

    <div class="pusher pushable">
        <div class="pusher" tabindex="1" style="overflow: auto; outline: none;">
            <t:insertAttribute name="header"/>

            <t:insertAttribute name="body"/>
        </div>
    </div>
    <input name = "${_csrf.parameterName}" type="hidden" value="${_csrf.token}">
</div>


<script src="/static/js/jquery-3.3.1.min.js"></script>
<script src="/static/semantic/semantic.min.js"></script>
<script src="/static/plugins/cookie/js.cookie.js"></script>
<%--<script src="/static/semantic/jquery.dataTables.js"></script>--%>
<%--<script src="/static/semantic/custom-datatable.js"></script>--%>
<script src="/static/plugins/nicescroll/jquery.nicescroll.min.js"></script>
<script src="/static/plugins/pacejs/pace.js"></script>
<script src="/static/plugins/laydate/laydate.js"></script>
<script src="/static/plugins/pagination/jquery.twbsPagination.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/js/component/searchdept.js"></script>
<script src="/static/plugins/ztree/jquery.ztree.core.min.js"></script>
<script src="/static/plugins/ztree/jquery.ztree.excheck.min.js"></script>
<!-- 开发环境版本，包含了用帮助的命令行警告 -->
<script src="/static/js/vue.js"></script>
<script src="/static/js/main.js"></script>
<script src="/static/plugins/fullcalendar/moment.min.js"></script>
<script src="/static/plugins/fullcalendar/fullcalendar.min.js"></script>
<script src="/static/plugins/fullcalendar/locale-all.js"></script>
</body>
</html>
