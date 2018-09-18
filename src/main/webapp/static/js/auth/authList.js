var curMenu = null,zTree_Menu = null;
//zTree配置
var setting = {
    view : {
        showLine: false, //是否显示note之间的连接线
        showIcon: false, //是否显示节点图标
        selectedMulti: false, //是否可以多选
        dblClickExpand: false,//双击节点时，是否展示子节点
        addDiyDom: addDiyDom //用于在节点上固定显示用户自定义控件
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeClick: beforeClick, //单击节点的回调函数，并根据返回值确定是否允许操作
        onClick : clickNoteMethod
    }
};

//zTree的数据
var zNodes = null;


/**
 * 自定义tree的DOM元素
 * @param treeId
 * @param treeNode
 */
function addDiyDom(treeId, treeNode) {
    var spaceWidth = 5;
    var switchObj = $("#" + treeNode.tId + "_switch"),
        icoObj = $("#" + treeNode.tId + "_ico");
    switchObj.remove();
    icoObj.before(switchObj);

    if (treeNode.level > 1) {
        var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
        switchObj.before(spaceStr);
    }
    var lev = treeNode.level;
    if (lev != 3){
        var sObj = $("#" + treeNode.tId + "_a");
        var addStr = "<a class='addStr'><i class='icon add circle green' id='addBtn_" + treeNode.tId
            + "' title='新增子权限'></i></a>";
        var removeStr = "<a class='addStr'><i class='icon remove circle red' id='delBtn_" + treeNode.tId
            + "' title='删除当前权限'></i></a>";
        sObj.after(addStr + removeStr);
    }


    var addBtn = $("#addBtn_" + treeNode.tId);
    var removeBtn = $("#delBtn_" + treeNode.tId);

    if (addBtn){
        addBtn.bind("click",function(){
            $("#authId").val(0);
            $("#authName").val('');
            $("#authCode").val('');
            $("#authPid").val(treeNode.id);
            $("#authPName").val(treeNode.name);
            $("#authPriority").val('');
            $("#authIcon").val('');
            $("#authIsOption").checkbox('set unchecked');
            $("#authUrl").val('');
            $("#authRemark").val('');
            initServerError();
        });
    }

    if (removeBtn){
        removeBtn.bind("click",function(){
            layer.confirm("您确定要删除该权限？", {
                title : '删除',
                btn: ['确定', '取消'],
                yes: function (index) {
                    $.ajax({
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            authId: treeNode.id,
                            _csrf: $("input[name='_csrf']").val()
                        },
                        url: '/auth/deleteAuth',
                        success: function (data) {
                            if (data.success) {
                                layer.close(index);
                                layer.alert('删除成功', {icon: 1}, function (index) {
                                    layer.close(index);
                                    initAuthTree(zTree_Menu.getNodeByTId(treeNode.tId));
                                });
                            }else{
                                layer.close(index);
                                layer.alert(data.errorMsg, {icon: 2}, function (index) {
                                    layer.close(index);
                                    initAuthTree(zTree_Menu.getNodeByTId(treeNode.tId));
                                });
                            }
                        }
                    });
                }
            });
        });
    }
}

function beforeClick(treeId, treeNode) {
    if (treeNode.level == 0 ) {
        var zTree = $.fn.zTree.getZTreeObj("authTree");
        zTree.expandNode(treeNode);
        return false;
    }
    return true;
}

function clickNoteMethod(event,treeId,treeNode,clickFlag){
    var clickTree = $.fn.zTree.getZTreeObj("authTree");
    $("#authId").val(treeNode.id);
    $("#authName").val(treeNode.name);
    $("#authCode").val(treeNode.code);
    $("#authPid").val(treeNode.pId);
    var parentNode = clickTree.getNodeByTId(treeNode.parentTId);
    $("#authPName").val(parentNode.name);
    $("#authPriority").val(treeNode.authPriority);
    if(treeNode.authIsOption == 1){
        $("#authIsOption").checkbox('set checked');
    }else{
        $("#authIsOption").checkbox('set unchecked');
    }
    $("#authIcon").val(treeNode.authIcon);
    $("#authUrl").val(treeNode.authUrl);
    $("#authRemark").val(treeNode.authRemark);
    initServerError();
}

function initAuthTree(treeNode){
    $("#authTreeDimmer").dimmer('show');
    $.ajax({
        type : 'GET',
        dataType : 'json',
        data : {},
        url: '/auth/findListForTree',
        async: true,
        success: function (data) {
            // console.info(data);
            zNodes = data;
            var treeObj = $("#authTree");
            $.fn.zTree.init(treeObj, setting, zNodes);
            zTree_Menu = $.fn.zTree.getZTreeObj("authTree");
            if (!curMenu){
                curMenu = zTree_Menu.getNodes()[0].children[0].children[0];
            }else{
                curMenu = treeNode;
            }
            zTree_Menu.selectNode(curMenu);

            $("#authId").val(curMenu.id);
            $("#authName").val(curMenu.name);
            $("#authCode").val(curMenu.code);
            $("#authPid").val(curMenu.pId);
            $("#authPName").val(zTree_Menu.getNodes()[0].children[0].name);
            $("#authPriority").val(curMenu.authPriority);
            if(curMenu.authIsOption == 1){
                $("#authIsOption").checkbox('set checked');
            }else{
                $("#authIsOption").checkbox('set unchecked');
            }
            $("#authIcon").val(curMenu.authIcon);
            $("#authUrl").val(curMenu.authUrl);
            $("#authRemark").val(curMenu.authRemark);

            treeObj.hover(function () {
                if (!treeObj.hasClass("showIcon")) {
                    treeObj.addClass("showIcon");
                }
            }, function() {
                treeObj.removeClass("showIcon");
            });
        }
    });
    $("#authTreeDimmer").dimmer('hide');
}


function initServerError(){
    $('#errorMsg > li').remove();
    $('#errorMsg').parent().addClass('hidden');
}

$(function(){
    //点击关闭错误提示信息
    $('.message .close')
        .on('click', function () {
            $(this)
                .closest('.message')
                .transition('fade')
            ;
        })
    ;
    //加载权限树
    initAuthTree();
    //保存事件
    $('#saveAuthBtn').on('click', function () {
        $('#editAuthForm').form("validate form");
    });

    //JS校验
    $('#editAuthForm').form({
        inline: true,
        fields: {
            authName: {
                identifier: 'authName',
                rules: [
                    {
                        type: 'empty',
                        prompt: '权限名称不能为空'
                    },
                    {
                        type: 'maxLength[12]',
                        prompt: '权限名称不能大于12个字符'
                    }
                ]
            },
            authCode: {
                identifier: 'authCode',
                rules: [
                    {
                        type: 'empty',
                        prompt: '权限编码不能为空'
                    },
                    {
                        type : 'maxLength[32]',
                        prompt : '权限编码不能大于12个字符'
                    }
                ]
            },
            authPriority: {
                identifier: 'authPriority',
                rules: [
                    {
                        type: 'empty',
                        prompt: '请填写权限的权重值'
                    },
                    {
                        type : 'number',
                        prompt : '权重值必须为数字'
                    }
                ]
            }
        },
        onSuccess: function (event, fields) {
            $.ajax({
                type: 'POST',
                dataType: 'json',
                data: {
                    authId : $("#authId").val(),
                    authName : $("#authName").val(),
                    authCode : $("#authCode").val(),
                    authPid : $("#authPid").val(),
                    authPriority : $("#authPriority").val(),
                    authIsOption : $("#authIsOption").checkbox('is checked') == true ? 1 : 0,
                    authRemark : $("#authRemark").val(),
                    authIcon : $("#authIcon").val(),
                    authUrl : $("#authUrl").val(),
                    _csrf: $("input[name='_csrf']").val()
                },
                url: "/auth/save",
                success: function (data) {
                    if (data.success) {
                        layer.alert('保存成功', {
                            icon: 1
                        }, function (index) {
                            layer.close(index);
                            initAuthTree(zTree_Menu.getNodeByTId(zTree_Menu.getSelectedNodes()[0].parentTId));
                        });
                    } else {
                        $('#errorMsg').parent().removeClass('hidden');
                        $('#errorMsg').html(data.errorMsg);
                    }
                }
            });
        }
    });

});