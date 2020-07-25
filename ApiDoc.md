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

  

## /api/user/basic-info

+ HTTP方法：GET

+ 返回类型：json

+ 参数：user_id（类型Biginteger[]）

+ 说明：根据id得到用户基本信息

+ 调用实例：

+ ```json
  //api/user/basic-info?user_id=18,22,30
  ```

+ 返回实例

``` json
{
    "status": 0,
    "date": "2020-07-24T03:18:03.506+00:00",
    "msg": "success",
    "total_rows": 3,
    "data": [
        {
            "email": "123123@163.com",
            "role_name": "ROLE_STUDENT_MEMBER",
            "username": "WHU-2018302110018",
            "name": "李四",
            "avatar": "https://cdn.v2ex.com/gravatar/c8e96b40867bb2e296213c7d4008be7d/?d=null%2Fassets%2Favatar.png&s=512&r=g",
            "common_id": "2018302110018",
            "organization_id": 2,
            "phone_number": null,
            "gender": null,
            "introduction": null,
            "domain": null,
            "default_avatar": "null/assets/avatar.png",
            "gravatar_root": "https://cdn.v2ex.com/gravatar/",
            "user_id": 18
        },
        {
            "email": "1542342@qq.com",
            "role_name": "ROLE_SCHOOL_TEACHER",
            "username": "WHU-2018302110054",
            "name": "刘二思",
            "avatar": "https://cdn.v2ex.com/gravatar/7e02704d19c48e25b5dbddabf982ce49/?d=null%2Fassets%2Favatar.png&s=512&r=g",
            "common_id": "2018302110054",
            "organization_id": 2,
            "phone_number": null,
            "gender": null,
            "introduction": null,
            "domain": null,
            "default_avatar": "null/assets/avatar.png",
            "gravatar_root": "https://cdn.v2ex.com/gravatar/",
            "user_id": 22
        },
        {
            "email": "125431288@163.com",
            "role_name": "ROLE_ENTERPRISE_ADMIN",
            "username": "E-2019302110055",
            "name": "王文生",
            "avatar": "https://cdn.v2ex.com/gravatar/7a3f86fd59244591bc18e1dd7d892964/?d=null%2Fassets%2Favatar.png&s=512&r=g",
            "common_id": "2019302110055",
            "organization_id": 1,
            "phone_number": null,
            "gender": null,
            "introduction": null,
            "domain": null,
            "default_avatar": "null/assets/avatar.png",
            "gravatar_root": "https://cdn.v2ex.com/gravatar/",
            "user_id": 30
        }
    ]
}
```

## /api/user/pwd

+ HTTP方法：PUT

+ 返回类型：json

+ 说明：系统管理员设置密码

+ 调用实例：

+ ```json
   {
          "username": "WHU-1000432423552",
          "new_password": "123456"
   }
  ```

+ 返回实例

+ ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-19T03:50:49.147+00:00",
      "msg": "update password successfully"
  }
  //失败（权限不足）
  {
      "timestamp": "2020-07-19T03:53:05.162+00:00",
      "status": 403,
      "error": "Forbidden",
      "message": "",
      "path": "/api/user/pwd"
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

## /api/org

+ HTTP方法：GET
+ 参数：page 、offset
+ 返回类型：json
+ 说明：获取所有组织
+ 调用实例：

``` json
//api/org?page=1&offset=4
```

+ 返回实例

``` json
// 成功
{
    "status": 0,
    "date": "2020-07-18T03:28:06.695+00:00",
    "msg": "success",
    "total_rows": 1,
    "data": [
        {
            "gmt_create": "2020-07-16T16:00:00.000+00:00",
            "name": "WHU",
            "real_name": "武汉大学",
            "description": "湖北省武汉市武汉大学",
            "website_url": "www.whu.edu.cn"
        }
    ]
}
// 失败
```

## /api/org/{org_id}

+ HTTP方法：GET
+ 返回类型：json
+ 说明：根据id获取组织
+ 调用实例：

``` json
//api/org/1
```

+ 返回实例

``` json
// 成功
{
    "status": 0,
    "date": "2020-07-18T06:26:51.680+00:00",
    "msg": "success",
    "data": {
        "gmt_create": "2020-07-16T16:00:00.000+00:00",
        "name": "WHU",
        "real_name": "武汉大学",
        "description": "湖北省武汉市武汉大学",
        "website_url": "www.whu.edu.cn"
    }
}
// 失败
{
    "status": 15,
    "date": "2020-07-18T06:27:47.060+00:00",
    "msg": "get org info failed",
    "data": null
}
```



## /api/org/name

+ HTTP方法：GET
+ 返回类型：json
+ 说明：根据id批量获取组织名称
+ 参数：org_id
+ 调用实例：

``` json
//api/org/name?org_id=1,2
```

+ 返回实例

``` json
// 成功
{
    "status": 0,
    "date": "2020-07-20T07:14:30.977+00:00",
    "msg": "success",
    "data": [
        "百度",
        "HUST"
    ]
}
// 失败
{
    "status": 15,
    "date": "2020-07-20T07:15:26.112+00:00",
    "msg": "get org info failed",
    "data": null
}
```

## /api/org/{org_id}

+ HTTP方法：DELETE
+ 返回类型：json
+ 说明：根据id删除组织
+ 调用实例：

``` json
//api/org/3
```

+ 返回实例

``` json
// 成功
{
    "status": 0,
    "date": "2020-07-20T09:18:13.813+00:00",
    "msg": "delete org successfully"
}
// 失败
{
    "status": 17,
    "date": "2020-07-20T10:27:41.497+00:00",
    "msg": "delete org failed"
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
## /api/train-project/{id}/resource-lib
+ HTTP方法：POST
+ 参数：文件（key=“file”）
+ 说明：上传文件
+ 调用实例：
``` json
//api/train-project/1/resource-lib
参数为要上传文件
```

+ 返回实例:

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

## /api/train-project/{id}/resource-lib

+ HTTP方法：DELETE
+ 参数：文件（key=“file”）
+ 说明：删除文件
+ 调用实例：

``` json
//api/train-project/1/resource-lib
{
   "fileName":"0b09aecb-f324-4f83-b453-e5b55203048a.md",
   "filePath":"C:\\\\cptmp\\\\private\\\\2020\\\\7\\\\18\\\\0b09aecb-f324-4f83-b453-e5b55203048a.md",
   "fileSize":31,
   "fileType":"text/markdown",
   "fileUrl":"/api/storage/2020/7/18/0b09aecb-f324-4f83-b453-e5b55203048a.md",
   "gmtCreate":1595067876399,
   "originName":"CPTMP-需求分析文档.md"
}
```

+ 返回实例:

``` 
// 成功
{
    "status": 0,
    "date": "2020-07-14T02:18:20.590+00:00",
    "msg": "remove resource files success"
}
// 失败
{
    "status": 16,
    "date": "2020-07-14T02:21:43.068+00:00",
    "msg": "remove resource files failed"
}
```

## /api/train

+ HTTP方法：GET
+ 返回类型：json
+ 参数：offset、page
+ 说明：获取所有项目
+ 调用实例：
``` 
/api/train-project?offset=2&page=1
```
+ 返回实例:
``` json
// 成功
{
    "status": 0,
    "date": "2020-07-20T08:54:01.213+00:00",
    "msg": "success",
    "total_rows": 5,
    "data": [
        {
            "id": 1,
            "name": "大一实训",
            "level": 1,
            "content": "坦克大战",
            "resource_library": "https://www.baidu.com/"
        },
        {
            "id": 2,
            "name": "大二实训",
            "level": 2,
            "content": "飞机大战",
            "resource_library": "https://www.baidu.com/"
        }
    ]
}
// 失败

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

## api/project/search/name

+ HTTP方法：GET
+ 返回类型：json
+ 说明：按名称获取项目
+ 调用实例：

``` json
//api/project/search/name?page=1&offset=6&key_word=实训
```

+ 返回实例:

``` json
// 成功
{
    "status": 0,
    "date": "2020-07-20T16:14:07.727+00:00",
    "msg": "success",
    "total_rows": 3,
    "data": [
        {
            "id": 1,
            "name": "大一实训",
            "level": 1,
            "content": "坦克大战",
            "resource_library": "https://www.baidu.com/"
        },
        {
            "id": 2,
            "name": "大二实训",
            "level": 2,
            "content": "飞机大战",
            "resource_library": "https://www.baidu.com/"
        },
        {
            "id": 3,
            "name": "大三实训",
            "level": 3,
            "content": "实训管理平台",
            "resource_library": "https://www.baidu.com/"
        }
    ]
}
// 失败

```



# PersonTrainController

## /api/student/me/team/{teamId}/remark
+ HTTP方法：GET
+ 返回类型：json
+ 说明：学生查询自己的成绩
+ 返回实例：
``` json
// 成功
{
    "status": 0,
    "date": "2020-07-24T07:46:19.776+00:00",
    "msg": "success",
    "data": {
        "id": 4,
        "evaluation": "优秀",
        "team_id": 4,
        "user_id": 4,
        "manage_point": 100,
        "code_point": 100,
        "tech_point": 100,
        "framework_point": 100,
        "communication_point": 100
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
        "manage_point": 100,
        "code_point": 100,
        "tech_point": 100,
        "framework_point": 100,
        "communication_point": 100，
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

+ 参数：page，offset

+ 调用实例：

  ```json
  //api/train?page=1&offset=8
  ```
  
+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-20T13:09:51.868+00:00",
      "msg": "success",
      "total_rows": 4,
      "data": [
          {
              "id": 1,
              "name": "初级项目实训",
              "content": "武汉大学大一实训",
              "limits": null,
              "organization_id": 1,
              "start_time": "2018-07-07T16:00:00.000+00:00",
              "end_time": "2018-07-19T16:00:00.000+00:00",
              "accept_standard": "完成基础项目",
              "resource_library": "{\"resourceLib\":[]}",
              "gps_info": "http://www.boorgeel.com/"
          },
          {
              "id": 2,
              "name": "中级项目实训",
              "content": "武汉大学大二实训",
              "limits": null,
              "organization_id": 2,
              "start_time": "2019-07-15T16:00:00.000+00:00",
              "end_time": "2019-08-05T16:00:00.000+00:00",
              "accept_standard": "完成基础项目",
              "resource_library": "{\"resourceLib\":[]}",
              "gps_info": "http://www.boorgeel.com/"
          },
        {
              "id": 4,
              "name": "大三合作实训",
              "content": "武汉大学大三实训",
              "limits": null,
              "organization_id": 4,
              "start_time": "2021-07-15T16:00:00.000+00:00",
              "end_time": "2021-09-15T16:00:00.000+00:00",
              "accept_standard": "完成拓展项目",
              "resource_library": "{\"resourceLib\":[]}",
              "gps_info": "http://www.boorgeel.com/"
          },
          {
              "id": 5,
              "name": "大四合作实训",
              "content": "武汉大学大四实训",
              "limits": null,
              "organization_id": 5,
              "start_time": "2022-07-15T16:00:00.000+00:00",
              "end_time": "2022-10-15T16:00:00.000+00:00",
              "accept_standard": "完成所有项目",
              "resource_library": "{\"resourceLib\":[]}",
              "gps_info": "http://www.boorgeel.com/"
          }
      ]
  }
  //失败
  {
      "status": 15,
      "date": "2020-07-17T17:23:14.420+00:00",
      "msg": "get train failed",
      "total_rows": 0,
      "data": null
  }
  ```
  
  

## 4.根据属性查询实训

#### 4.1 根据名称

+ 接口：api/train/search/name

+ 方法：GET

+  参数：key_word、page、offset

+ 调用实例：

  ```json
  //api/train/search/name?key_word=实训&page=1&offset=8
  ```
  
+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-20T13:19:00.713+00:00",
      "msg": "success",
      "total_rows": 2,
      "data": [
          {
              "id": 1,
              "name": "初级项目实训",
              "content": "武汉大学大一实训",
              "limits": null,
              "organization_id": 1,
              "start_time": "2018-07-07T16:00:00.000+00:00",
              "end_time": "2018-07-19T16:00:00.000+00:00",
              "accept_standard": "完成基础项目",
              "resource_library": "{\"resourceLib\":[]}",
              "gps_info": "http://www.boorgeel.com/"
          },
          {
              "id": 2,
              "name": "中级项目实训",
              "content": "武汉大学大二实训",
              "limits": null,
              "organization_id": 2,
              "start_time": "2019-07-15T16:00:00.000+00:00",
              "end_time": "2019-08-05T16:00:00.000+00:00",
              "accept_standard": "完成基础项目",
              "resource_library": "{\"resourceLib\":[]}",
              "gps_info": "http://www.boorgeel.com/"
          }
      ]
  }
  //失败
  {
    "status": 15,
      "date": "2020-07-17T17:23:14.420+00:00",
      "msg": "get train failed",
      "total_rows": 0,
      "data": null
  }
  ```
  
  

#### 4.2 根据组织

+ 接口：api/train/search/org

+ 方法：GET

+ 参数：key_word、page、offset

+ 调用实例：

  ```json
  //api/train/search/org?key_word=1&page=1&offset=8
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-20T13:21:59.181+00:00",
      "msg": "success",
      "total_rows": 1,
      "data": [
          {
              "id": 1,
              "name": "初级项目实训",
              "content": "武汉大学大一实训",
              "limits": null,
              "organization_id": 1,
              "start_time": "2018-07-07T16:00:00.000+00:00",
              "end_time": "2018-07-19T16:00:00.000+00:00",
              "accept_standard": "完成基础项目",
              "resource_library": "{\"resourceLib\":[]}",
              "gps_info": "http://www.boorgeel.com/"
          }
      ]
  }
  //失败
  {
    "status": 15,
      "date": "2020-07-17T17:23:14.420+00:00",
      "msg": "get train failed",
      "total_rows": 0,
      "data": null
  }
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
  //api/train/1/project?page=1&offset=2
  ```
  
+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-20T13:26:40.487+00:00",
      "msg": "success",
      "total_rows": 1,
      "data": [
          {
              "id": 1,
              "name": "大一实训",
              "level": 1,
              "content": "坦克大战",
              "resource_library": "https://www.baidu.com/"
          }
      ]
  }
  ```

  

## 9.上传实训相关文件

+ 接口：/api/train/{train_id}/resource-lib

+ 方法：POST

+ 调用实例：

  ```json
  //api/train/1/resource-lib
  
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T16:13:00.278+00:00",
      "msg": "upload resource files success"
  }
  //失败
  {
      "status": 16,
      "date": "2020-07-17T16:13:17.800+00:00",
      "msg": "upload resource files failed"
  }
  ```

  

## 10.删除实训相关文件

+ 接口：/api/train/{train_id}/resource-lib

+ 方法：DELETE

+ 调用实例：

  ```json
  //api/train/1/resource-lib
  {
     "fileName":"0b09aecb-f324-4f83-b453-e5b55203048a.md",
     "filePath":"C:\\\\cptmp\\\\private\\\\2020\\\\7\\\\18\\\\0b09aecb-f324-4f83-b453-e5b55203048a.md",
     "fileSize":31,
     "fileType":"text/markdown",
     "fileUrl":"/api/storage/2020/7/18/0b09aecb-f324-4f83-b453-e5b55203048a.md",
     "gmtCreate":1595067876399,
     "originName":"CPTMP-需求分析文档.md"
  }
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-18T11:45:47.318+00:00",
      "msg": "remove resource files success"
  }
  //失败
  {
      "status": 16,
      "date": "2020-07-18T11:48:27.966+00:00",
      "msg": "remove resource files failed"
  }
  ```

  

## 11.更新实训信息

+ 接口：/api/train

+ 方法：PUT

+ 调用实例：

  ```json
  {
      "id":5,
      "name": "2045武汉大学暑期实训",
      "content": "啊这",
      "organization_id": 1,
      "start_time": "2020-07-14T16:00:00",
      "end_time": "2020-07-14T16:00:00.000+00:00",
      "accept_standard": "啊这",
      "resource_library": "",
      "gps_info": ""
  }
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-18T06:18:27.818+00:00",
      "msg": "update train successfully"
  }
  //失败
  {
      "status": 18,
      "date": "2020-07-18T06:21:32.702+00:00",
      "msg": "Train update failed"
  }
  ```

  

# 团队模块（TeamDetialsController）

## 1.创建团队

+ 接口：api/team

+ 方法：POST

+ 调用实例：

  ```json
  {
      "name": "章鱼鸽",
      "avatar": "abc.com",
      "evaluation": "good",
      "train_id": 1,
      "project_id": 3,
      "repo_url": "123456.github.io",
      "team_grade": 99
  }
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T12:52:03.411+00:00",
      "msg": "create team successfully"
  }
  //失败
  {
      "status": 11,
      "date": "2020-07-17T12:52:50.922+00:00",
      "msg": "Team create failed"
  }
  ```


## 2.删除团队

+ 接口：api/team/{team_id}

+ 方法：DELETE

+ 调用实例：

  ```json
  //api/team/6
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T13:07:16.334+00:00",
      "msg": "team remove successfully"
  }
  //失败
  {
      "status": 17,
      "date": "2020-07-17T13:10:44.450+00:00",
      "msg": "team remove failed"
  }
  ```

  

## 3.根据名称分页查询团队

+ 接口：api/team/search/name

+ 方法：GET

+ 参数：key_word,offset,page

+ 调用实例：

  ```json
  //api/team/search/name?key_word=鸽&page=1&offset=2
  ```
  
+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-20T13:06:07.225+00:00",
      "msg": "success",
      "total_rows": 3,
      "data": [
          {
              "id": 1,
              "name": "章鱼鸽",
              "avatar": null,
              "evaluation": "优秀",
              "train_id": 1,
              "project_id": 1,
              "repo_url": "Octopigeon/CPTMP-Backend",
              "team_grade": 99
          },
          {
              "id": 4,
              "name": "小鸽子",
              "avatar": null,
            "evaluation": "及格",
              "train_id": 4,
              "project_id": 4,
              "repo_url": "",
              "team_grade": 98
          }
      ]
  }
  //失败
  ```
  
  

## 4.根据id获取团队信息

+ 接口：api/team/{team_id}

+ 方法：GET

+ 调用实例：

  ```json
  //api/team/4
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T14:11:56.234+00:00",
      "msg": "success",
      "data": {
          "id": 4,
          "name": "章鱼鸽",
          "avatar": "abc.com",
          "evaluation": "good",
          "train_id": 1,
          "project_id": 3,
          "repo_url": "123456.github.io",
          "team_grade": 99
      }
  }
  //失败
  {
      "status": 15,
      "date": "2020-07-17T14:13:10.350+00:00",
      "msg": "find team failed",
      "data": null
  }
  ```

  

## 5.修改团队信息

+ 接口：api/team

+ 方法：PUT

+ 调用实例：

  ```json
  {
      "id": 4,
      "name": "章鱼鸽！",
      "avatar": "abc.com",
      "evaluation": "good",
      "train_id": 1,
      "project_id": 1,
      "repo_url": "123456.github.io",
      "team_grade": 100
  }
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T15:20:24.169+00:00",
      "msg": "update team info successfully"
  }
  //失败
  {
      "status": 10,
      "date": "2020-07-17T14:14:37.127+00:00",
      "msg": "update team info failed"
  }
  ```

  

## 6.增加团队成员

+ 接口：api/team/{team_id}/member

+ 方法：POST

+ 调用实例：

  ```json
  //api/team/1/member
  [
      2,
      1
  ]
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T15:23:06.096+00:00",
      "msg": "all set",
      "data": null
  }
  //失败
  {
      "status": 11,
      "date": "2020-07-17T15:23:30.293+00:00",
      "msg": "operation failed",
      "data": [
          0,
          1
      ]
  }
  ```

  

## 7.删除团队成员

+ 接口：api/team/{team_id}/member

+ 方法：DELETE

+ 调用实例：

  ```json
  //api/team/1/member
  [
      2,
      1
  ]
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T15:25:07.028+00:00",
      "msg": "all set",
      "data": null
  }
  //失败
  {
      "status": 11,
      "date": "2020-07-17T15:23:30.293+00:00",
      "msg": "operation failed",
      "data": [
          0
      ]
  }
  ```


## 8.根据实训id获取团队

+ 接口：api/team/train/{train_id}

+ 方法：GET

+ 参数：offset ，page

+ 调用实例：

  ```json
  //api/team/train/2?page=1&offset=8
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-22T16:50:59.604+00:00",
      "msg": "success",
      "total_rows": 1,
      "data": [
          {
              "id": 2,
              "name": "小章鱼",
              "avatar": null,
              "evaluation": "良好",
              "size": 1,
              "train_id": 2,
              "train_name": "中级项目实训",
              "project_id": 2,
              "project_name": "大二实训",
              "repo_url": "https://www.baidu.com/",
              "team_grade": 94,
              "team_master_id": 2,
              "team_master": "刘恒"
          }
      ]
  }
  //失败
  ```

## 9.根据项目id获取团队

+ 接口：api/team/project/{project_id}

+ 方法：GET

+ 参数：offset ，page

+ 调用实例：

  ```json
  //api/team/project/2?page=1&offset=8
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-23T17:07:51.375+00:00",
      "msg": "success",
      "total_rows": 1,
      "data": [
          {
              "id": 2,
              "name": "小章鱼",
              "avatar": "/storage/2020/7/24/33fa7170-861a-4914-be0a-eb3b236b0964.png",
              "evaluation": "良好",
              "size": 1,
              "repo_url": "https://www.baidu.com/",
              "team_grade": 94,
              "train_id": 2,
              "train_name": "中级项目实训",
              "project_id": 2,
              "project_name": "大二实训",
              "team_master_id": 2,
              "team_master_name": "刘恒"
          }
      ]
  }
  //失败
  ```

## 10. 根据实训id和项目id获取团队

+ 接口：api/team/train_project

+ 方法：GET

+ 参数：offset ，page，project_id，train_id

+ 调用实例：

  ```json
  //api/team/train_project?page=1&offset=8&train_id=1&project_id=1
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-23T17:08:58.049+00:00",
      "msg": "success",
      "total_rows": 1,
      "data": [
          {
              "id": 1,
              "name": "章鱼鸽",
              "avatar": "/storage/2020/7/23/38c76975-4632-4946-be1f-437902a1fb22.",
              "evaluation": "优秀",
              "size": 3,
              "repo_url": "https://www.baidu.com/",
              "team_grade": 99,
              "train_id": 1,
              "train_name": "初级项目实训",
              "project_id": 1,
              "project_name": "大一实训",
              "team_master_id": 1,
              "team_master_name": "李豪四"
          }
      ]
  }
  //失败
  ```



## 11. 根据用户id获取团队

+ 接口：api/team/user/{user_id}

+ 方法：GET

+ 参数：offset ，page

+ 调用实例：

  ```json
  //api/team/user/1?page=1&offset=8
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-24T07:38:47.580+00:00",
      "msg": "success",
      "total_rows": 1,
      "data": [
          {
              "id": 1,
              "name": "章鱼鸽",
              "avatar": "/storage/2020/7/23/38c76975-4632-4946-be1f-437902a1fb22.",
              "evaluation": "优秀",
              "size": 4,
              "repo_url": "https://www.baidu.com/",
              "team_grade": 99,
              "train_id": 1,
              "train_name": "初级项目实训",
              "project_id": 1,
              "project_name": "大一实训",
              "team_master_id": 1,
              "team_master_name": "李豪四"
          }
      ]
  }
  //失败
  ```



## 12.获取团队成员信息

+ 接口：api/team/{team_id}/member

+ 方法：GET

+ 调用实例：

  ```json
  //api/team/4/member
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T15:37:41.609+00:00",
      "msg": "success",
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
          }
      ]
  }
  //失败
  ```



## 13.上传团队头像

+ 接口：api/team/{team_id}/uploadAvatar

+ 方法：POST

+ 参数：文件

+ 调用实例：

  ```json
  //api/team/1/uploadAvatar
  文件 key：file
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-22T17:09:13.955+00:00",
      "msg": "upload Avatar successfully"
  }
  //失败
  ```



# 流程模块（ProcessDetailsController）

## 1.创建流程

+ 接口：api/process

+ 方法：POST

+ 调用实例：

  ```json
   {
      "train_id": 1,
      "start_time": "2020-07-17T16:00:00.000+00:00",
      "end_time": "2020-09-09T16:00:00.000+00:00"
  }
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T16:26:50.983+00:00",
      "msg": "create process successfully"
  }
  //失败
  {
      "status": 18,
      "date": "2020-07-17T16:27:32.657+00:00",
      "msg": "create process failed"
  }
  ```

  

## 2.根据id删除流程

+ 接口：api/process/{process_id}

+ 方法：DELETE

+ 调用实例：

  ```json
  //api/process/6
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T16:32:45.114+00:00",
      "msg": "delete process successfully"
  }
  //失败
  ```


## 3.根据id获取流程

+ 接口：api/process

+ 方法：GET

+ 调用实例：

  ```json
  //api/process/4
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T16:36:58.279+00:00",
      "msg": "success",
      "data": {
          "id": 4,
          "train_id": 1,
          "start_time": "2020-07-16T16:00:00.000+00:00",
          "end_time": "2020-09-08T16:00:00.000+00:00"
      }
  }
  //失败
  {
      "status": 15,
      "date": "2020-07-17T16:29:13.959+00:00",
      "msg": "get process failed",
      "data": null
  }
  ```


## 4.根据id更新流程

+ 接口：api/process

+ 方法：PUT

+ 调用实例：

  ```json
   {
      "id": 4,
      "train_id": 1,
      "start_time": "2020-07-17T16:00:00.000+00:00",
      "end_time": "2020-09-20T16:00:00.000+00:00"
  }
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T16:38:41.682+00:00",
      "msg": "update process successfully"
  }
  //失败
  {
      "status": 0,
      "date": "2020-07-17T16:39:19.346+00:00",
      "msg": "update process successfully"
  }
  ```

  

## 5.在流程中添加事件

+ 接口：api/process_event

+ 参数：process_id 、event_id

+ 方法：PUT

+ 调用实例：

  ```json
  //api/process_event?process_id=1&event_id=1
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T16:48:20.430+00:00",
      "msg": "add event successfully"
  }
  //失败
  {
      "status": 18,
      "date": "2020-07-17T16:50:59.501+00:00",
      "msg": "add event failed"
  }
  ```

  

## 6.在流程中删除事件

+ 接口：api/process_event

+ 参数：process_id 、event_id

+ 方法：DELETE

+ 调用实例：

  ```json
  //api/process_event?process_id=1&event_id=1
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T16:57:57.309+00:00",
      "msg": "remove event successfully"
  }
  //失败
  ```

  

## 7.根据实训id获取流程

+ 接口：api/train_process/{train_id}

+ 方法：GET

+ 调用实例：

  ```json
  //api/train_process/1
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T17:03:35.310+00:00",
      "msg": "success",
      "data": [
          {
              "id": 4,
              "train_id": 1,
              "start_time": "2020-07-16T16:00:00.000+00:00",
              "end_time": "2020-09-19T16:00:00.000+00:00"
          }
      ]
  }
  //失败
  ```

  

## 8.移除特定实训中的流程

+ 接口：api/train_process/{train_id}

+ 方法：DELETE

+ 调用实例：

  ```json
  //api/train_process/1
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T17:12:37.232+00:00",
      "msg": "remove process successfully"
  }
  //失败
  ```

  

# 事件模块（EventDetailsController）

## 1.创建事件

+ 接口：api/event

+ 方法：POST

+ 调用实例：

  ```json
  {
      "start_time": "2020-07-14T16:00:00.000+00:00",
      "end_time": "2020-07-14T16:00:00.000+00:00",
      "content": "提交文档",
      "person_or_team": true
  }
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T17:28:51.048+00:00",
      "msg": "add event successfully"
  }
  //失败
  {
      "status": 11,
      "date": "2020-07-17T17:21:40.747+00:00",
      "msg": "event add failed"
  }
  ```

  

## 2.根据id删除event

+ 接口：api/event/{event_id}

+ 方法：DELETE

+ 调用实例：

  ```json
  //api/event/2
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T17:29:33.969+00:00",
      "msg": "delete event successfully"
  }
  //失败
  {
      "status": 17,
      "date": "2020-07-17T17:30:12.115+00:00",
      "msg": "event delete failed"
  }
  ```

  

## 3.更新事件信息

+ 接口：api/event

+ 方法：PUT

+ 调用实例：

  ```json
  {
      "id":1,
      "start_time": "2020-07-14T16:00:00.000+00:00",
      "end_time": "2020-07-19T16:00:00.000+00:00",
      "content": "提交文档",
      "person_or_team": true
  }
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T17:30:50.886+00:00",
      "msg": "update event successfully"
  }
  //失败
  ```

  

## 4.根据id获取事件

+ 接口：api/event/{event_id}

+ 方法：GET

+ 调用实例：

  ```json
  //api/event/1
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-17T17:33:25.691+00:00",
      "msg": "success",
      "data": {
          "id": 1,
          "content": "提交文档",
          "start_time": "2020-07-13T16:00:00.000+00:00",
          "end_time": "2020-07-18T16:00:00.000+00:00",
          "person_or_team": true
      }
  }
  //失败
  {
      "status": 15,
      "date": "2020-07-17T17:33:53.600+00:00",
      "msg": "get event failed",
      "data": null
  }
  ```



## 5.分页获取所有事件

+ 接口：api/event

+ 方法：GET

+ 调用实例：

  ```json
  //api/event?page=1&offset=4
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-19T12:52:34.538+00:00",
      "msg": "success",
      "total_rows": 4,
      "data": [
          {
              "id": 1,
              "content": "注册成功",
              "start_time": "2020-07-15T16:00:00.000+00:00",
              "end_time": "2020-07-15T16:00:00.000+00:00",
              "person_or_team": false
          },
          {
              "id": 2,
              "content": "选择项目",
              "start_time": "2020-07-15T16:00:00.000+00:00",
              "end_time": "2020-07-15T16:00:00.000+00:00",
              "person_or_team": false
          },
          {
              "id": 3,
              "content": "团队破冰",
              "start_time": "2020-07-15T16:00:00.000+00:00",
              "end_time": "2020-07-15T16:00:00.000+00:00",
              "person_or_team": false
          },
          {
              "id": 4,
              "content": "发布项目",
              "start_time": "2020-07-15T16:00:00.000+00:00",
              "end_time": "2020-07-15T16:00:00.000+00:00",
              "person_or_team": false
          }
      ]
  }
  //失败
  ```



# 记录模块（RecordDetailsController）

## 1.创建记录

+ 接口：api/record

+ 方法：POST

+ 调用实例：

  ```json
   {
      "train_id": 1,
      "team_id": 1,
      "user_id": 1,
      "process_id": 1,
      "event_id": 1,
      "assignments_lib": "www.baidu.com"
  }
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-22T12:30:45.747+00:00",
      "msg": "create record successfully"
  }
  //失败
  {
      "status": 18,
      "date": "2020-07-22T12:31:13.889+00:00",
      "msg": "create record failed"
  }
  ```

  

## 2.通过id获取记录

+ 接口：api/record

+ 方法：GET

+ 调用实例：

  ```json
  //api/record/1
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-22T12:33:49.938+00:00",
      "msg": "success",
      "data": {
          "id": 1,
          "train_id": 1,
          "team_id": 1,
          "user_id": 1,
          "process_id": 1,
          "event_id": 1,
          "assignments_lib": "www.baidu.com"
      }
  }
  //失败
  {
      "status": 15,
      "date": "2020-07-22T12:34:41.794+00:00",
      "msg": "get record failed",
      "data": null
  }
  ```

  

## 3.通过实训id和用户id获取记录

+ 接口：api/record

+ 方法：GET

+ 参数：train_id,user_id

+ 调用实例：

  ```json
  //api/record/user?train_id=2&user_id=2
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-22T12:35:05.545+00:00",
      "msg": "success",
      "data": [
          {
              "id": 2,
              "train_id": 2,
              "team_id": 2,
              "user_id": 2,
              "process_id": 2,
              "event_id": 2,
              "assignments_lib": "www.baidu.com"
          }
      ]
  }
  //失败
  
  ```

  

## 4.通过团队id记录

+ 接口：api/record/team/{team_id}

+ 方法：GET

+ 调用实例：

  ```json
  //api/record/team/1
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-22T12:51:43.602+00:00",
      "msg": "success",
      "data": [
          {
              "id": 1,
              "train_id": 1,
              "team_id": 1,
              "user_id": 1,
              "process_id": 1,
              "event_id": 1,
              "assignments_lib": "www.baidu.com"
          },
          {
              "id": 8,
              "train_id": 1,
              "team_id": 1,
              "user_id": 1,
              "process_id": 1,
              "event_id": 1,
              "assignments_lib": "{\"assignments\":[]}"
          }
      ]
  }
  //失败
  
  ```

  

## 5.上传文件

+ 接口：api/record/{record_id}/file

+ 方法：POST

+ 参数：文件

+ 调用实例：

  ```json
  //api/record/8/file
  文件key为file
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-22T13:00:04.438+00:00",
      "msg": "upload resource files successfully"
  }
  //失败
  {
      "status": 16,
      "date": "2020-07-22T13:02:12.474+00:00",
      "msg": "upload resource files failed"
  }
  ```

  

## 6.删除记录相关文件

+ 接口：api/record

+ 方法：POST

+ 调用实例：

  ```json
  {
      "fileName":"be2dd4db-ba70-472f-ae47-d2c9ef5eddef.png",
      "filePath":"C:\\cptmp\\private\\2020\\7\\22\\be2dd4db-ba70-472f-ae47-d2c9ef5eddef.png",
      "fileSize":175186,
      "fileType":"image/png",
      "fileUrl":"/api/storage/2020/7/22/be2dd4db-ba70-472f-ae47-d2c9ef5eddef.png",
      "gmtCreate":1595422804089,
      "originName":"Logo4-5 (1).png"
  }
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-22T13:05:04.216+00:00",
      "msg": "remove resource files success"
  }
  //失败
  {
      "status": 16,
      "date": "2020-07-22T13:03:59.791+00:00",
      "msg": "remove resource files failed"
  }
  ```

  

## 7.添加人脸信息

+ 接口：api/face/{user_id}

+ 方法：POST

+ 参数：人脸图片（key=“file”）

+ 调用实例：

  ```json
  //api/face/4
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-24T16:22:39.683+00:00",
      "msg": "add face info successfully"
  }
  //失败
  {
      "status": 18,
      "date": "2020-07-24T16:52:47.627+00:00",
      "msg": "add face info failed"
  }
  ```

## 8.人脸识别签到

+ 接口：api/face/{user_id}

+ 方法：POST

+ 参数：人脸图片（key=“file”），user_id，team_id，train_id

+ 调用实例：

  ```json
  //api/signin/face?user_id=4&team_id=4&train_id=4
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-24T16:38:57.734+00:00",
      "msg": "sign in successfully"
  }
  //识别失败但未发生异常
  {
      "status": 0,
      "date": "2020-07-24T16:55:58.019+00:00",
      "msg": "Identification failed or User is not exist"
  }
  //失败
  {
      "status": 19,
      "date": "2020-07-24T16:54:31.315+00:00",
      "msg": "sign in failed"
  }
  ```



## 9.定位签到

+ 接口：api/signin/gps

+ 方法：POST

+ 调用实例：

  ```json
  {
      "user_id":4,
      "team_id":4,
      "train_id":1,
      "longitude":114.3574959,
      "latitude":30.5332712
  }
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-24T18:50:45.880+00:00",
      "msg": "sign in successfully"
  }
  //未在指定范围
  {
      "status": 0,
      "date": "2020-07-24T18:52:21.794+00:00",
      "msg": "You are not in the specified location range"
  }
  //失败
  {
      "status": 19,
      "date": "2020-07-24T18:52:44.747+00:00",
      "msg": "sign in failed"
  }
  ```



# 提醒模块（NoticeDetailsController）

## 1.创建提示

+ 接口：api/notice

+ 方法：POST

+ 调用实例：

  ```json
   {
      "sender_id": 1,
      "receiver_id": 5,
      "team_id": 1,
      "content": "创建团队",
      "is_read": false,
      "type": "普通"
  }
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-22T13:42:28.735+00:00",
      "msg": "create notice successfully"
  }
  //失败
  {
      "status": 18,
      "date": "2020-07-22T13:42:53.215+00:00",
      "msg": "create notice failed"
  }
  ```

  

## 2.通过接收者id获取通知信息

+ 接口：api/notice/receiver/{receiver_id}

+ 方法：GET

+ 参数：offset、page

+ 调用实例：

  ```json
  //api/notice/receiver/5?offset=8&page=1
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-22T13:31:17.335+00:00",
      "msg": "success",
      "total_rows": 1,
      "data": [
          {
              "id": 1,
              "sender_id": 1,
              "receiver_id": 5,
              "team_id": 1,
              "content": "创建团队",
              "is_read": true,
              "type": "普通"
          }
      ]
  }
  //失败
  {
      "status": 15,
      "date": "2020-07-22T13:29:00.589+00:00",
      "msg": "get notice failed",
      "total_rows":0,
      "data": null
  }
  ```

  

## 3.通过id获取通知信息

+ 接口：api/notice/{notice_id}

+ 方法：GET

+ 调用实例：

  ```json
  //api/notice/1
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-22T13:19:13.096+00:00",
      "msg": "success",
      "data": {
          "id": 1,
          "sender_id": 1,
          "receiver_id": 5,
          "team_id": 1,
          "content": "创建团队",
          "is_read": true,
          "type": "普通"
      }
  }
  //失败
  {
      "status": 15,
      "date": "2020-07-22T13:29:00.589+00:00",
      "msg": "get notice failed",
      "data": null
  }
  ```

  

## 4.通过团队id获取通知信息

+ 接口：api/notice/team/{team_id}

+ 方法：POST

+ 参数：offset、page

+ 调用实例：

  ```json
  //api/notice/team/1?offset=8&page=1
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-22T13:45:33.952+00:00",
      "msg": "success",
    "total_rows": 2,
      "data": [
          {
              "id": 1,
              "content": "创建团队",
              "sender_id": 1,
              "receiver_id": 5,
              "team_id": 1,
              "type": "普通",
              "is_read": true
          },
          {
              "id": 8,
              "content": "创建团队",
              "sender_id": 1,
              "receiver_id": 5,
              "team_id": 1,
              "type": "MESSAGE_NOTICE",
              "is_read": false
          }
      ]
  }
  //失败
  
  ```
  
  

## 5.根据id删除通知消息

+ 接口：api/notice/{notice_id}

+ 方法：DELETE

+ 调用实例：

  ```json
  //api/notice/8
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-22T14:03:50.541+00:00",
      "msg": "remove notice successfully"
}
  //失败
  
  ```
  
  

## 6.更新通知消息

+ 接口：api/notice

+ 方法：PUT

+ 调用实例：

  ```json
   {
      "id":1,
      "sender_id": 2,
      "receiver_id": 3,
      "team_id": 1,
      "content": "提交文档",
      "is_read": false,
      "type": "普通"
  }
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-22T14:11:35.501+00:00",
      "msg": "update notice successfully"
  }
  //失败
  {
      "status": 10,
      "date": "2020-07-22T14:10:55.746+00:00",
      "msg": "update notice failed"
  }
  ```

  

## 7.发送签到通知

+ 接口：api/notice/signin

+ 方法：POST

+ 参数：sender_id、train_id、type

+ 调用实例：

  ```json
  //api/notice/signin?sender_id=1&train_id=4&type=识别
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-25T06:16:45.197+00:00",
      "msg": "send message successfully"
  }
  //失败
  {
      "status": 18,
      "date": "2020-07-25T06:18:28.186+00:00",
      "msg": "send message failed"
  }
  ```

  

# 招聘模块（RecruitmentDetailsController）

## 1.创建招聘信息

+ 接口：api/Recruitment

+ 方法：POST

+ 调用实例：

  ```json
   {
      "photo": "//www.baidu.com/",
      "title": "腾讯",
      "start_time": "2020-07-19T16:00:00.000+00:00",
      "end_time": "2020-07-24T16:00:00.000+00:00",
      "website_url": "https://www.baidu.com/"
  }
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-24T13:43:18.968+00:00",
      "msg": "add Recruitment successfully"
  }
  //失败
  {
      "status": 18,
      "date": "2020-07-23T17:41:39.215+00:00",
      "msg": "add Recruitment failed"
  }
  ```



## 2.根据id获取招聘信息

+ 接口：api/recruitment/{recruitment_id}

+ 方法：GET

+ 调用实例：

  ```json
  //api/recruitment/1
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-23T17:40:15.482+00:00",
      "msg": "success",
      "data": {
          "id": 1,
          "photo": "https://www.baidu.com/",
          "title": "百度",
          "start_time": "2020-07-19T16:00:00.000+00:00",
          "end_time": "2020-07-24T16:00:00.000+00:00",
          "website_url": "https://www.baidu.com/"
      }
  }
  //失败
  {
      "status": 15,
      "date": "2020-07-23T17:40:28.337+00:00",
      "msg": "get info failed",
      "data": null
  }
  ```



## 3.获取所有招聘信息

+ 接口：api/recruitment

+ 方法：GET

+ 参数：offset、page

+ 调用实例：

  ```json
  //api/recruitment?offset=3&page=1
  ```

+ 返回实例：

  ```json
  //成功
  {
      "status": 0,
      "date": "2020-07-24T05:45:14.271+00:00",
      "msg": "success",
      "total_rows": 5,
      "data": [
          {
              "id": 1,
              "photo": "https://www.baidu.com/",
              "title": "百度",
              "start_time": "2020-07-19T16:00:00.000+00:00",
              "end_time": "2020-07-24T16:00:00.000+00:00",
              "website_url": "https://www.baidu.com/"
          },
          {
              "id": 2,
              "photo": "https://www.baidu.com/",
              "title": "阿里",
              "start_time": "2020-07-19T16:00:00.000+00:00",
              "end_time": "2020-07-24T16:00:00.000+00:00",
              "website_url": "https://www.baidu.com/"
          },
          {
              "id": 3,
              "photo": "https://www.baidu.com/",
              "title": "清华大学",
              "start_time": "2020-07-19T16:00:00.000+00:00",
              "end_time": "2020-07-24T16:00:00.000+00:00",
              "website_url": "https://www.baidu.com/"
          }
      ]
  }
  //失败
  {
      "status": 15,
      "date": "2020-07-23T17:40:28.337+00:00",
      "msg": "get info failed",
      "data": null
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

# GitHubApiController
## /api/team/{id}/contributor/statics
+ 方法：GET
+ 说明：通过teamId得到团队贡献数据
+ 返回实例：
``` 
// 成功
{
    "status": 0,
    "date": "2020-07-20T07:27:27.055+00:00",
    "msg": "success",
    "contributor_dtolist": [
        {
            "total": 10,
            "total_additions": 229,
            "total_deletions": 469,
            "weeks": [
                {
                    "w": "1970-01-19T10:35:02.400+00:00",
                    "a": 0,
                    "d": 0,
                    "c": 0
                },
                {
                    "w": "1970-01-19T10:45:07.200+00:00",
                    "a": 229,
                    "d": 469,
                    "c": 10
                },
                {
                    "w": "1970-01-19T10:55:12.000+00:00",
                    "a": 0,
                    "d": 0,
                    "c": 0
                },
                {
                    "w": "1970-01-19T11:05:16.800+00:00",
                    "a": 0,
                    "d": 0,
                    "c": 0
                }
            ],
            "author": {
                "login": "ExerciseBook",
                "id": 6327311,
                "node_id": "MDQ6VXNlcjYzMjczMTE=",
                "avatar_url": "https://avatars0.githubusercontent.com/u/6327311?v=4",
                "gravatar_id": "",
                "url": "https://api.github.com/users/ExerciseBook",
                "html_url": "https://github.com/ExerciseBook",
                "followers_url": "https://api.github.com/users/ExerciseBook/followers",
                "following_url": "https://api.github.com/users/ExerciseBook/following{/other_user}",
                "gists_url": "https://api.github.com/users/ExerciseBook/gists{/gist_id}",
                "starred_url": "https://api.github.com/users/ExerciseBook/starred{/owner}{/repo}",
                "subscriptions_url": "https://api.github.com/users/ExerciseBook/subscriptions",
                "organizations_url": "https://api.github.com/users/ExerciseBook/orgs",
                "repos_url": "https://api.github.com/users/ExerciseBook/repos",
                "events_url": "https://api.github.com/users/ExerciseBook/events{/privacy}",
                "received_events_url": "https://api.github.com/users/ExerciseBook/received_events",
                "type": "User",
                "site_admin": false
            }
        },
        {
            "total": 42,
            "total_additions": 3517,
            "total_deletions": 829,
            "weeks": [
                {
                    "w": "1970-01-19T10:35:02.400+00:00",
                    "a": 0,
                    "d": 0,
                    "c": 0
                },
                {
                    "w": "1970-01-19T10:45:07.200+00:00",
                    "a": 283,
                    "d": 188,
                    "c": 4
                },
                {
                    "w": "1970-01-19T10:55:12.000+00:00",
                    "a": 3052,
                    "d": 622,
                    "c": 34
                },
                {
                    "w": "1970-01-19T11:05:16.800+00:00",
                    "a": 182,
                    "d": 19,
                    "c": 4
                }
            ],
            "author": {
                "login": "Arthur-0",
                "id": 61322473,
                "node_id": "MDQ6VXNlcjYxMzIyNDcz",
                "avatar_url": "https://avatars3.githubusercontent.com/u/61322473?v=4",
                "gravatar_id": "",
                "url": "https://api.github.com/users/Arthur-0",
                "html_url": "https://github.com/Arthur-0",
                "followers_url": "https://api.github.com/users/Arthur-0/followers",
                "following_url": "https://api.github.com/users/Arthur-0/following{/other_user}",
                "gists_url": "https://api.github.com/users/Arthur-0/gists{/gist_id}",
                "starred_url": "https://api.github.com/users/Arthur-0/starred{/owner}{/repo}",
                "subscriptions_url": "https://api.github.com/users/Arthur-0/subscriptions",
                "organizations_url": "https://api.github.com/users/Arthur-0/orgs",
                "repos_url": "https://api.github.com/users/Arthur-0/repos",
                "events_url": "https://api.github.com/users/Arthur-0/events{/privacy}",
                "received_events_url": "https://api.github.com/users/Arthur-0/received_events",
                "type": "User",
                "site_admin": false
            }
        },
        {
            "total": 43,
            "total_additions": 7356,
            "total_deletions": 4180,
            "weeks": [
                {
                    "w": "1970-01-19T10:35:02.400+00:00",
                    "a": 0,
                    "d": 0,
                    "c": 0
                },
                {
                    "w": "1970-01-19T10:45:07.200+00:00",
                    "a": 2261,
                    "d": 810,
                    "c": 18
                },
                {
                    "w": "1970-01-19T10:55:12.000+00:00",
                    "a": 4248,
                    "d": 2969,
                    "c": 20
                },
                {
                    "w": "1970-01-19T11:05:16.800+00:00",
                    "a": 847,
                    "d": 401,
                    "c": 5
                }
            ],
            "author": {
                "login": "liguopeng0923",
                "id": 61257834,
                "node_id": "MDQ6VXNlcjYxMjU3ODM0",
                "avatar_url": "https://avatars2.githubusercontent.com/u/61257834?v=4",
                "gravatar_id": "",
                "url": "https://api.github.com/users/liguopeng0923",
                "html_url": "https://github.com/liguopeng0923",
                "followers_url": "https://api.github.com/users/liguopeng0923/followers",
                "following_url": "https://api.github.com/users/liguopeng0923/following{/other_user}",
                "gists_url": "https://api.github.com/users/liguopeng0923/gists{/gist_id}",
                "starred_url": "https://api.github.com/users/liguopeng0923/starred{/owner}{/repo}",
                "subscriptions_url": "https://api.github.com/users/liguopeng0923/subscriptions",
                "organizations_url": "https://api.github.com/users/liguopeng0923/orgs",
                "repos_url": "https://api.github.com/users/liguopeng0923/repos",
                "events_url": "https://api.github.com/users/liguopeng0923/events{/privacy}",
                "received_events_url": "https://api.github.com/users/liguopeng0923/received_events",
                "type": "User",
                "site_admin": false
            }
        },
        {
            "total": 107,
            "total_additions": 8984,
            "total_deletions": 4606,
            "weeks": [
                {
                    "w": "1970-01-19T10:35:02.400+00:00",
                    "a": 0,
                    "d": 0,
                    "c": 0
                },
                {
                    "w": "1970-01-19T10:45:07.200+00:00",
                    "a": 3888,
                    "d": 2464,
                    "c": 22
                },
                {
                    "w": "1970-01-19T10:55:12.000+00:00",
                    "a": 4881,
                    "d": 2105,
                    "c": 80
                },
                {
                    "w": "1970-01-19T11:05:16.800+00:00",
                    "a": 215,
                    "d": 37,
                    "c": 5
                }
            ],
            "author": {
                "login": "G-H-Li",
                "id": 45568133,
                "node_id": "MDQ6VXNlcjQ1NTY4MTMz",
                "avatar_url": "https://avatars0.githubusercontent.com/u/45568133?v=4",
                "gravatar_id": "",
                "url": "https://api.github.com/users/G-H-Li",
                "html_url": "https://github.com/G-H-Li",
                "followers_url": "https://api.github.com/users/G-H-Li/followers",
                "following_url": "https://api.github.com/users/G-H-Li/following{/other_user}",
                "gists_url": "https://api.github.com/users/G-H-Li/gists{/gist_id}",
                "starred_url": "https://api.github.com/users/G-H-Li/starred{/owner}{/repo}",
                "subscriptions_url": "https://api.github.com/users/G-H-Li/subscriptions",
                "organizations_url": "https://api.github.com/users/G-H-Li/orgs",
                "repos_url": "https://api.github.com/users/G-H-Li/repos",
                "events_url": "https://api.github.com/users/G-H-Li/events{/privacy}",
                "received_events_url": "https://api.github.com/users/G-H-Li/received_events",
                "type": "User",
                "site_admin": false
            }
        },
        {
            "total": 191,
            "total_additions": 11678,
            "total_deletions": 6369,
            "weeks": [
                {
                    "w": "1970-01-19T10:35:02.400+00:00",
                    "a": 2,
                    "d": 0,
                    "c": 5
                },
                {
                    "w": "1970-01-19T10:45:07.200+00:00",
                    "a": 6582,
                    "d": 2768,
                    "c": 82
                },
                {
                    "w": "1970-01-19T10:55:12.000+00:00",
                    "a": 5074,
                    "d": 3593,
                    "c": 102
                },
                {
                    "w": "1970-01-19T11:05:16.800+00:00",
                    "a": 20,
                    "d": 8,
                    "c": 2
                }
            ],
            "author": {
                "login": "anlowee",
                "id": 40865608,
                "node_id": "MDQ6VXNlcjQwODY1NjA4",
                "avatar_url": "https://avatars2.githubusercontent.com/u/40865608?v=4",
                "gravatar_id": "",
                "url": "https://api.github.com/users/anlowee",
                "html_url": "https://github.com/anlowee",
                "followers_url": "https://api.github.com/users/anlowee/followers",
                "following_url": "https://api.github.com/users/anlowee/following{/other_user}",
                "gists_url": "https://api.github.com/users/anlowee/gists{/gist_id}",
                "starred_url": "https://api.github.com/users/anlowee/starred{/owner}{/repo}",
                "subscriptions_url": "https://api.github.com/users/anlowee/subscriptions",
                "organizations_url": "https://api.github.com/users/anlowee/orgs",
                "repos_url": "https://api.github.com/users/anlowee/repos",
                "events_url": "https://api.github.com/users/anlowee/events{/privacy}",
                "received_events_url": "https://api.github.com/users/anlowee/received_events",
                "type": "User",
                "site_admin": false
            }
        }
    ]
}
// 失败
{
    "status": 15,
    "date": "2020-07-20T07:31:39.352+00:00",
    "msg": "get contributor info failed",
    "contributor_dtolist": null
}
```
