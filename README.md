# CPTMP-Backend
College Practice Training Management Platform

# 项目结构
更新时间：2020/7/6
+ 四个模块，其中一个父模块cptmp，三个子模块cptmp-dao、cptmp-service、cptmp-web
+ 父模块继承自springboot官方
+ 子模块继承自父模块
+ cptmp-dao用于对数据库模型进行orm，对应模型类存放在model中；并基于mybatis框架提供与数据库交互的mapper类，存放在mapper包中
+ cptmp-service通过调用cptmp-dao中mapper的方法，与数据库进行交互，并实现业务逻辑
+ cptmp-web包括controller类，向前端提供各种api，调用service中封装的方法实现相应的功能
+ 三个子模块间的依赖关系为：web->service->dao

# API说明
更新时间：2020/7/9

# 登录/登出
## /api/login
+ HTTP方法：POST
+ 返回类型：json
+ 说明：返回体包括status，date，msg，详情@RespBean和@CptmpStatusCode中的状态码定义
+ 调用实例：
```
{
    "username": "test",
    "password": "123"
}
```
+ 返回json：
```
{
    "status": 0,
    "date": 1594371610050,
    "msg": "login successfully"
}
```

## /api/logout
+ HTTP方法：POST
+ 返回类型：json
+ 说明：返回体包括status，date，msg，详情@RespBean和@CptmpStatusCode中的状态码定义
+ 返回json：
``` 
{
    "status": 8,
    "date": "2020-07-11T03:54:26.043+00:00",
    "msg": "not login"
}
```


# LoginController
## /api/guard
+ HTTP方法：GET
+ 返回类型：json
+ 说明：当用户未登录时尝试访问未登录不能访问的地址时，返回一个json，状态码为8（以@CptmpStatusCode中的为准）
+ 返回json：
```
{
    "status": 8,
    "date": "2020-07-10T09:04:10.710+00:00",
    "msg": "not login"
}
```

## /api/access
+ HTTP方法：GET
+ 返回类型：json
+ 说明：用于测试登录是否成功
+ 返回json:
``` 
{
    "status": 0,
    "date": "2020-07-10T09:04:55.531+00:00",
    "msg": "access successfully"
}
```

# TrainProjectFindController
## /api/enterprise-admin/find/train-project
+ HTTP方法：GET
+ 返回类型：json
+ 说明：企业管理员模糊查询实训项目，可以查询到所有的项目
+ 调用实例：
```
{
    "key_word": "高校"
}
```
+ 返回json:
``` 
[
    {
        "id": 14,
        "project_name": "高校实训平台"
    },
    {
        "id": 25,
        "project_name": "高校实习平台"
    }
]
```

# UserDetailsController
## /api/user/me/basic-info
+ HTTP方法：GET
+ 返回类型：json
+ 说明：根据用户名，得到用户基本信息
+ 调用实例：
```
{
    "username": "test"
}
```
+ 返回json:
``` 
{
    "status": 0,
    "date": "2020-07-11T07:56:42.676+00:00",
    "msg": "success",
    "basic_info": {
        "email": "123@qq.com",
        "role_name": "ROLE_ENTERPRISE_ADMIN",
        "username": "test",
        "nickname": "test_nick",
        "phone_number": null,
        "gender": null,
        "introduction": null,
        "user_id": 4,
        "invitation_code": null,
        "name": "wxc",
        "employee_id": "123123"
    }
}
```

## /api/user/me/password
+ HTTP方法：PUT
+ 返回类型：json
+ 说明：修改用户密码，当用户输入的旧密码与现在的密码相同时，才能设置新密码
+ 调用实例：
```
{
    "username": "test"
}
```
+ 返回json:
```
// 成功 
{
    "username": "test",
    "origin_password": "123",
    "new_password": "123"
}
// 失败
{
    "status": 9,
    "date": "2020-07-11T07:58:48.703+00:00",
    "msg": "wrong origin password"
}
```