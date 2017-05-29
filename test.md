#说明文档

##收支管理


###添加支出日志
####方法
`post` `/CostService?action=add`

####功能
*添加支出日志

####请求参数
    {
        category_id:1
        member_id:1
        number:1.0
        date:2017-03-04
    }

####请求实例
    $.ajax({
        url:"../CostService?action=add",
        type:"POST",
        dataType:"json",
        data:{
            category_id:body.find("select#cate").val(),
            member_id:body.find("select#member").val(),
            number:body.find("input#number").val(),
            date:body.find("input#date").val()
                },
        success:function(rs){
            if(rs.success=="true"){
                layer.msg('添加成功'); 
            }
        }
    })
####返回参数
字段|类型|说明
:---|:---:|-----:
success|boolean|成功插入返回true，否则返回false



###更新支出日志
####方法
`post` `/CostService?action=update`

####功能
*更新支出日志

####请求参数
    {
        id:1
        category_id:1
        member_id:1
        number:1.0
        date:2017-03-04
    }

####返回参数
字段|类型|说明
:---|:---:|-----:
success|boolean|成功插入返回true，否则返回false


###删除支出日志
####方法
`get` `/CostService?action=delete`

####功能
*删除支出日志

####请求参数
    {
        id:1
    }

####返回参数
字段|类型|说明
:---|:---:|-----:
success|boolean|成功插入返回true，否则返回false




###添加收入日志
####方法
`post` `/IncomeService?action=add`

####功能
*添加收入日志

####请求参数
    {
        category_id:1
        member_id:1
        number:1.0
        date:2017-03-04
    }


####返回参数
字段|类型|说明
:---|:---:|-----:
success|boolean|成功插入返回true，否则返回false



###更新收入日志
####方法
`post` `/IncomeService?action=update`

####功能
*更新收入日志

####请求参数
    {
        id:1
        category_id:1
        member_id:1
        number:1.0
        date:2017-03-04
    }

####返回参数
字段|类型|说明
:---|:---:|-----:
success|boolean|成功插入返回true，否则返回false


###删除收入日志
####方法
`get` `/InconeService?action=delete`

####功能
*删除收入日志

####请求参数
    {
        id:1
    }

####返回参数
字段|类型|说明
:---|:---:|-----:
success|boolean|成功插入返回true，否则返回false


###查询收入支出
####方法
`get`  `CostService?action=select`

####功能
*根据category_id 查询收入支出

####请求参数
    {
        id:1,
        note:rrrr
    }

####返回参数
字段|类型|说明
:---|:---:|-----:
id|string|收支ID
num|string|收支金额
category|string|收支类名（返回是真实的类型名称)
member|string|家庭成员名（返回真实的家庭成员名称)
sum|string|成功插入返回true，否则返回false


###收入支出图表
####方法
*`get` `CostService?action=form` `支出`
*`get` `IncomeService?action=form` `收入`


####功能
1. 按照支出类别分类，对应类别所有支出金额之和
2. 按照收入类别分类，对应类别所有收入金额之和


####返回参数
字段|类型|说明
:---|:---:|-----:
cate|string|类名（真实类名）
sum|string|收支金额



###收支折线图
####方法
*`get` `CostService?action=week`
*`get` `IncomeService?action=week`

####功能
1. 返回最近7天的收支记录

####返回参数
字段|类型|说明
:---|:---:|-----:
type|int|类名（真实类名）
sum|string|收支金额
date|date|时间
