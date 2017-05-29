layui.define(['element','laypage', 'layer', 'form', 'pagesize'], function (exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form(),
        laypage = layui.laypage;
    var laypageId = 'pageNav';
    urlId = document.URL.split('?')[1].split("=")[1];
    initilData(1, 8);
    //页数据初始化
    //currentIndex：当前也下标
    //pageSize：页容量（每页显示的条数）
    function initilData(currentIndex, pageSize) {
        var index = layer.load(1);
        //模拟数据加载
        setTimeout(function () {
        	data = new Array();
            switch(parseInt(urlId)){
                case 1:     //收入类别
                    data = getData("CategoryService?action=incomeType");
                    break;
                case 2:     //支出类别
                    data = getData("CategoryService?action=costType");
                    break;
                case 3:     //成员信息
                    data = getData("MemberService?action=find");
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
            //该模块是我定义的拓展laypage，增加设置页容量功能
            //laypageId:laypage对象的id同laypage({})里面的cont属性
            //pagesize当前页容量，用于显示当前页容量
            //callback用于设置pagesize确定按钮点击时的回掉函数，返回新的页容量
            layui.pagesize(laypageId, pageSize).callback(function (newPageSize) {
                //这里不能传当前页，因为改变页容量后，当前页很可能没有数据
                initilData(1, newPageSize);
            });
        }, 50);
    }

    //监听推荐CheckBox
    form.on('checkbox(recommend)', function (data) {
        var index = layer.load(1);
        setTimeout(function () {
            layer.close(index);
            layer.msg('操作成功');
        }, 300);
    });

    //监听添加信息
    $('#add').on('click', function () {
       // var index = layer.load(1);
        var con = '<div style="padding:10px;"><input type="text" class="layui-input" style="margin-bottom:5px;" name="url" placeholder="名称" /></div>';
        layer.open({
            type: 1,
            title: '添加信息',
            content: con,
            btn: ['确定', '取消'],
            area: ['350px', '250px'],
            yes: function (index, layero) {
                var success = false;
                var name = $(layero).find('input[name=url]').val();
                switch(parseInt(urlId)){
                case 1:     //收入类别
                    success = addData("CategoryService?action=add",name,1);
                    break;
                case 2:     //支出类别
                    success = addData("CategoryService?action=add",name,2);
                    break;
                case 3:     //成员信息
                    success = addData("MemberService?action=add",name,"");
                    break;            
                }
                layer.msg(success==true?'添加成功':'添加失败');
                initilData(1, 8);
                form.render();  //重新渲染   
                layer.close(index);
            },
            shade: false,
            maxmin: true
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
        var x=document.getElementsByName("myselect");
        for(i=0;i<x.length;i++){           
            x[i].checked=data.elem.checked;
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
                case 1: //收入类别
                case 2: //支出类别
                    success = delData("CategoryService?action=delete",id);
                    break;
                case 3:     //成员信息
                    success = delData("MemberService?action=delete",id);
                    break;            
                }
                layer.msg(success==true?'删除成功':'删除失败');
                form.render();  //重新渲染   
                initilData(1, 8);
            }, function () {
                layer.msg('取消');
            });
        },
        editData: function (id, layero) {
            var con = '<div style="padding:10px;"><input type="text" class="layui-input" style="margin-bottom:5px;" name="url" value="' + $("td#"+id).text() + '" /></div>';
            layer.open({
                type: 1,
                title: '修改信息',
                content: con,
                btn: ['确定', '取消'],
                area: ['350px', '250px'],
                yes: function (index, layero) {
                    var success = false;
                    var name = $(layero).find('input[name=url]').val();
                    switch(parseInt(urlId)){
                    case 1: //收入类别
                    case 2: //支出类别
                        success = editData("CategoryService?action=update",id,name);
                        break;
                    case 3:     //成员信息
                        success = editData("MemberService?action=update",id,name);
                        break;            
                    }
                    layer.msg(success==true?'修改成功':'修改失败');
                    form.render();  //重新渲染   
                    layer.close(index);
                    initilData(1, 8);           
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
        html += "<td id="+ item.id + ">" + item.name + "</td>";
        html += '<td><form class="layui-form"><input type="checkbox" value="' + item.id + '" lay-filter="recommend" lay-skin="switch" checked /></form></td>';
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
        list.push({ id: datalist[i].id, name: datalist[i].name});
    }
    return  list;
}

function getSearch(addr, key){
    var list = new Array();
    var html = $.ajax({
       type: "GET",
       url: addr,
       data: "name=" + key,
       async: false
    }).responseText;
    var datalist = JSON.parse(html);
    for(i = 0; i < datalist.length; i++){
        list.push({ id: datalist[i].id, name: datalist[i].name});
    }
    return  list;
}

function Search(){
    var searchResult = new Array();
    var key = $('#keyword').val();
    switch(parseInt(urlId)){
    case 1:     //收入类别
        searchResult = getSearch("test.jsp", key);
        break;
    case 2:     //支出类别
        searchResult = getSearch(addr, key);
        break;
    case 3:     //成员信息
        searchResult = getSearch(addr, key);
        break;            
    }

    $("tbody").empty();
    var html = '';  //由于静态页面，所以只能作字符串拼接，实际使用一般是ajax请求服务器数据
    html = exportHTML(searchResult);
    $('#dataContent').html(html);
}

function addData(addr,name,type){
    var success = false;
    var html = $.ajax({
       type: "GET",
       url: addr,
       data: "name=" + name + "&type=" + type,
       async: false
    }).responseText;
    var datalist = JSON.parse(html);
    if(datalist[0].success=="true"){
        success = true;
    }
    return  success;
}

function delData(addr,id){
    var success = false;
    var html = $.ajax({
       type: "GET",
       url: addr,
       data: "id=" + id,
       async: false
    }).responseText;
    var datalist = JSON.parse(html);
    if(datalist[0].success=="true"){
        success = true;
    }
    return  success;
}

function editData(addr,id,name){
    var success = false;
    var html = $.ajax({
       type: "GET",
       url: addr,
       data: "id=" + id + "&name=" + name,
       async: false
    }).responseText;
    var datalist = JSON.parse(html);
    if(datalist[0].success=="true"){
        success = true;
    }
    return  success;
}