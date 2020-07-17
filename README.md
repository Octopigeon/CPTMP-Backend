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

## /api/user

+ HTTP方法：GET

+ 返回类型：json

+ 说明：获取所有用户

+ 调用实例：

+ ```json
  {
    "page":1,
    "offset":3
  }
  ```

+ 返回实例

+ ```json
  {
      "status": 0,
      "date": "2020-07-17T06:12:51.123+00:00",
      "msg": "success",
      "total_rows": 2,
      "data": [
          {
              "email": "wxcnb@qq.com",
              "role_name": "ROLE_SCHOOL_TEACHER",
              "username": "WHU-2018302060342",
              "name": "魏啸冲",
              "avatar": null,
              "common_id": "2018302060342",
              "organization_id": 1,
              "phone_number": null,
              "gender": null,
              "introduction": null,
              "user_id": 1
          },
          {
              "email": "1111@qq.com",
              "role_name": "ROLE_SCHOOL_TEACHER",
              "username": "WHU-2018302060000",
              "name": "QQ",
              "avatar": null,
              "common_id": "2018302060000",
              "organization_id": 1,
              "phone_number": null,
              "gender": null,
              "introduction": null,
              "user_id": 2
          }
      ]
  }
  ```

  

# RegisterController

## /api/user/enterprise-admin
## /api/user/teacher-admin
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

## /api/train-project
+ HTTP方法：POST
+ 返回类型：json
+ 说明：项目注册，必须至少为企业管理员权限
+ 调用实例：
``` 
[
    {
        "project_name": "武汉大学暑期实训",
        "project_level": 1,
        "project_content": "东方瑞通中级项目实训"
    },
    {
        "project_name": "华中科技大学暑期实训",
        "project_level": 0,
        "project_content": "摸鱼滑水"
    }
]
```
+ 返回实例:
``` 
// 成功
{
    "status": 0,
    "date": "2020-07-15T05:49:03.340+00:00",
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

# OrganizationDetailsController
## /api/org/basic-info
+ HTTP方法：PUT
+ 返回类型：json
+ 说明：更新信息
+ 调用实例：
``` json
{
    "code": "WHU",
    "real_name": "武汉大学",
    "description": "中国最牛逼的大学",
    "website_url": "www.whu.edu.cn"
}
```
``` 
// 成功
{
    "status": 0,
    "date": "2020-07-14T02:18:20.590+00:00",
    "msg": "update organization info success"
}
// 失败
{
    "status": 10,
    "date": "2020-07-14T02:21:43.068+00:00",
    "msg": "update organization info failed"
}
```



# ProjectDetailsController

## /api/train-project/{id}/basic-info
+ HTTP方法：GET
+ 返回类型：json
+ 说明：通过id得到具体某个project的信息
+ 调用实例：
``` 
// 成功
{
    "status": 0,
    "date": "2020-07-15T06:55:20.353+00:00",
    "msg": "success",
    "data": {
        "id": 10,
        "name": "华中科技大学暑期实训",
        "level": 0,
        "content": "摸鱼滑水",
        "resource_library": "{\"resourceLib\":[]}"
    }
}
// 失败
{
    "status": 15,
    "date": "2020-07-15T06:57:45.645+00:00",
    "msg": "find project info failed",
    "data": null
}
```
+ HTTP方法：PUT
+ 返回类型：json
+ 说明：通过id更新具体某个project的信息
+ 调用实例：
``` 
{
    "name": "华中科技大学真正的实训",
    "content": "一天爆肝24h",
    "level": 5
}
```
+ 返回实例：
``` 
// 成功
{
    "status": 0,
    "date": "2020-07-15T07:14:14.543+00:00",
    "msg": "update success"
}
// 失败
{
    "status": 10,
    "date": "2020-07-15T07:13:29.680+00:00",
    "msg": "update project basic info failed"
}
```
## "/api/train-project/{id}/resource-lib
+ HTTP方法：PUT
+ 返回类型：json
+ 说明：通过id更新具体某个project的信息
+ 返回实例
``` 
// 成功
{
    "status": 0,
    "date": "2020-07-14T02:18:20.590+00:00",
    "msg": "upload resource files success"
}
// 失败
{
    "status": 16,
    "date": "2020-07-14T02:21:43.068+00:00",
    "msg": "upload resource files failed"
}
```

## /api/train-project
+ HTTP方法：DELETE
+ 返回类型：json
+ 说明：批量删除project
+ 调用实例：
``` 
[
    9,
    10,
    11
]
```
+ 返回实例:
``` 
// 成功
{
    "status": 0,
    "date": "2020-07-15T07:49:46.410+00:00",
    "msg": "all set",
    "data": null
}
// 失败
{
    "status": 11,
    "date": "2020-07-15T07:51:08.223+00:00",
    "msg": "operation failed",
    "data": [
        2
    ]
}
```

# PersonTrainController
## /api/student/me/team/{teamId}/remark
+ HTTP方法：GET
+ 返回类型：json
+ 说明：学生查询自己的成绩
+ 返回实例：
``` 
// 成功
{
    "status": 0,
    "date": "2020-07-16T10:20:00.560+00:00",
    "msg": "success",
    "data": {
        "id": 5,
        "team_id": 5,
        "user_id": 5,
        "personal_grade": 50,
        "evaluation": "真tm好啊"
    }
}
// 失败
{
    "status": 15,
    "date": "2020-07-16T10:20:31.764+00:00",
    "msg": "get grade failed",
    "data": null
}
```
## /api/student/{userId}/team/{teamId}/remark
+ HTTP方法：GET
+ 返回类型：json
+ 说明：学校老师查询自己学校学生的成绩
+ 返回实例：
``` 
// 成功
{
    "status": 0,
    "date": "2020-07-16T10:15:45.261+00:00",
    "msg": "success",
    "data": {
        "id": 5,
        "team_id": 5,
        "user_id": 5,
        "personal_grade": 50,
        "evaluation": "真tm好啊"
    }
}
// 失败
{
    "status": 15,
    "date": "2020-07-16T10:17:23.745+00:00",
    "msg": "get student grade failed",
    "data": null
}
```
+ HTTP方法：PUT
+ 返回类型：json
+ 说明：学校老师修改学生成绩
+ 调用实例：
```
{
    "personal_grade": 50,
    "evaluation": "真tm好啊"
}
```
+ 返回实例：
``` 
// 成功
{
    "status": 0,
    "date": "2020-07-16T10:21:15.155+00:00",
    "msg": "modify student grade success"
}
// 失败
{
    "status": 10,
    "date": "2020-07-16T10:22:46.603+00:00",
    "msg": "modify student grade failed"
}
```

# 实训模块（TrainDetailsController）

## 1.创建实训

+ 接口：api/train

+ 方法：POST

+ 调用实例：

  ```json
  {
      "name": "2020武汉大学暑期实训",
      "content": "啊这",
      "organization_id": 1,
      "start_time": "2020-07-14T16:00:00.000+00:00",
      "end_time": "2020-07-14T16:00:00.000+00:00",
      "accept_standard": "啊这",
      "resource_library": "{}",
      "gps_info": "{}"
  }
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T06:41:16.877+00:00",
      "msg": "create train successfully"
  }
  //失败
  {
      "status": 11,
      "date": "2020-07-17T06:44:31.537+00:00",
      "msg": "Train create failed"
  }
  ```

## 2.通过ID获取实训信息

+ 接口：api/train/{train_id}

+ 方法：GET

+ 调用实例：

  ```json
  //无Requstbody
  api/train/1
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T06:45:02.189+00:00",
      "msg": "success",
      "data": {
          "id": 1,
          "name": "清华大学暑期实训",
          "content": "啊这",
          "organization_id": 1,
          "start_time": "2020-07-16T16:00:00.000+00:00",
          "end_time": "2020-07-16T16:00:00.000+00:00",
          "accept_standard": "啊这也",
          "resource_library": "{\"resourceLib}",
          "gps_info": "{}"
      }
  }
  //失败
  {
      "status": 15,
      "date": "2020-07-17T06:50:59.336+00:00",
      "msg": "get train failed",
      "data": null
  }
  ```

## 3.获取所有实训

+ 接口：api/train

+ 方法：GET

+ 调用实例：

  ```json
  {
      "page":1,  //页号
      "offset":8 //每页最大条目数
  }
  ```

+ 返回实例：

  ```json
  {
      "status": 0,
      "date": "2020-07-17T06:57:21.536+00:00",
      "msg": "success",
      "total_rows": 2,
      "data": [
          {
              "id": 1,
              "name": "清华大学暑期实训",
              "content": "啊这",
              "organization_id": 1,
              "start_time": "2020-07-16T16:00:00.000+00:00",
              "end_time": "2020-07-16T16:00:00.000+00:00",
              "accept_standard": "啊这也",
              "resource_library": "{\"resourceLib\":[]}",
              "gps_info": "{}"
          },
          {
              "id": 2,
              "name": "2020武汉大学暑期实训",
              "content": "啊这",
              "organization_id": 1,
              "start_time": "2020-07-13T16:00:00.000+00:00",
              "end_time": "2020-07-13T16:00:00.000+00:00",
              "accept_standard": "啊这",
              "resource_library": "{\"resourceLib\":[]}",
              "gps_info": "{}"
          }
      ]
  }
  ```

  

## 4.根据关键词查询实训

+ 接口：api/train/search/{property}

+ 方法：GET

+ 调用实例：

  ```json
  //api/train/search/name
  {
      "key_word":"武汉大学",
      "page":1,
      "offset":8
  }
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T07:04:41.931+00:00",
      "msg": "success",
      "total_rows": 2,
      "data": [
          {
              "id": 2,
              "name": "2020武汉大学暑期实训",
              "content": "啊这",
              "organization_id": 1,
              "start_time": "2020-07-13T16:00:00.000+00:00",
              "end_time": "2020-07-13T16:00:00.000+00:00",
              "accept_standard": "啊这",
              "resource_library": "{\"resourceLib\":[]}",
              "gps_info": "{}"
          },
          {
              "id": 3,
              "name": "2020武汉大学暑期实训",
              "content": "啊这",
              "organization_id": 1,
              "start_time": "2020-07-13T16:00:00.000+00:00",
              "end_time": "2020-07-13T16:00:00.000+00:00",
              "accept_standard": "啊这",
              "resource_library": "{\"resourceLib\":[]}",
              "gps_info": "{}"
          }
      ]
  }
  //
  ```

  

## 5.根据id删除实训

+ 接口：api/train/{train_id}

+ 方法：DELETE

+ 调用实例：

  ```json
  //api/train/3
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T07:08:14.449+00:00",
      "msg": "train remove successfully"
  }
  //失败
  {
      "status": 17,
      "date": "2020-07-17T07:08:48.066+00:00",
      "msg": "train remove failed"
  }
  ```

  

## 6.在实训中批量添加项目

+ 接口：api/train/{train_id}/project

+ 方法：PUT

+ 调用实例：

  ```json
  //api/train/1/project
  [
      2，
      3
  ]
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T07:34:03.662+00:00",
      "msg": "all set",
      "data": null
  }
  //失败
  //data：添加失败的项目在数组中的次序（若需要可替换为id）
  {
      "status": 11,
      "date": "2020-07-17T07:38:41.924+00:00",
      "msg": "operation failed",
      "data": [
          1
      ]
  }
  ```

  

## 7.在实训中批量删除项目

+ 接口：api/train{train_id}/project

+ 方法：DELETE

+ 调用实例：

  ```json
  //api/train/1/project
  [
      2，
      3
  ]
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T07:45:07.812+00:00",
      "msg": "all set",
      "data": null
  }
  //失败
  {
      "status": 11,
      "date": "2020-07-17T07:45:21.481+00:00",
      "msg": "operation failed",
      "data": [
          0
      ]
  }
  ```

  

## 8.获取实训的所有项目

+ 接口：api/train/{train_id}/project

+ 方法：POST

+ 调用实例：

  ```json
  //api/train/1/project
  {
      "page":1,
      "offset":8
  }
  
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T07:47:33.010+00:00",
      "msg": "success",
      "total_rows": 1,
      "data": [
          {
              "id": 3,
              "name": "cptmp",
              "level": 1,
              "content": "xxx",
              "resource_library": "file/233"
          }
      ]
  }
  ```

  

## 1.创建实训//这是我的模板 勿删🙅‍

+ 接口：api/train

+ 方法：POST

+ 调用实例：

  ```json
  
  ```

+ 返回实例：

  ```json
  //成功
  //失败
  ```

  

