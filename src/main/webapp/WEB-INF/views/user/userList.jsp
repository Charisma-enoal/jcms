<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018-03-26
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="mainWrap navslide">
    <div class="ui equal width left aligned padded grid stackable">


        <div class="row" style="padding-bottom: 0px;">
            <div class="sixteen wide column">
                <div class="ui segments no-padding">
                    <div class="ui segment ">
                        <div class="ui small breadcrumb">
                            <div class="section">首页</div>
                            <i class="right angle icon divider"></i>
                            <div class="section"><i class="ui icon ion-settings"></i>系统管理</div>
                            <i class="right angle icon divider"></i>
                            <div class="active section"><i class="ui icon ion-person"></i>用户列表
                            </div>
                        </div>
                        <%--<div class="ui divider  "></div>--%>
                    </div>


                </div>
            </div>
        </div>

        <div class="row">
            <div class="sixteen wide column">
                <div class="ui form segments" id="superQueryForm">
                    <div class="ui segment" style="padding-bottom: 0px;">
                        <h5 class="ui left floated header">
                            高级查询
                        </h5>
                        <h5 class="ui right floated header">
                            <i class="ion-ios-arrow-up icon link updownicon" data-flag="0"></i>
                            <i class="ion-ios-refresh-empty refreshing icon link"></i>
                        </h5>
                        <div class="clearfix"></div>
                    </div>

                    <div class="ui segment basic">
                        <div class="four fields">
                            <div class="field">
                                <label>用户账号</label>
                                <input placeholder="用户账号" id="searchUserName" name="searchUserName" type="text">
                            </div>
                            <div class="field">
                                <label>真实姓名</label>
                                <input placeholder="真实姓名" id="searchUserRealName" name="searchUserRealName" type="text">
                            </div>
                            <div class="field">
                                <label>部门</label>
                                <%--<input placeholder="部门" id="searchUserDept" name="searchUserDept" type="text">--%>
                                <div class="ui fluid  search">
                                    <div class="ui icon input">
                                        <input class="prompt" placeholder="部门" id="searchUserDept" name="searchUserDept"
                                               type="text">
                                        <input type="hidden" id="searchUserId" name="searchUserId">
                                        <i class="search icon"></i>
                                    </div>
                                    <div class="results" data-filtered="filtered"></div>
                                </div>
                            </div>
                            <div class="field">
                                <label>状态</label>
                                <select class="ui dropdown" name="searchUserEnable" id="searchUserEnable">
                                    <option value="-1" data-filtered="filtered">所有</option>
                                    <option value="1" data-filtered="filtered">正常</option>
                                    <option value="0" data-filtered="filtered">禁用</option>
                                </select>
                            </div>
                        </div>
                        <div class="two fields">
                            <div class="field">
                                <label>创建日期</label>
                                <input placeholder="创建日期" id="searchUserCreateDate" name="searchUserCreateDate"
                                       type="text">
                            </div>
                            <div class="field">
                                <label>&nbsp;</label>
                                <button class="ui red labeled icon right floated button" id="queryBySuperBtn">
                                    <i class="icon search"></i>查询
                                </button>
                            </div>
                        </div>
                    </div>

                    <%--<div class="ui segment basic">--%>
                    <%--<div class="sixteen wide column">--%>

                    <%--</div>--%>
                    <%--</div>--%>

                </div>
            </div>
        </div>

        <div class="row no-padding">
            <div class="sixteen wide column">
                <div class="ui segments no-padding">
                    <div class="ui segment " style="padding-bottom: 0px;">
                        <h5 class="ui left floated header">
                            用户管理
                        </h5>
                        <%--<h5 class="ui right floated header">--%>
                        <%--<i class="ion-ios-arrow-up icon link"></i>--%>
                        <%--&lt;%&ndash;<i class="ion-ios-refresh-empty refreshing icon link"></i>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<i class="ion-ios-close-empty icon link"></i>&ndash;%&gt;--%>
                        <%--</h5>--%>
                        <div class="clearfix"></div>
                    </div>
                    <div class="ui segment basic">
                        <div class="sixteen wide column">
                            <button class="ui green labeled icon right floated  button" id="exportUserForExcel">
                                <i class="icon download"></i>导出
                            </button>
                            <sec:authorize access="hasAuthority('OPTION_USER_EDIT')">
                                <button class="ui primary labeled icon right floated  button" id="addUserBtn">
                                    <i class="icon add"></i>新增
                                </button>
                            </sec:authorize>
                            <div class="ui action input">
                                <input type="text" placeholder="关键字..." id="keyWorld" name="keyWorld">
                                <div class="ui compact selection dropdown" tabindex="0"><select id="keyType">
                                    <option value="0">下拉选择</option>
                                    <option value="1">用户账号</option>
                                    <option value="2">真实姓名</option>
                                </select><i class="dropdown icon"></i>
                                    <div class="text">下拉选择</div>
                                    <div class="menu" tabindex="-1">
                                        <div class="item active selected" data-value="0">下拉选择</div>
                                        <div class="item" data-value="1">用户账号</div>
                                        <div class="item" data-value="2">真实姓名</div>
                                    </div>
                                </div>
                                <button type="button" class="ui button" id="queryByKeyBtn"><i class="icon search"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="ui segment basic">
                        <div class="ui inverted dimmer" id="gridDimmer">
                            <div class="ui text loader">加载中...</div>
                        </div>
                        <table class="ui selectable celled striped table" id="userList">
                            <thead>
                            <tr>
                                <th>用户账号</th>
                                <th>真实姓名</th>
                                <th>所属部门</th>
                                <th>状态</th>
                                <th>创建时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <template v-if="userList == null || userList.length <= 0">
                                <tr>
                                    <td colspan="6" style="text-align: center">
                                        <div class="ui label">没有查询到任何数据...</div>
                                    </td>
                                </tr>
                            </template>
                            <tr v-for="user in userList">
                                <td>
                                    {{user.userName}}
                                </td>
                                <td>{{user.userRealName}}</td>
                                <td>{{user.sysDeptEntity.deptName}}</td>
                                <td v-if="user.userEnable == 1">
                                    正常
                                </td>
                                <td v-if="user.userEnable == 0">
                                    禁用
                                </td>
                                <td>{{user.userCreateDate}}</td>
                                <td style="width: 12%">
                                    <a class="ui icon " title="修改" v-on:click="editUser(user.userName)"><i
                                            class="ion-edit icon large"></i></a>
                                    <a class="ui icon " title="删除" v-on:click="deleteUser(user.userId)"><i
                                            class="ion-trash-a icon red large"></i></a>
                                    <a class="ui icon" title="分配角色" v-on:click="alloRole(user.userId)"><i
                                            class="key icon olive large"></i></a>
                                    <template v-if="user.userEnable == 1">
                                        <a class="ui icon" title="禁用"
                                           v-on:click="disabledUser(user.userId,user.userEnable)"><i
                                                class="lock icon red large"></i></a>
                                    </template>
                                    <template v-if="user.userEnable == 0">
                                        <a class="ui icon" title="启用"
                                           v-on:click="disabledUser(user.userId,user.userEnable)"><i
                                                class="unlock alternate icon green large"></i></a>
                                    </template>
                                </td>
                            </tr>
                            </tbody>
                            <tfoot>
                            <tr>
                                <th colspan="6">
                                    <div id="pagePlugin" class="ui right floated">

                                    </div>
                                </th>
                            </tr>
                            </tfoot>
                        </table>
                    </div>

                </div>
            </div>
        </div>

    </div>
</div>

<div class="ui mini modal user">
    <div class="ui inverted dimmer" id="editDimmer">
        <div class="ui text loader">加载中...</div>
    </div>
    <i class="close icon"></i>
    <div class="header">编辑用户</div>
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
            <div class="field">
                <label style="color:red">部门</label>
                <div class="ui fluid  search">
                    <div class="ui icon input">
                        <input class="prompt" placeholder="部门" id="userDeptName" name="userDeptName" type="text">
                        <input type="hidden" id="userDept" name="userDept">
                        <i class="search icon"></i>
                    </div>
                    <div class="results" data-filtered="filtered"></div>
                </div>
            </div>
            <div class="field">
                <label style="color:red">用户名</label>
                <input placeholder="用户名" type="text" id="userName" name="userName">
                <input type="hidden" id="userId" name="userId">
            </div>
            <div class="field">
                <label style="color:red">真实姓名</label>
                <input placeholder="真实姓名" type="text" id="userRealName" name="userRealName">
            </div>
            <%--<div class="field">--%>
            <%--<label style="color:red">密码</label>--%>
            <%--<input placeholder="密码" type="text" id="userPassword" name="userPassword">--%>
            <%--</div>--%>

            <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}">
        </form>
    </div>
    <div class="actions">
        <div class="ui green button" id="saveUserBtn"><i class="icon save"></i>保存</div>
        <div class="ui button cancel"><i class="icon cancel"></i>取消</div>
    </div>
</div>

<div class="ui mini modal role">
    <i class="close icon"></i>
    <div class="header">分配角色</div>
    <div class="content">
        <div class="ui segment basic" style="padding-top: 0px;">
            <div class="ui inverted dimmer" id="roleDimmer">
                <div class="ui text loader">加载中...</div>
            </div>
            <div class="ui form">
                <div class="field">
                    <label style="color: red">
                        分配角色
                    </label>
                    <div class="ui multiple selection dropdown">
                        <input name="userRoles" id="userRoles" type="hidden">
                        <i class="icon dropdown"></i>
                        <div class="default text"></div>
                        <div class="menu"></div>
                    </div>
                    <input type="hidden" id="alloUserId" name="alloUserId">
                </div>
            </div>
        </div>
    </div>
    <div class="actions">
        <div class="ui green button" id="saveUserRoleBtn"><i class="icon save"></i>保存</div>
        <div class="ui button cancel"><i class="icon cancel"></i>取消</div>
    </div>
</div>

<script src="/static/js/user/userList.js"></script>





