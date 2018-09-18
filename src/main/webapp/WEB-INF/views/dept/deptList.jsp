<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018-06-12
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="/static/css/outlook_tree.css" rel="stylesheet" type="text/css">
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
                            <div class="active section"><i class="ui icon sitemap"></i>组织架构
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
                            部门列表
                        </h6>
                        <%--<h5 class="ui right floated header">--%>
                        <%--<i class="ion-ios-arrow-up icon link updownicon" data-flag="0"></i>--%>
                        <%--<i class="ion-ios-refresh-empty refreshing icon link"></i>--%>
                        <%--</h5>--%>
                        <div class="clearfix"></div>
                    </div>
                    <div class="ui segment basic">
                        <div class="ui inverted dimmer" id="deptTreeDimmer">
                            <div class="ui text loader">加载中...</div>
                        </div>
                        <ul id="deptTree" class="ztree"></ul>
                    </div>
                </div>
            </div>
            <div class="twelve wide column" style="padding-left: 0px;">
                <div class="ui segments">
                    <div class="ui segment" style="padding-bottom: 0px;">
                        <h6 class="ui left floated header">
                            部门明细
                        </h6>
                        <div class="clearfix"></div>
                    </div>
                    <form id="editDeptForm" class="ui form segment basic">
                        <div class="ui negative message hidden">
                            <i class="close icon"></i>
                            <div class="header">
                                <h5>错误提示</h5>
                            </div>
                            <ul class="list" id="errorMsg">

                            </ul>
                        </div>
                        <div class="two fields">
                            <div class="field">
                                <label style="color:red">部门名称</label>
                                <input placeholder="请输入部门名称" id="deptName" name="deptName" type="text">
                                <input type="hidden" id="deptId" name="deptId">
                            </div>
                            <div class="field">
                                <label style="color:red">部门编码</label>
                                <input placeholder="请输入部门编码" id="deptCode" name="deptCode" type="text">
                            </div>
                        </div>
                        <div class="two fields">
                            <div class="field">
                                <label style="color:red">上级部门</label>
                                <div class="ui fluid  search">
                                    <div class="ui icon input">
                                        <input class="prompt" placeholder="部门" id="showDeptName" name="showDeptName"
                                               type="text">
                                        <input type="hidden" id="deptPid" name="deptPid">
                                        <i class="search icon"></i>
                                    </div>
                                    <div class="results" data-filtered="filtered"></div>
                                </div>
                            </div>
                            <div class="field">
                                <label>&nbsp;</label>
                                <div class="ui toggle checkbox" id="deptEnable" name="deptEnable">
                                    <input type="checkbox" checked>
                                    <label>是否启用</label>
                                </div>
                            </div>
                        </div>
                        <div class="field">
                            <label>备注</label>
                            <textarea id="deptRemark" name="deptRemark"></textarea>
                        </div>
                        <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}">
                    </form>
                    <div class="ui segment" style="padding-bottom: 0px;">
                        <div class="ui green button right floated" id="saveDeptBtn"><i class="icon save"></i>保存</div>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<script src="/static/js/dept/deptList.js"></script>