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
