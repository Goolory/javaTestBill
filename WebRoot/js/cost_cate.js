/*

@Name：不落阁后台模板源码 
@Author：Absolutely 
@Site：http://www.lyblogs.cn

*/

layui.define(['laypage', 'layer', 'form', 'pagesize'], function (exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form(),
        laypage = layui.laypage;
    var laypageId = 'pageNav';

    initilData(1, 8);
    //页数据初始化
    //currentIndex：当前也下标
    //pageSize：页容量（每页显示的条数）
    function initilData(currentIndex, pageSize) {
        var index = layer.load(1);
        //模拟数据
        var data = new Array();
        var html = $.ajax({
            type: "GET",
            url: "CategoryService?action=costType",
            async: false
         }).responseText;
         var jsonobj = JSON.parse(html);
         for (var i = 0; i < jsonobj.length; i++) {
             data.push({ id: jsonobj[i].id,  name: jsonobj[i].name });
         }
       

        //模拟数据加载
        setTimeout(function () {
            layer.close(index);
            //计算总页数（一般由后台返回）
            pages = Math.ceil(data.length / pageSize);
            //模拟数据分页（实际上获取的数据已经经过分页）
            var skip = pageSize * (currentIndex - 1);
            var take = skip + Number(pageSize);
            data = data.slice(skip, take);
            var html = '';  //由于静态页面，所以只能作字符串拼接，实际使用一般是ajax请求服务器数据
            html += '<table style="" class="layui-table" lay-even>';
            html += '<colgroup><col width="90"><col width="180"><col width="90"><col width="90"><col width="50"><col width="50"></colgroup>';
            html += '<thead><tr><th><form class="layui-form"><input type="checkbox" lay-filter="select_all" title="全选"></form></th><th>名称</th><th>有效性</th><th colspan="2">操作</th></tr></thead>';
            html += '<tbody>';
            //遍历文章集合
            for (var i = 0; i < data.length; i++) {
                var item = data[i];
                html += "<tr>";
                html += '<td><input type="checkbox" name="myselect"></td>'
                html += "<td id="+ item.id + ">" + item.name + "</td>";
                html += '<td><form class="layui-form"><input type="checkbox" value="' + item.id + '" lay-filter="recommend" lay-skin="switch" checked /></form></td>';
                html += '<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.datalist.editData(\'' + item.id + '\')"><i class="layui-icon">&#xe642;</i></button></td>';
                html += '<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.datalist.deleteData(\'' + item.id + '\')"><i class="layui-icon">&#xe640;</i></button></td>';
                html += "</tr>";
            }
            html += '</tbody>';
            html += '</table>';
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
        }, 500);
    }

    //监听置顶CheckBox
    form.on('checkbox(top)', function (data) {
        var index = layer.load(1);
        setTimeout(function () {
            layer.close(index);
            if (data.elem.checked) {
                data.elem.checked = false;
            }
            else {
                data.elem.checked = true;
            }
            layer.msg('操作失败，返回原来状态');
            form.render();  //重新渲染
        }, 300);
    });

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
//            	layer.msg('操作成功');
                //这是核心的代码。
            	var name = $(layero).find('input[name=url]').val();
            	var html = $.ajax({
                    type: "GET",
                    url: "CategoryService?action=add&name="+name+"&type="+2,
                    async: false
                 }).responseText;
            	var jsonobj = JSON.parse(html);
            	if(jsonobj[0].success=="true"){
            		html = "<tr>";
                    html += '<td><input type="checkbox" name="myselect"></td>'
                    html += "<td id="+ 5 + ">" + $(layero).find('input[name=url]').val() + "</td>";
                    html += '<td><form class="layui-form"><input type="checkbox" value="' + 5 + '" lay-filter="recommend" lay-skin="switch" checked /></form></td>';
                    html += '<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.datalist.editData(\'' + 5 + '\')"><i class="layui-icon">&#xe642;</i></button></td>';
                    html += '<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.datalist.deleteData(\'' + 5 + '\')"><i class="layui-icon">&#xe640;</i></button></td>';
                    html += "</tr>";
                    $("td#1").parent().parent().append(html);
            	}
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
        layer.msg('删除');
        var x=document.getElementsByName("myselect");
        var cout = 0;
        for(i=0;i < x.length;i++){
            if(x[i-cout].checked){
        //        alert(i);
               $("tbody>tr").eq(i-cout).remove();
               cout++;
            }       
        };
    });

    //监听全选
    form.on('checkbox(select_all)', function (data) {
        var x=document.getElementsByName("myselect");
        for(i=0;i<x.length;i++){           
            if(data.elem.checked){
                x[i].checked=true;
            }else{
                x[i].checked=false;
            }
        };
    });

    //输出接口，主要是两个函数，一个删除一个编辑
    var datalist = {
        deleteData: function (id) {
            layer.confirm('确定删除？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
            	var html = $.ajax({
                    type: "GET",
                    url: "CategoryService?action=delete&id="+id,
                    async: false
                 }).responseText;
            	var jsonobj = JSON.parse(html);
            	if(jsonobj[0].success){
            		layer.msg('删除成功'); 
            		$("td#"+id).parent().remove();
            	}
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
                    $("td#"+id).text($(layero).find('input[name=url]').val());
                    
                    var html = $.ajax({
                        type: "GET",
                        url: "CategoryService?action=update&id="+id+"&name="+$(layero).find('input[name=url]').val(),
                        async: false
                     }).responseText;
                	var jsonobj = JSON.parse(html);
                	if(jsonobj[0].success){
                		layer.msg('修改成功');
                	}
                	form.render();  //重新渲染   
                    layer.close(index);           
                },
                shade: false,
                maxmin: true
            });
        }
    };
    exports('datalist', datalist);
});