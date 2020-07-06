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
更新时间：2020/7/6

## /pigeon
+ HTTP方法：GET
+ 返回类型：/
+ 说明：仅用于测试环境，两个参数cmd和arg，支持添加一个pigeon，查询所有pigeon，删除所有pigeon
+ 调用示例：/pigeon?cmd=add&arg=wxc;/pigeon?cmd=list;/pigeon?cmd=remove;
