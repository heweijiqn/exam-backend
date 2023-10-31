## 系统简介
* 采用前后端分离的模式，前端框架选型vue-element-admin，前端代码地址：
* https://github.com/heweijiqn/exam-front 
* 后端采用Spring Boot、Spring Cloud & Alibaba、Mybatis-plus。
* 注册中心、配置中心选型Nacos，权限认证使用OAuth2。
* 主要用了客户端调用工具OpenFeign、服务降级Sentinel、服务网关APIGateway

## 系统模块
~~~
exam-ning                               
├── exam-ning-springcloud-api           // 公共模块
├── exam-ning-springcloud-auth          // 认证中心[9527]
├── exam-ning-springcloud-gateway       // 网关模块[8080]
├── exam-ning-springcloud-system-exam   // 考试模块[9201]
├── exam-ning-springcloud-system-user   // 系统模块[9202]
~~~

## 系统功能
1. 用户管理：添加用户及用户基本信息的维护。
2. 角色管理：角色信息的维护及对应权限范围的控制。
3. 菜单管理：菜单权限信息的维护。
4. 试卷管理：添加试卷、预览试卷、发布试卷、删除试卷。
5. 试题管理：试题的管理，分为单选、多线、填空、判断、简答5钟题型。
6. 考试管理：考试、阅卷（客观题自动阅卷）、查看错题。

## 演示图
![image](https://github.com/heweijiqn/exam-backend/assets/95403358/70361109-faeb-40c7-aa4a-978787bc3d3a)
![image](https://github.com/heweijiqn/exam-backend/assets/95403358/866c0513-29e2-4935-a4cd-c2fe3187d7ca)
![image](https://github.com/heweijiqn/exam-backend/assets/95403358/eda3cb08-0f55-4c9a-b9f2-0f6d76a167a2)
![image](https://github.com/heweijiqn/exam-backend/assets/95403358/7dd73a87-6186-43df-abac-b830c3d62dd5)
![image](https://github.com/heweijiqn/exam-backend/assets/95403358/d21509ff-dac4-4d73-bd88-8abee4936bbf)
![image](https://github.com/heweijiqn/exam-backend/assets/95403358/b4489084-ac5d-4e17-a4d7-04da6374a8ed)
![image](https://github.com/heweijiqn/exam-backend/assets/95403358/946eb8d7-277d-486a-b8f1-da04ca76b464)
![image](https://github.com/heweijiqn/exam-backend/assets/95403358/e87c1c31-65bc-4598-a3df-7c002554f67f)








## 部署文档
1. 初始化数据库，分别创建 exam-ning-springcloud-exam 和 exam-ning-springcloud-user 数据库，导入对应sql文件。 
2. 启动Nacos，下载Nacos，运行startup.cmd。
```
【注意事项】
1、注意下 nacos 的版本，本项目依赖升级后 nacos-client 的版本是 2.0.3。
2. 启动Redis。
3. 依次启动exam-ning-springcloud-gateway、exam-ning-springcloud-auth、exam-ning-springcloud-system-exam、exam-ning-springcloud-system-user。
4. 浏览器访问http://localhost:9528/，账号密码 ： system/system。
5. 接口文档地址：http://127.0.0.1:8080/swagger-ui.html

