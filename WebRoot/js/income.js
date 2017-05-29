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
                url: "../IncomeService?action=find",
                async: false
             }).responseText;
         var jsonobj = JSON.parse(html);
         for (var i = 0; i < jsonobj.length; i++) {
             data.push({ id: jsonobj[i].id, num:jsonobj[i].num, category:jsonobj[i].category, member:jsonobj[i].member, sum:jsonobj[i].sum, date: jsonobj[i].date});
         }

//        for (var i = 0; i < 4; i++) {
//            data.push({ id: i + 1, num:'1', category:'工资', member:'父亲', sum:'￥120', date: '2017-3-26 15:56'});
//        }
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
            html += '<colgroup><col width="60"><col width="150"><col width="150"><col width="180"><col width="180"><col width="150"></colgroup>';
            html += '<thead><tr><th><form class="layui-form" action=""><input type="checkbox" lay-filter="select_all" title="全选"></form></th><th>类别</th><th>成员</th><th>总额</th><th>日期</th><th colspan="2">操作</th></tr></thead>';
            html += '<tbody>';
            //遍历文章集合
            for (var i = 0; i < data.length; i++) {
                var item = data[i];
                html += "<tr>";
                html += '<td><input type="checkbox" name="myselect"></td>'
                html += "<td id="+ item.id + ">" + item.category + "</td>";
                html += "<td>" + item.member + "</td>";
                html += "<td>" + item.sum + "</td>";
                html += "<td>" + item.date + "</td>";              
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
            layui.pagesize(laypageId, pageSize).callback(function (newPageSize) {
                initilData(1, newPageSize);
            });
        }, 500);
    }

    //监听添加信息
    $('#add').on('click', function () {
        layer.open({
            type: 2,
            title: '添加信息',
            content: ['in_add.html', 'no'],
            btn: ['确定', '取消'],
            area: ['600px', '500px'],
            yes: function (index, layero) { 
                var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var html = "<tr>";
                html += '<td><input type="checkbox" name="myselect"></td>'
                html += "<td id="+ 7 + ">" + body.find("select#cate").val() + "</td>";
                html += "<td>" + body.find("select#member").val() + "</td>";
                html += "<td>￥" + body.find("input#number").val() + "</td>";
                html += "<td>" + body.find("input#date").val() + "</td>";              
                html += '<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.datalist.editData(\'' + 7 + '\')"><i class="layui-icon">&#xe642;</i></button></td>';
                html += '<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.datalist.deleteData(\'' + 7 + '\')"><i class="layui-icon">&#xe640;</i></button></td>';
                html += "</tr>";
                $("tbody").append(html);
                form.render();  //重新渲染   
                layer.close(index);   
            },
         });
    });

    //监听删除
    $('#del').on('click', function () {
        layer.confirm('确定删除？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            layer.msg('删除成功'); 
            var x=document.getElementsByName("myselect");
            var cout = 0;
            for(i=0;i < x.length;i++){
                if(x[i-cout].checked){
                   $("tbody>tr").eq(i-cout).remove();
                   cout++;
                }       
            };
        }, function () {
            layer.msg('取消');
        });      
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
                layer.msg('删除成功'); 
                $("td#"+id).parent().remove();
            }, function () {
                layer.msg('取消');
            });
        },
        editData: function (id, layero) {
            layer.open({
                type: 2,
                title: '添加信息',
                content: ['in_add.html', 'no'],
                btn: ['确定', '取消'],
                area: ['600px', '500px'],
                yes: function (index, layero) { 
                    var body = layer.getChildFrame('body', index);
                    var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();

                    $("td#"+id).text(body.find("select#cate").val());
                    $("td#"+id).next().text(body.find("select#member").val());
                    $("td#"+id).next().next().text('￥' + body.find("input#number").val());
                    $("td#"+id).next().next().next().text(body.find("input#date").val());
                    form.render();  //重新渲染   
                    layer.close(index);   
                },
                success: function(layero, index){
                    var body = layer.getChildFrame('body', index);
                    var iframeWin = window[layero.find('iframe')[0]['name']];
                    body.find("select#cate").val($("td#"+id).text());
                    body.find("select#member").val($("td#"+id).next().text());
                    body.find("input#number").val($("td#"+id).next().next().text().substr(1));
                    body.find("input#date").val($("td#"+id).next().next().next().text());
                }
            });
        }
    };
    exports('datalist', datalist);  
});