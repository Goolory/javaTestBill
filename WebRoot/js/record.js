layui.define(['laypage', 'layer', 'form', 'pagesize'], function (exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form(),
        laypage = layui.laypage;
    var laypageId = 'pageNav';
    var index = layer.load(1);

    initilData(1, 8);
    function initilData(currentIndex, pageSize) {
    	var index = layer.load(1);
        //模拟数据加载
        setTimeout(function () {
        	data = new Array();
            urlId = document.URL.split('?')[1].split("=")[1];
            switch(parseInt(urlId)){
                case 1:     //收入记录
                	data = getData("IncomeService?action=find");
                    break;
                case 2:     //支出记录
                	data = getData("CostService?action=find");
                    break;        
            }
            layer.close(index);
            //计算总页数（一般由后台返回）
            pages = Math.ceil(data.length / pageSize);
            //模拟数据分页（实际上获取的数据已经经过分页）
            var skip = pageSize * (currentIndex - 1);
            var take = skip + Number(pageSize);
            data = data.slice(skip, take);
            var html = '';  //由于静态页面，所以只能作字符串拼接，实际使用一般是ajax请求服务器数据
            html = exportHTML(data);
            html += '<div id="' + laypageId + '"></div>';
            $('#dataContent').html(html);

            form.render('checkbox');  //重新渲染CheckBox，编辑和添加的时候
            $('#dataConsole,#dataList').attr('style', 'display:block'); //显示FiledBox
            laypage({
                cont: laypageId,
                pages: pages,
                groups: 8,
                skip: true,
                curr: currentIndex,
                jump: function (obj, first) {
                    var currentIndex = obj.curr;
                    if (!first) {
                        initilData(currentIndex, pageSize);
                    }
                }
            });
            layui.pagesize(laypageId, pageSize).callback(function (newPageSize) {
                //这里不能传当前页，因为改变页容量后，当前页很可能没有数据
                initilData(1, newPageSize);
            });
        }, 50);
    }

    //监听添加信息
    $('#add').on('click', function () {
        layer.open({
            type: 2,
            title: '添加信息',
            content: ['add.html?id=' + urlId, 'no'],
            btn: ['确定', '取消'],
            area: ['600px', '500px'],
            yes: function (index, layero) { 
                var body = layer.getChildFrame('body', index);

                var success = false;
                var cate = body.find("select#cate").val();
                var member = body.find("select#member").val();
                var sum = body.find("input#number").val();
                var date = body.find("input#date").val();
                switch(parseInt(urlId)){
                case 1:     //收入类别
                    success = addData("IncomeService?action=add",cate,member,sum,date);
                    break;
                case 2:     //支出类别
                    success = addData("CostService?action=add",cate,member,sum,date);
                    break;        
                }
                layer.msg(success==true?'添加成功':'添加失败');
                form.render();  //重新渲染   
                layer.close(index);
                initilData(1, 8); 
            },
        });
    });

    //监听删除
    $('#del').on('click', function () {
        var x=document.getElementsByName("all");
        if(x[0].checked){
            $("tbody").empty();
            form.render();  //重新渲染   
        };
        var y=document.getElementsByName("myselect");
        for(i=0;i < y.length;i++){
            if(y[i].checked){
                $("tbody>tr").eq(i).remove();
            }       
        };
        layer.msg('删除');
    });

    //监听全选
    form.on('checkbox(select_all)', function (data) {
        var x = document.getElementsByName("myselect");
        for(i = 0; i < x.length; i++){           
            x[i].checked = data.elem.checked;
        };
    });

    //输出接口，主要是两个函数，一个删除一个编辑
    var datalist = {
        deleteData: function (id) {
            layer.confirm('确定删除？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                var success = false;
                switch(parseInt(urlId)){
                case 1:     //收入记录
                    success = delData("IncomeService?action=delete",id);
                    break;
                case 2:     //支出记录
                    success = delData("CostService?action=delete",id);
                    break;           
                }
                layer.msg(success==true?'删除成功':'删除失败');
                form.render();  //重新渲染   
                layer.close(index);
                initilData(1, 8);
            }, function () {
                layer.msg('取消');
            });
        },
        editData: function (id, layero) {
        	var html = $.ajax({
                type: "GET",
                url:  "RecordService?action=findId",
                data: "id=" + id,
                async: false
            }).responseText;
            var datalist = JSON.parse(html);
            var member_id = datalist[0].member_id;
            var category_id = datalist[0].category_id;
            var ids = [];

            $.cookie('url_id', urlId); 
            $.cookie('member_id', member_id); 
            $.cookie('category_id', category_id);
            
            layer.open({
                type: 2,
                title: '修改信息',
                content: ['add.html?id=0', 'no'],
                btn: ['确定', '取消'],
                area: ['600px', '500px'],
                yes: function (index, layero) { 
                    var body = layer.getChildFrame('body', index);
                    console.log(body);
                    var success = false;
                    var cate = body.find("select#cate").val();
                    var member = body.find("select#member").val();
                    var sum = body.find("input#number").val();
                    var date = body.find("input#date").val();
                    if(cate=="请选择"){
                    	cate = category_id;
                    }
                    if(member=="请选择"){
                    	member = member_id;
                    }
                    console.log(cate);
                    console.log(member);
                    console.log(sum);
                    console.log(date);
                    switch(parseInt(urlId)){
                    case 1:     //收入类别
                        success = editData("IncomeService?action=update",id,cate,member,sum,date);
                        break;
                    case 2:     //支出类别
                        success = editData("CostService?action=update",id,cate,member,sum,date);
                        break;          
                    }
                    layer.msg(success==true?'修改成功':'修改失败');
                    form.render();  //重新渲染   
                    layer.close(index);
                    initilData(1, 8);  
                },success: function(layero, index){
                    var body = layer.getChildFrame('body', index);
                    body.find("select#cate").val('理财');
                    body.find("select#member").val($("td#"+id).next().text());
                    body.find("input#number").val($("td#"+id).next().next().text().substr(1));
                    body.find("input#date").val($("td#"+id).next().next().next().text());
                }
            });
        }
    };
    exports('datalist', datalist);
});

function exportHTML(data){
    var html = '';  //由于静态页面，所以只能作字符串拼接，实际使用一般是ajax请求服务器数据
    //遍历集合
    for (var i = 0; i < data.length; i++) {
        var item = data[i];
        html += '<tr>';
        html += '<td><input type="checkbox" name="myselect"></td>'
        html += "<td id="+ item.id + ">" + item.category + "</td>";
        html += "<td>" + item.member + "</td>";
        html += "<td>" + "￥" + item.sum + "</td>";
        html += "<td>" + item.date + "</td>";
        html += '<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.datalist.editData(\'' + item.id + '\')"><i class="layui-icon">&#xe642;</i></button></td>';
        html += '<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.datalist.deleteData(\'' + item.id + '\')"><i class="layui-icon">&#xe640;</i></button></td>';
        html += "</tr>";
    }
    return html;
}

function getData(addr){
    var list = new Array();
    var html = $.ajax({
       type: "GET",
       url:  addr,
       async: false
    }).responseText;
    
    var datalist = JSON.parse(html);
    for(i = 0; i < datalist.length; i++){
        list.push({ id: datalist[i].id, category: datalist[i].category, member: datalist[i].member , sum: datalist[i].sum, date: datalist[i].date});
    }
    return  list;
}

function getSearch(addr, id, key){
    var list = new Array();
    var html = $.ajax({
       type: "GET",
       url: addr,
       data: "id=" + id + "&note=" + key,
       async: false
    }).responseText;
    var datalist = JSON.parse(html);
    for(i = 0; i < datalist.length; i++){
        list.push({ id: datalist[i].id, category: datalist[i].category, member: datalist[i].member , sum: datalist[i].num, date: datalist[i].date});
    }
    return  list;
}

function Search(){
    var searchResult = new Array();
    var key = $('#keyword').val();
    var id = $('#cate').val();
    switch(parseInt(urlId)){
    case 1:     //收入类别
        searchResult = getSearch("IncomeService?action=select", id, key);
        break;
    case 2:     //支出类别
        searchResult = getSearch("CostService?action=select", id, key);
        break;         
    }

    $("tbody").empty();
    var html = '';  //由于静态页面，所以只能作字符串拼接，实际使用一般是ajax请求服务器数据
    html = exportHTML(searchResult);
    $('#dataContent').html(html);
}

function addData(addr,cate,member,sum,date){
    var success = false;
    var html = $.ajax({
        url: addr,
        data: "category_id=" + cate + "&member_id=" + member + "&number=" + sum + "&date=" + date,
        type:"POST",      
        dataType:"json",
        data:{
       		category_id:cate,
       		member_id:member,
       		number:sum,
       		date:date
       	},
       async: false
    }).responseText;
    var datalist = JSON.parse(html);
    if(datalist[0].success){
        success = true;
    }
    return  success;
}

function delData(addr,id){
    var success = false;
    var html = $.ajax({
       type: "POST",
       url: addr,
       data: "id=" + id,
       async: false
    }).responseText;
    var datalist = JSON.parse(html);
    if(datalist[0].success){
        success = true;
    }
    return  success;
}

function editData(addr,id,cate,member,sum,date){
    var success = false;
    var html = $.ajax({
       type: "POST",
       url: addr,
       data: "id=" + id + "&category_id=" + cate + "&member_id=" + member + "&number=" + sum + "&date=" + date,
       async: false
    }).responseText;
    var datalist = JSON.parse(html);
    if(datalist[0].success){
        success = true;
    }
    return  success;
}