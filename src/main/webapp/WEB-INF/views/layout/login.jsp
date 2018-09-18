<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018-01-25
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page session="true" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>用户登录</title>
    <link href="/static/semantic/semantic.css" rel="stylesheet" type="text/css">
    <script src="/static/js/jquery-3.3.1.min.js"></script>
    <script src="/static/semantic/semantic.min.js"></script>
    <style type="text/css">

        body {
            /*background-color: #DADADA;*/
        }
        body > .grid {
            height: 100%;
        }
        .image {
            margin-top: -100px;
        }
        .column {
            max-width: 450px;
        }


    </style>
</head>
<body>
<div class="ui middle aligned center aligned grid">
    <div class="column">
        <%--<h2 class="ui teal image header">--%>

        <%--<div class="content">--%>
        <%--欢迎登录JCMS--%>
        <%--</div>--%>
        <%--</h2>--%>
        <div>
            <img src="/static/img/logo.png" class="image small">
        </div>
        <form class="ui large form" action="/login" method="post">
            <div class="ui stacked segment" style="background-color: #f9f9f9">
                <div class="field">
                    <div class="ui left icon input">
                        <i class="user icon"></i>
                        <input type="text" name="username" placeholder="请输入您的用户名">
                    </div>
                </div>
                <div class="field">
                    <div class="ui left icon input">
                        <i class="lock icon"></i>
                        <input type="password" name="password" placeholder="请输入您的密码">
                    </div>
                </div>
                <div class="field" style="text-align: right">
                    <div class="ui toggle checkbox">
                        <input type="checkbox" tabindex="0" name="remember-me" id="remember-me">
                        <label for="remember-me">记住我</label>
                    </div>
                </div>

                <%--<button class="ui fluid large teal sub button" id="btnLogin" name="btnLogin">--%>
                <%--登录--%>
                <%--</button>--%>
                <input type="submit" class="ui fluid large teal sub button" id="btnLogin" name="btnLogin" value="登录"/>
                <input name = "${_csrf.parameterName}" type="hidden" value="${_csrf.token}">
            </div>
            <c:if test="${param.error == true}">
                <div class="ui message red">
                    <a class="item">
                        <div class="ui horizontal label red">
                            <spring:message code="PasswordComparisonAuthenticator.badCredentials"/>
                        </div>
                    </a>
                </div>
            </c:if>
            <div class="ui error message">
            </div>
        </form>
        <div class="ui message">
            <a class="item">
                <div class="ui  horizontal label">@Copy Right JCMS Co.,Ltd</div>
            </a>
        </div>
    </div>
</div>
</body>
</html>

<script>
    $(function(){
        $('.ui .form')
            .form({
                on: 'blur',
                fields : {
                    username :{
                        identifier : 'username',
                        rules : [
                            {
                                type : 'empty',
                                prompt : '请输入您的用户名'
                            }
                        ]
                    },
                    password: {
                        identifier: 'password',
                        rules: [
                            {
                                type   : 'empty',
                                prompt : '请输入您的密码'
                            },
                            {
                                type   : 'minLength[4]',
                                prompt : '请输入4-12位密码'
                            }
                        ]
                    }
                }
            });
    });
</script>
