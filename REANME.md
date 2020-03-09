# 论坛项目

# 资源
[spring 组件](https://spring.io/guides)

[Thymeleaf](https://spring.io/guides/gs/serving-web-content/)

# 整合Thymeleaf
- 引入依赖
- 创建Controller
- 创建index.html

# 使用bootstrap前端模板
- [官方下载资源](https://v3.bootcss.com/getting-started/)，并拷贝至项目static文件夹中
- html页面在head中引入css、js文件
```html
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/bootstrap-theme.min.css">
<script src="js/bootstrap.min.js" type="application/javascript"></script>
```
- 寻找对应组件，将代码复制后修改使用。[[组件链接]](https://v3.bootcss.com/components/)

# github第三方登录
- Creating an OAuth App.[[使用说明]](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)
- Authorizing OAuth Apps.[[使用说明]](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)
### 创建OAuth APPs.[链接](https://github.com/settings/applications/new)
### 一、Get请求Github用户身份
```html
<a href="https://github.com/login/oauth/authorize?client_id=2635142931332aac30d8&redirect_uri=http://localhost:8080/callback&scope=user&state=1">登录</a>
```
### 二、Gitbub重定向回callback，Controller接收参数获取code
### 三、发起post请求获取AccessToken
- 将多参数进行对象封装（DTO）
- 将对象转化为Json字符串
```
JSON.toJSONString(accessTokenDTO)
```
- 使用okhttp工具发送post请求
### 四、发起get请求获取GithubUser
- 使用okhttp工具发送get请求
- 将Json字符串转化为相应的对象
```
JSON.parseObject(string, GithubUser.class)
```
### 五、将user保存至数据库，实现登录状态持久化
- 构建一个user实体，创建user对象插入数据库，并保存至cookie，最后重定向首页
- 首页控制器获取所有cookie，遍历cookie集合，发现key后，从数据库进行匹配
- 如果存在，从数据库返回一个user对象，保存至session域中
- 首页页面通过thymeleaf标签进行获取session域中的数据。

# 将java代码中的硬编码放入application.properties配置文件中
- 使用@Value(${key})注解读取
- 配置文件中通过key=value的形式保存，注意value不需要单双引号。

# 集成Mybatis
- 引入依赖
- 在application.properties配置文件中添加配置
- 创建Mapper接口，编写CRUD方法使用sql语句。

# 编写发布内容页面（publish.html)
- 使用流式布局容器，设定合适的栅格参数
- 自定义css样式文件，并且引入、使用。

# Lombok插件工具
- 引入依赖
- 数据传输对象或数据库模型对象类中使用@Data注解

# spring boot热部署 -devtools

# 分页功能

select * from question limit step,size;

第1页：0,5      
第2页：5,5     
第3页：10,5    

step = (page-1) * 5

pagetotal = recordtotal / size （向上取整）
if(recordtotal % size != 0)
    pagetotal = (recordtotal / size) + 1;
else 
    pagetotal = recordtotal / size;
   
1、传递参数：当前页码（page）和每页显示记录数（size）
2、index控制器接收参数，传递给service层处理
3、service层根据当前页码page和size计算出sql语句条件limit step,size
4、Mapper通过step和size查询记录数返回对象集合
5、通过Mapper查询出总记录数
6、构建分页模型
7、new分页模型，赋值所有成员属性
8、将分页模型返回，并保存到域中
9、页面通过Thymeleaf标签获取值

# 完善导航条的封装
### Jquery的引入
### Thymeleaf的外部引用

# 实现问题详情页面，以及问题内容的修改

# 修复登录功能，判断用户是否存在数据库
# 实现退出登录功能
# 实现删除个人的发布内容
# 使用拦截器封装登录cookie遍历代码