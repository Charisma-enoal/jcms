<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018-03-26
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navslide navwrap">
    <div class="ui menu icon borderless grid" data-color = "inverted white">
        <a class="item labeled openbtn">
            <i class="ion-navicon-round big icon"></i>
        </a>
        <a class="item labeled expandit" onclick="toggleFullScreen(document.body)">
            <i class="ion-arrow-expand big icon"></i>
        </a>
        <div class="right menu colhidden">

            <div class="ui dropdown item labeled icon" tabindex="0">
                <i class="bell icon"></i>
                <div class="ui red label mini circular">6</div>
                <div class="menu transition hidden" tabindex="-1">
                    <div class="header">
                        待办事项
                    </div>
                    <div class="item">
                        Janice Robinson
                    </div>
                    <div class="item">
                        Cynthia May
                    </div>
                    <div class="item">
                        Hugh Carter
                    </div>
                    <div class="header">
                        消息
                    </div>
                    <div class="item">
                        Pauline Cain
                    </div>
                    <div class="item">
                        Marco Beck
                    </div>
                    <div class="item">
                        Sue Quinn
                    </div>
                </div>
            </div>
            <div class="ui dropdown item" tabindex="0">
                Language <i class="dropdown icon"></i>
                <div class="menu transition hidden" tabindex="-1">
                    <a class="item"><i class="china flag"></i>简体中文 </a>
                    <a class="item"><i class="united kingdom flag"></i>English</a>
                    <a class="item"><i class="turkey flag"></i>Turkish</a>
                    <a class="item"><i class="spain flag"></i>Spanish</a>
                </div>
            </div>
            <div class="ui dropdown item" tabindex="0">
                <img class="ui mini circular image" src="/static/img/matt.jpg" alt="label-image">
                <div class="menu" tabindex="-1">
                    <a class="item" href="/agenda/list">日程</a>
                    <a class="item" href="settings.html">个人设置</a>
                    <div class="ui divider"></div>
                    <a class="item" href="/logout"> 退出登录</a>
                </div>
            </div>
            <a class="item labeled computer only">
                <i class="ion-help-circled large icon"></i>
            </a>
        </div>
    </div>
</div>
