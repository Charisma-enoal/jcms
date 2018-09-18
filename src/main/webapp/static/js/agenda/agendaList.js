$(function () {
    /**
     * 起始时间
     * @type {string}
     */
    var agendaStart = "";
    /**
     * 结束时间
     * @type {string}
     */
    var agendaEnd = "";

    //是否全天选择事件
    $("#agendaAllDay").checkbox({
       onChange : function () {
           var isChecked = $('#agendaAllDay').checkbox('is checked');
           if (isChecked){
               $(this).parent('div').parent('div').next().hide();
           }else{
               $(this).parent('div').parent('div').next().show();
           }
       } 
    });

    //初始化日期组件
    laydate.render({
        elem: '#beginEndDateTime',
        range: true,
        theme: 'molv',
        type : 'datetime',
        done: function (value, date, endDate) {
            agendaStart = '';
            agendaEnd = '';
            var dateRanges = value.split(' - ');
            agendaStart = dateRanges[0];
            agendaEnd = dateRanges[1];
        }
    });

    //模态框关闭时，将错误提示列表清空
    $('.ui.modal.agenda').modal({
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

    /**
     * 初始化fullCalendar
     */
    initCalendar();

});

/**
 * 初始化日程组件
 */
function initCalendar() {
    var initialLocaleCode = "zh-cn";
    $('#agendaPanel').fullCalendar({
        // customButtons : {
        //   addAgendaBtn : {
        //       text : "新增日程"
        //   }
        // },
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay,listMonth'
        },
        defaultDate: new Date(),
        locale: initialLocaleCode,
        buttonIcons: false, // show the prev/next text
        weekNumbers: true,
        navLinks: true, // can click day/week names to navigate views
        editable: true,
        eventLimit: true, // allow "more" link when too many events
        buttonText: {
            prev: '<',
            next: '>',
            prevYear: '«', // «
            nextYear: '»' // »
        },
        events: function (start, end, timezone, callback) {
            $.ajax({
                type: 'GET',
                dataType: 'json',
                data: {},
                url: '/agenda/getAgendaListByUsername',
                success: function (data) {
                    if(data){
                        callback(data);
                    }
                },
                error : function () {
                    layer.alert("服务器请求错误，请联系系统管理员",{icon : 2});
                }
            });
        },
        eventClick : function (calEvent,jsEvent,view) {
            $(".modal.agenda").modal("show");
            var allDay = calEvent.allDay;
            if (allDay){
                $('#agendaAllDay').checkbox('check');
            }else{
                $('#agendaAllDay').checkbox('uncheck');
                var startDate = new Date(calEvent.start).format("yyyy-MM-dd hh:mm:ss");
                var endDate = new Date(calEvent.end).format("yyyy-MM-dd hh:mm:ss");
                var agendaDate = startDate + ' - ' + endDate;
                $("#beginEndDateTime").val(agendaDate);
            }
            $("#agendaTitle").val(calEvent.title);
            $("#agendaUrl").val(calEvent.url);
        }
        ,
        dayClick : function (date,jsEvent,view) {
        }
    });
}
