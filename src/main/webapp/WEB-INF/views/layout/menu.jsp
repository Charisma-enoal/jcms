<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018-03-21
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="menu" class="ui accordion inverted">
    <a class="title item">
        <i class="ion-speedometer titleIcon icon"></i> 首页 <i class="dropdown icon"></i>
    </a>
    <div class="content">
        <a class="item transition hidden" href="/login" data-value="-1">
            首页
        </a>
    </div>
    <%--<div class="title item">--%>
        <%--<i class="icon titleIcon ion-settings"></i>--%>
        <%--系统管理--%>
        <%--<i class="dropdown icon"></i>--%>
    <%--</div>--%>
    <%--<div class="content">--%>
        <%--<a class="item" data-value="www.baidu.com" onclick="loadMenu(this);">修改密码</a>--%>
        <%--<a class="item">个人中心</a>--%>
        <%--<a class="item">绑定账号</a>--%>
    <%--</div>--%>
    <%--<div class="title item">--%>
        <%--<i class="icon titleIcon ion-settings"></i>--%>
        <%--系统管理--%>
        <%--<i class="dropdown icon"></i>--%>
    <%--</div>--%>
    <%--<div class="content">--%>
        <%--<a class="item">修改密码</a>--%>
        <%--<a class="item">个人中心</a>--%>
        <%--<a class="item">绑定账号</a>--%>
    <%--</div>--%>
    <%--<div class="title item">--%>
        <%--<i class="icon titleIcon ion-settings"></i>--%>
        <%--系统管理--%>
        <%--<i class="dropdown icon"></i>--%>
    <%--</div>--%>
    <%--<div class="content">--%>
        <%--<a class="item">修改密码</a>--%>
        <%--<a class="item">个人中心</a>--%>
        <%--<a class="item">绑定账号</a>--%>
    <%--</div>--%>
    <%--<div class="title item">--%>
        <%--<i class="icon titleIcon ion-settings"></i>--%>
        <%--系统管理--%>
        <%--<i class="dropdown icon"></i>--%>
    <%--</div>--%>
    <%--<div class="content">--%>
        <%--<a class="item">修改密码</a>--%>
        <%--<a class="item">个人中心</a>--%>
        <%--<a class="item">绑定账号</a>--%>
    <%--</div>--%>
    <%--<div class="title item">--%>
        <%--<i class="icon titleIcon ion-settings"></i>--%>
        <%--系统管理--%>
        <%--<i class="dropdown icon"></i>--%>
    <%--</div>--%>
    <%--<div class="content">--%>
        <%--<a class="item">修改密码</a>--%>
        <%--<a class="item">个人中心</a>--%>
        <%--<a class="item">绑定账号</a>--%>
    <%--</div>--%>
    <%--<div class="title item">--%>
        <%--<i class="icon titleIcon ion-settings"></i>--%>
        <%--系统管理--%>
        <%--<i class="dropdown icon"></i>--%>
    <%--</div>--%>
    <%--<div class="content">--%>
        <%--<a class="item">修改密码</a>--%>
        <%--<a class="item">个人中心</a>--%>
        <%--<a class="item">绑定账号</a>--%>
    <%--</div>--%>
</div>


<script src="/static/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
    $(function () {
        //获取到当前的URL
        var nowLocalUrl = window.location.pathname;
        $.ajax({
            type: 'GET',
            dataType : 'json',
            url: '/user/getMenuByUsername',
            success: function (obj) {
                var menuStr = "";
                $.each(obj,function(idx,item){
                    if (item.authPid == 1){
                        if(item.authUrl.split('/')[1] == nowLocalUrl.split('/')[1]){
                            menuStr += "<div class=\"title item active\">\n" +
                                "        <i class=\"icon titleIcon "+item.authIcon+"\"></i>\n" +
                                "        "+item.authName+"\n" +
                                "        <i class=\"dropdown icon\"></i>\n" +
                                "    </div>";
                        }else{
                            menuStr += "<div class=\"title item\">\n" +
                                "        <i class=\"icon titleIcon "+item.authIcon+"\"></i>\n" +
                                "        "+item.authName+"\n" +
                                "        <i class=\"dropdown icon\"></i>\n" +
                                "    </div>";
                        }

                        var menuChildBeginStr = "";
                        var menuChildEndStr = "";
                        var menuChildMainStr = "";
                        var menuChildBeginFlag = false;
                        $.each(obj,function(idj,childItem){
                            //循环子菜单
                            if (item.authId == childItem.authPid){
                                //如果当前的菜单和客户端URL相同
                                if (childItem.authUrl == nowLocalUrl){
                                    menuChildBeginFlag = true;
                                    menuChildMainStr += "<a class=\"item active transition visible\" href=\""+childItem.authUrl+"\">"+childItem.authName+"</a>";
                                }else{
                                    menuChildMainStr += "<a class=\"item transition visible\" href=\""+childItem.authUrl+"\">"+childItem.authName+"</a>";
                                }
                            }
                        });
                        if(menuChildBeginFlag){
                            menuChildBeginStr = "<div class=\"content active\">"
                            menuChildEndStr = "</div>";
                        }else{
                            menuChildBeginStr = "<div class=\"content\">"
                            menuChildEndStr = "</div>";
                        }
                        $('#menu').append(menuStr + menuChildBeginStr + menuChildMainStr + menuChildEndStr);
                    }

                });
            },
            error : function(XMLHttpRequest,textStatus,errorThrown){
                layer.alert("读取菜单失败，请联系系统管理员!",{icon : 2});
            }
        });


    });
</script>
