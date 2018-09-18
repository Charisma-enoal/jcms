<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018-08-16
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="mainWrap navslide">
    <div class="ui equal width left aligned padded grid stackable">
        <div class="row" style="padding-bottom: 0px;">
            <div class="sixteen wide column">
                <div class="ui segments no-padding">
                    <div class="ui segment">
                        <div class="ui small breadcrumb">
                            <div class="section">首页</div>
                            <i class="right angle icon divider"></i>
                            <div class="section"><i class="ui icon ion-settings"></i>系统管理</div>
                            <i class="right angle icon divider"></i>
                            <div class="active section"><i class="ui icon registered"></i>权限配置
                            </div>
                        </div>
                        <%--<div class="ui divider  "></div>--%>
                    </div>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="four wide column">
                <div class="ui segments">
                    <div class="ui segment" style="padding-bottom: 0px;">
                        <h6 class="ui left floated header">
                            权限列表
                        </h6>
                        <%--<h5 class="ui right floated header">--%>
                        <%--<i class="ion-ios-arrow-up icon link updownicon" data-flag="0"></i>--%>
                        <%--<i class="ion-ios-refresh-empty refreshing icon link"></i>--%>
                        <%--</h5>--%>
                        <div class="clearfix"></div>
                    </div>
                    <div class="ui segment basic">
                        <div class="ui inverted dimmer" id="authTreeDimmer">
                            <div class="ui text loader">加载中...</div>
                        </div>
                        <ul id="authTree" class="ztree"></ul>
                    </div>
                </div>
            </div>
            <div class="twelve wide column" style="padding-left: 0px;">
                <div class="ui segments">
                    <div class="ui segment" style="padding-bottom: 0px;">
                        <h6 class="ui left floated header">
                            权限明细
                        </h6>
                        <div class="clearfix"></div>
                    </div>
                    <form id="editAuthForm" class="ui form segment basic">
                        <div class="ui negative message hidden">
                            <i class="close icon"></i>
                            <div class="header">
                                <h5>错误提示</h5>
                            </div>
                            <ul class="list" id="errorMsg">

                            </ul>
                        </div>
                        <div class="three fields">
                            <div class="field">
                                <label style="color:red">权限名称</label>
                                <input placeholder="请输入权限名称" id="authName" name="authName" type="text">
                                <input type="hidden" id="authId" name="authId">
                            </div>
                            <div class="field">
                                <label style="color:red">权限编码</label>
                                <input placeholder="请输入权限编码" id="authCode" name="authCode" type="text">
                            </div>
                            <div class="field">
                                <label>上级权限</label>
                                <div class="ui disabled input">
                                    <input id="authPName" name="authPName" type="text">
                                    <input id="authPid" name="authPid" type="hidden">
                                </div>
                            </div>
                        </div>
                        <div class="three fields">
                            <div class="field">
                                <label style="color:red">权重</label>
                                <input type="number" id="authPriority" name="authPriority" placeholder="请输入该权限的排序权重">
                            </div>
                            <div class="field">
                                <label>权限图标</label>
                                <input type="text" id="authIcon" name="authIcon" placeholder="权限的图标">
                            </div>
                            <div class="field">
                                <label>&nbsp;</label>
                                <div class="ui toggle checkbox" id="authIsOption" name="authIsOption">
                                    <input type="checkbox">
                                    <label>操作权限？</label>
                                </div>
                            </div>
                        </div>
                        <div class="field">
                            <label style="color:orange">权限URL</label>
                            <input type="text" id="authUrl" name="authUrl" placeholder="请输入该权限的访问URL,若该权限不属于菜单权限可以不填">
                        </div>
                        <div class="field">
                            <label>备注</label>
                            <textarea id="authRemark" name="authRemark"></textarea>
                        </div>
                        <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}">
                    </form>
                    <div class="ui segment" style="padding-bottom: 0px;">
                        <div class="ui green button right floated" id="saveAuthBtn"><i class="icon save"></i>保存</div>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<link href="/static/css/outlook_tree.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/static/js/auth/authList.js"></script>