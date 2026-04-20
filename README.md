# 评论系统

### 项目简介
* 本项目基于Java springboot搭建，maven管理。

* 前端采用原生HTML+JS

* 本项目只允许管理员admin发帖，其余权限只能看贴和发表评论

* 用vscode编写，插件Extension Pack for Java、Spring Boot Extension Pack。

* CZ.Quectel工作期间2026年4月17日

* 2026年4月19日完成前后端分离架构，添加部署文件。部署Ubuntu成功

* 2026年4月21日05点21分完成删除功能，添加全局异常处理。添加业务逻辑分层目录

### deployment record
* 添加nginx.conf、Dockerfile、docker-compose.yaml文件用maven打包jar文件，上传到gitee仓库

* nginx.conf反向代理加接口路径/api，Dockerfile分打包和运行两个阶段执行，打包构建jar包，运行调用jar文件

* docker-compose.yaml文件只要后台backend和nginx两个容器，后台连接远程数据库，nginx显示页面

* 数据库root用户要赋予远程调用权限


*For further reference, please consider the following sections:*👇

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)