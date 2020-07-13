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

# 目前已和前端对接的API说明
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
+ 返回json:
``` 
{
    "status": 0,
    "date": "2020-07-11T18:12:16.758+00:00",
    "msg": "success",
    "data": {
        "email": "123@qq.com",
        "role_name": "ROLE_ENTERPRISE_ADMIN",
        "username": "test",
        "avatar": "124124321",
        "phone_number": 31241234,
        "gender": true,
        "introduction": "我是大鸽",
        "user_id": 4,
        "name": "wxc",
        "common_id": "123123"
    }
}
```
+ HTTP方法：PUT
+ 返回类型：json
+ 说明：修改用户姓名，性别，个人简介
+ 调用示例:
```
{
    "name": "李国鹏",
    "gender": false,
    "introduction": "我是大鸽"
} 
```
+ 返回json:
``` 
// 成功
{
    "status": 0,
    "date": "2020-07-13T06:28:09.780+00:00",
    "msg": "update basic info successfully"
}
// 失败
{
    "status": 10,
    "date": "2020-07-13T06:19:25.123+00:00",
    "msg": "modify info failed"
}
```

## /api/user/me/password
+ HTTP方法：PUT
+ 返回类型：json
+ 说明：修改用户密码，当用户输入的旧密码与现在的密码相同时，才能设置新密码
+ 调用实例：
```
{
    "origin_password": "123",
    "new_password": "123"
}
```
+ 返回json:
```
// 成功 
{
    "status": 0,
    "date": "2020-07-11T09:36:51.368+00:00",
    "msg": "reset password success"
}
// 失败
{
    "status": 9,
    "date": "2020-07-11T07:58:48.703+00:00",
    "msg": "wrong origin password"
}
```

## /api/user
+ HTTP方法：DELETE
+ 返回类型：json
+ 说明：删除账户（软删除，disable）（注：此处的错误码用的是注册失败的错误码，后面有可能会改，前端建议判断的时候只要状态码非0都算失败）
+ 调用实例：
``` 
[
    2,
    4
]
```
+ 返回实例：
``` 
// 成功
{
    "status": 0,
    "date": "2020-07-13T15:50:38.933+00:00",
    "msg": "all set",
    "data": null
}
// 失败
{
    "status": 11,
    "date": "2020-07-13T15:50:38.933+00:00",
    "msg": ""operation failed"",
    "data": null
}
```

# RegisterController
## /api/user/enterprise-admin
## /api/user/teacher
## /api/user/student
+ HTTP方法：POST
+ 返回类型：json
+ 说明：导入注册各种用户，**特别注意：超管注册企业账户需要将自己的orgId传给后端（因为超管和企业管理员都是属于企业的，是同一个组织）**，而注册老师和学生则是传相应的学校id
+ 调用实例：
``` 
[
    {
        "common_id": "1000302060342",
        "name": "王恒",
        "password": 123,
        "email": "whnb@qq.com",
        "organization_id": 2
    },
    {
        "common_id": "1000432423552",
        "name": "王竖",
        "password": 123,
        "email": "whtnbl@qq.com",
        "organization_id": 5
    }
]
```
+ 返回json：
``` 
// 成功
{
    "status": 0,
    "date": "2020-07-12T01:48:09.955+00:00",
    "msg": "all set",
    "data": null
}
// 失败
{
    "status": 11,
    "date": "2020-07-12T01:48:09.955+00:00",
    "msg": "register failed",
    "data": [
        0
    ]
}
```

## /api/org
+ HTTP方法：POST
+ 返回类型：json
+ 说明：企业集体注册学校
+ 调用实例：
``` 
[
    {
        "code": "WHU",
        "real_name": "武汉大学",
        "website_url": "www.whu.edu.cn",
        "description": "湖北省武汉市武汉大学"
    },
    {
        "code": "HUST",
        "real_name": "华中科技大学",
        "website_url": "www.hust.edu.cn",
        "description": "湖北省武汉市华中科技大学"
    }
]
```
+ 返回json：
``` 
// 成功
{
    "status": 0,
    "date": "2020-07-13T12:17:08.750+00:00",
    "msg": "all set",
    "data": null
}
// 失败
{
    "status": 11,
    "date": "2020-07-13T12:21:12.932+00:00",
    "msg": "register failed",
    "data": [
        0
    ]
}
```

## /api/user/student/invite
+ HTTP方法：GET
+ 返回类型：json
+ 说明：验证邀请链接是否有效
+ 调用实例：
``` 
{
    "invitation_code": "a4c49014-bd4c-4c20-94b3-a3485bae6e23"
}
```
+ 返回实例：
``` 
// 成功
{
    "status": 0,
    "date": "2020-07-13T14:11:53.656+00:00",
    "msg": "success",
    "data": {
        "name": "WHU",
        "real_name": "武汉大学",
        "description": "湖北省武汉市武汉大学",
        "website_url": "www.whu.edu.cn"
    }
}
// 失败
{
    "status": 14,
    "date": "2020-07-13T14:12:15.078+00:00",
    "msg": "fake invitation code",
    "data": null
}
```
+ HTTP方法：POST
+ 返回类型：json
+ 说明：学生注册
+ 调用实例：
``` 
{
    "name": "李国豪",
    "email": "2018302011123@whu.edu.cn",
    "username": "WHU-2018302011123",
    "password": 123,
    "invitation_code": "a4c49014-bd4c-4c20-94b3-a3485bae6e23"
}
```
+ 返回实例：
``` 
// 成功
{
    "status": 0,
    "date": "2020-07-13T13:52:11.829+00:00",
    "msg": "register success"
}
// 失败
{
    "status": 11,
    "date": "2020-07-13T13:52:48.798+00:00",
    "msg": "register failed"
}
```