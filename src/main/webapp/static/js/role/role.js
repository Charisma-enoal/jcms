$(function () {
    //关键字查询按钮事件
    $('#queryByKeyBtn').on('click', function () {
        initListByCondition();
    });
    //新增按钮事件
    $('#addRoleBtn').on('click', function () {
        $('.ui.mini.modal.role').modal('show');
    });

    //保存按钮事件
    $('#saveRoleBtn').on('click', function () {
        $('#editForm').form("validate form");
    });

    //保存权限
    $('#saveAuthBtn').on('click', function () {
        var treeObj = $.fn.zTree.getZTreeObj("authTree");
        //获取到所有的节点
        var allNodes = treeObj.transformToArray(treeObj.getNodes());
        //定义数组，保存未修改之前该角色所有的权限ID
        var notUpdateAuthIds = new Array();
        var notUpdateRaIds = new Array();
        //循环节点判断节点的raID是不是0，如果不是0代表了这个角色之前包含这个权限
        for (var a = 0; a < allNodes.length; a++){
            if (allNodes[a].raId != 0){
                notUpdateAuthIds.push(allNodes[a].id);
                notUpdateRaIds.push(allNodes[a].raId);
            }
        }
        //获取到所有选中的节点
        var checkNodes = treeObj.getCheckedNodes();
        if (checkNodes.length > 0){
            //定义数组，保存选中的所有的权限的ID
            var checkIdArry = new Array();
            for(var i = 0; i < checkNodes.length; i++){
                checkIdArry.push(checkNodes[i].id);
            }

            //双重循环，找出需要删除的权限
            var deleteAuthIds = new Array();
            for (var j = 0; j < notUpdateAuthIds.length; j++){
                var flag = false;
                for (var k = 0; k < checkIdArry.length; k++){
                    if(notUpdateAuthIds[j] == checkIdArry[k]){
                        flag = true;
                    }
                }
                if (!flag){
                    deleteAuthIds.push(notUpdateRaIds[j]);
                }
            }
            var deleteIdStr = deleteAuthIds.join(',');
            var checkIdStr = checkIdArry.join(',');
            $.ajax({
                type: 'POST',
                dataType: 'json',
                data: {
                    roleId : vm.authRoleId,
                    authIdStr : checkIdStr,
                    deleteIdStr : deleteIdStr,
                    _csrf: $("input[name='_csrf']").val()
                },
                url: '/role/saveAuth',
                async: true,
                success: function (data) {
                    if (data.success){
                        layer.alert('保存成功', {
                            icon: 1
                        }, function (index) {
                            layer.close(index);
                            $('.ui.modal').modal('hide');
                            initListByCondition();
                        });
                    }else{
                        $('#errorMsgForAuth').parent().removeClass('hidden');
                        $('#errorMsgForAuth').html(data.errorMsg);
                    }
                }
            });
        }else{
            layer.alert('请选择您要分配的权限', {
                icon: 2
            }, function (index) {
                layer.close(index);
            });
        }
    });

    //模态框关闭时，将错误提示列表清空
    $('.ui.modal').modal({
        closable: false,
        onHidden: function () {
            $('#errorMsg > li').remove();
            $('#errorMsg').parent().addClass('hidden');
            $('#editForm').form('reset');
        }
    });

    //错误提示框关闭事件
    $('.message .close')
        .on('click', function () {
            $(this)
                .closest('.message')
                .transition('fade')
            ;
        })
    ;

    //角色列表VUE对象
    var vm = new Vue({
        el: "#roleList",
        data: {
            roleList: [],
            authRoleId: ""
        },
        methods: {
            //分配权限
            alloAuth: function (roleId) {
                $('.ui.mini.modal.auth').modal('show', {
                    onShow: initAuthTree(roleId)
                });
            },
            //禁用角色
            lockRole: function (roleId) {
                lockOrEnableRole(1, roleId);
            },
            //启用角色
            enableRole: function (roleId) {
                lockOrEnableRole(0, roleId);
            },
            //删除角色
            deleteRole: function (roleId) {
                layer.confirm("角色删除后会将所有属于此角色的用户的角色清除，您确定要删除该角色？", {
                    btn: ['确定', '取消'],
                    yes: function (index) {
                        $.ajax({
                            type: 'POST',
                            dataType: 'json',
                            data: {
                                roleId: roleId,
                                _csrf: $("input[name='_csrf']").val()
                            },
                            url: '/role/deleteRole',
                            success: function (data) {
                                if (data.success) {
                                    layer.close(index);
                                    layer.alert('删除成功', {icon: 1}, function (index) {
                                        layer.close(index);
                                        initListByCondition();
                                    });
                                }
                            }
                        });
                    }
                });
            }
        }
    });

    /**
     * 禁用或启用角色
     * @param lockOrEnable
     */
    function lockOrEnableRole(lockOrEnable, roleId) {
        var cfStr = "";
        var alStr = "";
        if (lockOrEnable == 1) {
            cfStr = "您确定要禁用该角色？禁用后所有属于该角色的用户将不能享有该角色的权限";
            alStr = "禁用成功";
        } else {
            cfStr = "您确定要启用该角色？启用后所有属于该角色的用户将持有当前角色的权限";
            alStr = "启用成功";
        }
        layer.confirm(cfStr, {
            btn: ['确定', '取消'],
            yes: function (index) {
                $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        roleId: roleId,
                        _csrf: $("input[name='_csrf']").val()
                    },
                    url: '/role/lockOrEnableRole',
                    success: function (data) {
                        if (data.success) {
                            layer.close(index);
                            layer.alert(alStr, {icon: 1}, function (index) {
                                layer.close(index);
                                initListByCondition();
                            });
                        }
                    }
                });
            }
        });
    }

    /**
     * 根据查询条件加载列表
     */
    function initListByCondition() {
        $('#gridDimmer').dimmer('show');
        var activePage = $('#pagePlugin').children('.active').html();
        var params = {
            key: $('#keyWorld').val(),
            roleEnable: $('#searchRoleEnable').val(),
            page: activePage - 1,
            pageSize: 10
        };
        loadListDataByQuery(params);
        $('#gridDimmer').dimmer('hide');
    }


    /**
     * 默认加载所有数据
     */
    loadAllData();

    function loadAllData() {
        $.ajax({
            type: 'GET',
            dataType: 'json',
            data: {
                page: 0,
                pageSize: 10
            },
            url: '/role/findList',
            success: function (data) {
                if (data.content.length > 0) {
                    initPager(data.totalPages);
                } else {
                    vm.roleList = null;
                }
            }
        });
    }

    //加载分页
    function initPager(totalPages) {
        $("#pagePlugin").show();
        //初始化分页组件
        $('#pagePlugin').twbsPagination({
            totalPages: totalPages,
            startPage: 1,
            //点击页数回调
            onPageClick: function (event, page) {
                $('#gridDimmer').dimmer('show');
                $.ajax({
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        key: $('#keyWorld').val(),
                        roleEnable: $('#searchRoleEnable').val(),
                        page: page - 1,
                        pageSize: 10
                    },
                    url: '/role/findList',
                    async: true,
                    success: function (data) {
                        vm.roleList = data.content;
                    }
                });
                $('#gridDimmer').dimmer('hide');
            }
        });
    }

    //查询
    function loadListDataByQuery(param) {
        $.ajax({
            type: 'GET',
            dataType: 'json',
            data: param,
            url: '/role/findList',
            success: function (data) {
                if (data.content.length > 0) {
                    vm.roleList = data.content;
                    initPager(data.totalPages);
                } else {
                    vm.roleList = null;
                    $("#pagePlugin").hide();
                }
            }
        });
    }

    //JS校验
    $('#editForm').form({
        inline: true,
        fields: {
            userName: {
                identifier: 'roleName',
                rules: [
                    {
                        type: 'empty',
                        prompt: '请输入角色名称，角色名称不能为空'
                    },
                    {
                        type: 'minLength[2]',
                        prompt: '角色名称不能小于2个字符'
                    },
                    {
                        type: 'maxLength[32]',
                        prompt: '角色名称不能大于32个字符'
                    },
                ]
            }
        },
        onSuccess: function (event, fields) {
            $.ajax({
                type: 'POST',
                dataType: 'json',
                data: {
                    roleName: $('#roleName').val(),
                    roleEnable: $('#roleEnable').checkbox('is checked') == true ? 1 : 0,
                    roleRemark: $('#roleRemark').val(),
                    _csrf: $("input[name='_csrf']").val()
                },
                url: "/role/add",
                success: function (data) {
                    console.info(data);
                    if (data.success) {
                        $('.ui.modal').modal('hide');
                        layer.alert('保存成功', {
                            icon: 1
                        }, function (index) {
                            layer.close(index);
                            initListByCondition();
                        });
                    } else {
                        $('#errorMsg').parent().removeClass('hidden');
                        $('#errorMsg').html(data.errorMsg);
                    }
                }
            });
        }
    });

    //ztree配置
    var setting = {
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "s", "N": "s" }
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    /**
     * 根据角色初始化权限树
     * @param roleId
     */
    function initAuthTree(roleId) {
        $("#authTreeDimmer").dimmer('show');
        vm.authRoleId = roleId;
        $.ajax({
            type: 'GET',
            dataType: 'json',
            data: {
                roleId: roleId
            },
            url: '/role/authTree',
            async: true,
            success: function (data) {
                var zNodes = data;
                $.fn.zTree.init($("#authTree"), setting, zNodes);
            }
        });
        $("#authTreeDimmer").dimmer('hide');
    }
});


