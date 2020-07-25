CPTMP-Backend

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
[目前已和前端对接的API说明](ApiDoc.md)

# License

    College Practice Training Management Platform
    Copyright (C) 2020  Octopigeon Team
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.
    
    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
