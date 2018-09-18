$(function(){
    $('.ui.search').search({
        type: 'standard',
        apiSettings : {
            onResponse : function(deptResponse){
                var response = {
                    results : new Array()
                }
                $.each(deptResponse,function(i,item){
                    response.results.push({
                       title : item.deptName, description : item.deptCode,id : item.deptId
                    });
                });
                return response;
            },
            url : '/dept/searchDept?key={query}'
        },
        maxResults : 5,
        onSelect : function(result, response){
            $(this).find("input[type='hidden']").val(result.id);
        },
        error : {
            noResults : '没有查询到结果'
        }
    });


});

