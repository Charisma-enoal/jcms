<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018-08-08
  Time: 15:15
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
                            <div class="active section"><i class="ui icon users"></i>角色管理
                            </div>
                        </div>
                        <%--<div class="ui divider  "></div>--%>
                    </div>


                </div>
            </div>
        </div>


        <div class="row">
            <div class="sixteen wide column">
                <div class="ui segments no-padding">
                    <div class="ui segment " style="padding-bottom: 0px;">
                        <h5 class="ui left floated header">
                            角色管理
                        </h5>
                        <div class="clearfix"></div>
                    </div>
                    <div class="ui segment basic">
                        <div class="sixteen wide column">
                            <button class="ui primary labeled icon right floated  button" id="addRoleBtn">
                                <i class="icon add"></i>新增
                            </button>
                            <div class="ui action input">
                                <input type="text" placeholder="关键字..." id="keyWorld" name="keyWorld">
                                <div class="ui compact selection dropdown" tabindex="0"><select id="searchRoleEnable">
                                    <option value="-1">状态</option>
                                    <option value="1">启用</option>
                                    <option value="0">禁用</option>
                                </select><i class="dropdown icon"></i>
                                    <div class="text">下拉选择</div>
                                    <div class="menu" tabindex="-1">
                                        <div class="item active selected" data-value="-1">状态</div>
                                        <div class="item" data-value="1">启用</div>
                                        <div class="item" data-value="0">禁用</div>
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
                        <table class="ui selectable celled striped table" id="roleList">
                            <thead>
                            <tr>
                                <th>角色名称</th>
                                <th>角色状态</th>
                                <th>备注</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <template v-if="roleList == null || roleList.length <= 0">
                                <tr>
                                    <td colspan="4" style="text-align: center">
                                        <div class="ui label">没有查询到任何数据...</div>
                                    </td>
                                </tr>
                            </template>
                            <tr v-for="role in roleList">
                                <td>
                                    {{role.roleName}}
                                </td>
                                <td v-if="role.roleEnable == 1">
                                    正常
                                </td>
                                <td v-if="role.roleEnable == 0">
                                    禁用
                                </td>
                                <td>{{role.roleRemark}}</td>
                                <td style="width: 10%">
                                    <template v-if="role.roleEnable == 0">
                                        <a class="ui icon " title="启用" v-on:click = "enableRole(role.roleId)"><i class="checkmark icon green large"></i></a>
                                    </template>
                                    <template v-if="role.roleEnable == 1">
                                        <a class="ui icon " title="禁用" v-on:click = "lockRole(role.roleId)"><i class="ion-ios-locked icon orange large"></i></a>
                                    </template>
                                    <a class="ui icon " title="删除" v-on:click = "deleteRole(role.roleId)"><i class="ion-trash-a icon red large"></i></a>
                                    <a class="ui icon" title = "分配权限" v-on:click = "alloAuth(role.roleId)"><i class="key icon olive large"></i></a>
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

<div class="ui mini modal role">
    <i class="close icon"></i>
    <div class="header">新增角色</div>
    <div class="content">
        <div class="ui negative message hidden">
            <i class="close icon"></i>
            <div class="header">
                <h5>错误提示</h5>
            </div>
            <ul class="list" id = "errorMsg">

            </ul>
        </div>
        <form class="ui form" id="editForm">
            <div class="field">
                <label style="color:red">角色名称</label>
                <input placeholder="角色名称" type="text" id="roleName" name="roleName">
            </div>
            <div class="field">
                <label>备注</label>
                <textarea id="roleRemark" placeholder="备注" name="roleRemark" rows="3"></textarea>
            </div>
            <div class="field" style="text-align: right">
                <label>&nbsp;</label>
                <div class="ui toggle checkbox" id="roleEnable" >
                    <input type="checkbox" checked="checked" name="roleEnable">
                    <label>启用</label>
                </div>
            </div>
            <input name = "${_csrf.parameterName}" type="hidden" value="${_csrf.token}">
        </form>
    </div>
    <div class="actions">
        <div class="ui green button" id="saveRoleBtn"><i class="icon save"></i>保存</div>
        <div class="ui button cancel"><i class="icon cancel"></i>取消</div>
    </div>
</div>


<div class="ui mini modal auth">
    <i class="close icon"></i>
    <div class="header">分配权限</div>
    <div class="content">
        <div class="ui negative message hidden">
            <i class="close icon"></i>
            <div class="header">
                <h5>错误提示</h5>
            </div>
            <ul class="list" id = "errorMsgForAuth">

            </ul>
        </div>
        <div class="ui segment basic" style="padding-top: 0px;">
            <div class="ui inverted dimmer" id="authTreeDimmer">
                <div class="ui text loader">加载中...</div>
            </div>
            <ul id="authTree" class="ztree" style="padding-top: 0px;"></ul>
        </div>
    </div>
    <div class="actions">
        <div class="ui green button" id="saveAuthBtn"><i class="icon save"></i>保存</div>
        <div class="ui button cancel"><i class="icon cancel"></i>取消</div>
    </div>
</div>

<script src="/static/js/role/role.js"></script>
