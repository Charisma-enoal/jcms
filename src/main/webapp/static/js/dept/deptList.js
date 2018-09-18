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

//模拟数据
var zNodes = null;


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

    var sObj = $("#" + treeNode.tId + "_a");
    var addStr = "<a class='addStr'><i class='icon add circle green' id='addBtn_" + treeNode.tId
        + "' title='新增子部门'></i></a>";
    var removeStr = "<a class='addStr'><i class='icon remove circle red' id='delBtn_" + treeNode.tId
        + "' title='删除当前部门'></i></a>";
    sObj.after(addStr + removeStr);

    var addBtn = $("#addBtn_" + treeNode.tId);
    var removeBtn = $("#delBtn_" + treeNode.tId);

    if (addBtn){
        addBtn.bind("click",function(){
            $("#deptId").val(0);
            $("#deptName").val('');
            $("#deptCode").val(treeNode.code);
            $("#deptPid").val(treeNode.id);
            $("#showDeptName").val(treeNode.name);
            $("#deptRemark").val('');
            initServerError();
        });
    }

    if (removeBtn){
        removeBtn.bind("click",function(){
            layer.confirm("您确定要删除该部门？", {
                btn: ['确定', '取消'],
                yes: function (index) {
                    $.ajax({
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            deptId: treeNode.id,
                            _csrf: $("input[name='_csrf']").val()
                        },
                        url: '/dept/deleteDept',
                        success: function (data) {
                            if (data.success) {
                                layer.close(index);
                                layer.alert('删除成功', {icon: 1}, function (index) {
                                    layer.close(index);
                                    initDeptTree(zTree_Menu.getNodeByTId(treeNode.tId));
                                });
                            }else{
                                layer.close(index);
                                layer.alert(data.errorMsg, {icon: 2}, function (index) {
                                    layer.close(index);
                                    initDeptTree(zTree_Menu.getNodeByTId(treeNode.tId));
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
        var zTree = $.fn.zTree.getZTreeObj("deptTree");
        zTree.expandNode(treeNode);
        return false;
    }
    return true;
}

function clickNoteMethod(event,treeId,treeNode,clickFlag){
    var clickTree = $.fn.zTree.getZTreeObj("deptTree");
    $("#deptId").val(treeNode.id);
    $("#deptName").val(treeNode.name);
    $("#deptCode").val(treeNode.code);
    $("#deptPid").val(treeNode.pId);
    var parentNode = clickTree.getNodeByTId(treeNode.parentTId);
    $("#showDeptName").val(parentNode.name);
    if(treeNode.deptEnable == 1){
        $("#deptEnable").checkbox('set checked');
    }else{
        $("#deptEnable").checkbox('set unchecked');
    }

    $("#deptRemark").val(treeNode.deptRemark);
    initServerError();
}

function initDeptTree(treeNode){
    $("#deptTreeDimmer").dimmer('show');
    $.ajax({
        type : 'GET',
        dataType : 'json',
        data : {},
        url: '/dept/findListForTree',
        async: true,
        success: function (data) {
            // console.info(data);
            zNodes = data;
            var treeObj = $("#deptTree");
            $.fn.zTree.init(treeObj, setting, zNodes);
            zTree_Menu = $.fn.zTree.getZTreeObj("deptTree");
            if (!curMenu){
                curMenu = zTree_Menu.getNodes()[0].children[0].children[0];
            }else{
                curMenu = treeNode;
            }
            zTree_Menu.selectNode(curMenu);

            $("#deptId").val(curMenu.id);
            $("#deptName").val(curMenu.name);
            $("#deptCode").val(curMenu.code);
            $("#deptPid").val(curMenu.pId);
            $("#showDeptName").val(zTree_Menu.getNodes()[0].children[0].name);
            if(curMenu.deptEnable == 1){
                $("#deptEnable").checkbox('set checked');
            }else{
                $("#deptEnable").checkbox('set unchecked');
            }

            $("#deptRemark").val(curMenu.deptRemark);

            treeObj.hover(function () {
                if (!treeObj.hasClass("showIcon")) {
                    treeObj.addClass("showIcon");
                }
            }, function() {
                treeObj.removeClass("showIcon");
            });
        }
    });
    $("#deptTreeDimmer").dimmer('hide');
}


$(function(){
    initDeptTree();
    $('#saveDeptBtn').on('click', function () {
        $('#editDeptForm').form("validate form");
    });
    $('.message .close')
        .on('click', function () {
            $(this)
                .closest('.message')
                .transition('fade')
            ;
        })
    ;
    $('#editDeptForm').form({
        inline: true,
        fields: {
            deptName: {
                identifier: 'deptName',
                rules: [
                    {
                        type: 'empty',
                        prompt: '部门名称不能为空'
                    },
                    {
                        type: 'maxLength[12]',
                        prompt: '部门名称不能大于12个字符'
                    },
                ]
            },
            deptCode: {
                identifier: 'deptCode',
                rules: [
                    {
                        type: 'empty',
                        prompt: '部门编码不能为空'
                    }
                ]
            },
            showDeptName: {
                identifier: 'showDeptName',
                rules: [
                    {
                        type: 'empty',
                        prompt: '请选择上级部门'
                    }
                ]
            }
        },
        onSuccess: function (event, fields) {
            $.ajax({
                type: 'POST',
                dataType: 'json',
                data: {
                    deptId : $("#deptId").val(),
                    deptName : $("#deptName").val(),
                    deptCode : $("#deptCode").val(),
                    deptPid : $("#deptPid").val(),
                    deptEnable : $("#deptEnable").checkbox('is checked') == true ? 1 : 0,
                    deptRemark : $("#deptRemark").val(),
                    _csrf: $("input[name='_csrf']").val()
                },
                url: "/dept/save",
                success: function (data) {
                    if (data.success) {
                        layer.alert('保存成功', {
                            icon: 1
                        }, function (index) {
                            layer.close(index);
                            initDeptTree(zTree_Menu.getNodeByTId(zTree_Menu.getSelectedNodes()[0].parentTId));
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


function initServerError(){
    $('#errorMsg > li').remove();
    $('#errorMsg').parent().addClass('hidden');
}



