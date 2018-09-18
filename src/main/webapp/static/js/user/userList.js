$(function () {
    var userDateRangeBeginStr = '';
    var userDateRangeEndStr = '';

    //重置高级查询
    $(".refreshing.icon.link").on("click", function () {
        $("#searchUserName").val('');
        $("#searchUserRealName").val('');
        $("#searchUserDept").val('');
        $("#searchUserId").val('');
        $("#searchUserEnable").dropdown('set value', '-1');
        $("#searchUserCreateDate").val('');
        userDateRangeBeginStr = '';
        userDateRangeEndStr = '';
    });


    $('.ui.modal').modal({
        closable: false,
        onHidden: function () {
            $('#errorMsg > li').remove();
            $('#errorMsg').parent().addClass('hidden');
            $('#editForm').form('reset');
            // window.location.reload();
        }
    });

    var vm = new Vue({
        el: "#userList",
        data: {
            userList: []
        },
        methods: {
            editUser: function (userName) {
                $('.ui.modal.user').modal('show');
                if (userName) {
                    initUser(userName);
                } else {
                    $('#userId').val(0);
                    $('#userName').removeAttr('readonly');
                }
            },
            deleteUser: function (userId) {
                delUser(userId);
            },
            alloRole: function (userId) {
                $(".ui.modal.role").modal('show', {
                    onShow: initRolesByUserId(userId)
                });
            },
            disabledUser: function (userId, userEnable) {
                layer.confirm(userEnable == 0 ? "您确定要启用该用户？" : "您确定要禁用该用户", {
                    btn: ['确定', '取消'],
                    yes: function (index) {
                        $.ajax({
                            type: 'POST',
                            dataType: 'json',
                            data: {
                                userId: userId,
                                userEnable: userEnable,
                                _csrf: $("input[name='_csrf']").val()
                            },
                            url: '/user/updateUserStatus',
                            success: function (data) {
                                if (data.success) {
                                    layer.close(index);
                                    layer.alert(userEnable == 0 ? "启用成功" : "禁用成功", {icon: 1}, function (index) {
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
     * 删除用户
     * @param userId
     */
    function delUser(userId) {
        layer.confirm("您确定要删除该用户？", {
            title : '删除操作，请谨慎选择',
            icon : 2,
            btn: ['确定', '取消'],
            yes: function (index) {
                $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        userId: userId,
                        _csrf: $("input[name='_csrf']").val()
                    },
                    url: '/user/deleteUser',
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

    /**
     * 根据用户ID加载该用户的所有角色
     * @param userId
     */
    function initRolesByUserId(userId) {
        $("#alloUserId").val(userId);
        $.ajax({
            type: 'GET',
            dataType: 'json',
            data: {
                userId: userId
            },
            url: '/user/initRolesByUserId',
            success: function (data) {
                if (data) {
                    var selectedValue = [];
                    $.each(data, function (i, n) {
                        selectedValue.push(data[i].roleId);
                    });
                    $(".multiple.dropdown").dropdown('set exactly', selectedValue.join().split(','));
                }
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
            keyType: $('#keyType').val(),
            userName: $('#searchUserName').val(),
            userRealName: $('#searchUserRealName').val(),
            userDeptName: $('#searchUserDept').val(),
            userEnable: $('#searchUserEnable').val(),
            userDateRangeBegin: userDateRangeBeginStr,
            userDateRangeEnd: userDateRangeEndStr,
            page: activePage - 1,
            pageSize: 5
        };
        loadListDataByQuery(params);
        $('#gridDimmer').dimmer('hide');
    }

    /**
     * 加载用户信息
     * @param userId
     */
    function initUser(userName) {
        $('#editDimmer').dimmer('show');
        $.ajax({
            type: 'GET',
            dataType: 'json',
            data: {
                userName: userName
            },
            url: '/user/initUser',
            success: function (data) {
                $('#userName').val(data.userName);
                $('#userId').val(data.userId);
                $('#userRealName').val(data.userRealName);
                $('#userDept').val(data.userDept);
                $('#userDeptName').val(data.sysDeptEntity.deptName);
            }
        });
        $('#editDimmer').dimmer('hide');
        $('#userName').attr('readonly', true);
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
                pageSize: 5
            },
            url: '/user/findList',
            success: function (data) {
                if (data.content.length > 0) {
                    initPager(data.totalPages);
                } else {
                    vm.userList = null;
                }
            }
        });
    }


    $('#addUserBtn').on('click', function () {
        // $('.ui.modal').modal('show');
        vm.editUser();
    });


    laydate.render({
        elem: '#searchUserCreateDate',
        range: true,
        theme: 'molv',
        // change: function (value, date, endDate) {
        //     var userDateRanges = value.split(' - ');
        //     userDateRangeBeginStr = userDateRanges[0];
        //     userDateRangeEndStr = userDateRanges[1];
        //     // console.log(value); //得到日期生成的值，如：2017-08-18
        //     // console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
        //     // console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
        // },
        done: function (value, date, endDate) {
            userDateRangeBeginStr = '';
            userDateRangeEndStr = '';
            var userDateRanges = value.split(' - ');
            userDateRangeBeginStr = userDateRanges[0];
            userDateRangeEndStr = userDateRanges[1];
        }
    });

    //高级查询
    $('#queryBySuperBtn').on('click', function () {
        initListByCondition();
    });
    //关键字查询
    $('#queryByKeyBtn').on('click', function () {
        initListByCondition();
    });
    $('#exportUserForExcel').on('click', function () {
        $(this).addClass('loading');
        window.open("/user/exportExcel?key=" + $('#keyWorld').val() + "&keyType=" + $('#keyType').val() + "&userName="
            + $('#searchUserName').val() + "&userRealName=" + $('#searchUserRealName').val() + "&userDeptName=" + $('#searchUserDept').val()
            + "&userEnable=" + $('#searchUserEnable').val() + "&userDateRangeBegin=" + userDateRangeBeginStr
            + "&userDateRangeEnd=" + userDateRangeEndStr, "_blank");
        $(this).removeClass('loading');
    })

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
                        keyType: $('#keyType').val(),
                        userName: $('#searchUserName').val(),
                        userRealName: $('#searchUserRealName').val(),
                        userDeptName: $('#searchUserDept').val(),
                        userEnable: $('#searchUserEnable').val(),
                        userDateRangeBegin: userDateRangeBeginStr,
                        userDateRangeEnd: userDateRangeEndStr,
                        page: page - 1,
                        pageSize: 5
                    },
                    url: '/user/findListBySuper',
                    async: true,
                    success: function (data) {
                        vm.userList = data.content;
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
            url: '/user/findListBySuper',
            success: function (data) {
                if (data.content.length > 0) {
                    vm.userList = data.content;
                    initPager(data.totalPages);
                } else {
                    vm.userList = null;
                    $("#pagePlugin").hide();
                }
            }
        });
    }

    $('#saveUserBtn').on('click', function () {
        $('#editForm').form("validate form");
    });
    $('.message .close')
        .on('click', function () {
            $(this)
                .closest('.message')
                .transition('fade')
            ;
        })
    ;


    $('#editForm').form({
        inline: true,
        fields: {
            userName: {
                identifier: 'userName',
                rules: [
                    {
                        type: 'empty',
                        prompt: '请输入用户名，用户名不能为空'
                    },
                    {
                        type: 'minLength[3]',
                        prompt: '用户名不能小于3个字符'
                    },
                    {
                        type: 'maxLength[12]',
                        prompt: '用户名不能大于12个字符'
                    },
                ]
            },
            userRealName: {
                identifier: 'userRealName',
                rules: [
                    {
                        type: 'empty',
                        prompt: '请输入真实姓名，真实姓名不能为空'
                    },
                    {
                        type: 'maxLength[12]',
                        prompt: '真实姓名不能大于12个字符'
                    },
                ]
            },
            userDept: {
                identifier: 'userDept',
                rules: [
                    {
                        type: 'empty',
                        prompt: '请选择所属部门，部门不能为空'
                    }
                ]
            }
        },
        onSuccess: function (event, fields) {
            $.ajax({
                type: 'POST',
                dataType: 'json',
                data: $('#editForm').serialize(),
                url: "/user/add",
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

    initRoles();

    //分配用户角色
    $("#saveUserRoleBtn").on("click", function () {
        var roleIds = $(".multiple.dropdown").dropdown('get value');
        if (roleIds) {
            $.ajax({
                type: 'POST',
                dataType: 'json',
                data: {
                    userId: $("#alloUserId").val(),
                    roleIds: roleIds,
                    _csrf: $("input[name='_csrf']").val()
                },
                url: "/user/alloRoles",
                success: function (data) {
                    if (data.success) {
                        $('.ui.modal.role').modal('hide');
                        layer.alert('保存成功', {
                            icon: 1
                        }, function (index) {
                            layer.close(index);
                            initListByCondition();
                        });
                    } else {
                        layer.alert(data.errorMsg, {
                            icon: 2
                        }, function (index) {
                            layer.close(index);
                        });
                    }
                }
            });
        } else {
            layer.alert('请选择角色', {
                icon: 2
            }, function (index) {
                layer.close(index);
            });
        }
    });

});

/**
 * 初始化角色选择项
 */
function initRoles() {
    $.ajax({
        type: 'GET',
        dataType: 'json',
        data: {},
        url: "/user/initRoles",
        success: function (data) {
            if (data) {
                var appendStr = "";
                $.each(data, function (i, n) {
                    appendStr += "<div class='item' data-value='" + data[i].roleId + "'>" + data[i].roleName + "</div>";
                });
                $(".multiple.dropdown > .menu").append(appendStr);
            }
        }
    });
    $(".multiple.dropdown").dropdown();
}


