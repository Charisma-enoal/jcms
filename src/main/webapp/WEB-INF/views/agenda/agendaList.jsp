<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018-08-30
  Time: 10:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="mainWrap navslide">
    <div class="ui equal width center aligned padded grid stackable">
        <div class="row">
            <div class="twelve wide column">
                <div id="agendaPanel"></div>

            </div>
        </div>
    </div>
</div>
<div class="ui mini modal agenda">
    <i class="close icon"></i>
    <div class="header">编辑日程</div>
    <div class="content">
        <div class="ui negative message hidden">
            <i class="close icon"></i>
            <div class="header">
                <h5>错误提示</h5>
            </div>
            <ul class="list" id="errorMsg">

            </ul>
        </div>
        <form class="ui form" id="editForm">
            <div class="field" style="text-align: right">
                <label>&nbsp;</label>
                <div class="ui toggle checkbox" id="agendaAllDay">
                    <input type="checkbox" checked="checked" name="agendaAllDay">
                    <label>全天</label>
                </div>
            </div>
            <div class="field datediv" style="display: none">
                <label style="color: orange">起止时间</label>
                <input placeholder="起止时间" id="beginEndDateTime" name="beginEndDateTime"
                       type="text">
            </div>
            <div class="field">
                <label style="color:red">日程</label>
                <textarea id="agendaTitle" placeholder="请填写日程信息" name="agendaTitle" rows="3"></textarea>
            </div>
            <div class="field">
                <label>Url</label>
                <input id="agendaUrl" name="agendaUrl" placeholder="如您的日程有外链，请将链接地址填与此处">
            </div>
            <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}">
        </form>
    </div>
    <div class="actions">
        <div class="ui green button" id="saveRoleBtn"><i class="icon save"></i>保存</div>
        <div class="ui button cancel"><i class="icon cancel"></i>取消</div>
    </div>
</div>
<script src="/static/js/agenda/agendaList.js"></script>