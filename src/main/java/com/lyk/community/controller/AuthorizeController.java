package com.lyk.community.controller;

import com.lyk.community.dto.AccessTokenDTO;
import com.lyk.community.dto.GithubUser;
import com.lyk.community.mapper.UserMapper;
import com.lyk.community.model.User;
import com.lyk.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;


    @Value("${github.client.id}")
    private String client_id;

    @Value("${github.client.secret}")
    private String client_secret;

    @Value("${github.redirect.uri}")
    private String redirect_uri;

    @Autowired
    private UserMapper userMapper;

    /**
     * GitHub第三方登录实现
     * 一、客户端请求如下地址
     *  https://github.com/login/oauth/authorize?client_id=2635142931332aac30d8&redirect_uri=http://localhost:8080/callback&scope=user&state=1
     * 二、GitHub Apps服务端会回调redirect_uri地址，请求callback控制器
     * 三、控制器接收code、state参数
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code, @RequestParam(name="state")String state, HttpServletResponse response) {

        // 将请求需要传输的参数封装成对象（Data Transfer Object）
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);

        /*
            四、通过使用工具（OkHttp）发起post请求
                i.请求地址为：https://github.com/login/oauth/access_token
                ii.通过fastjson将accessTokenDTO传输对象转换成json格式
                iii.将Github返回的字符截取出access_token的值
         */
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);

        /*
            五、同样使用工具（OkHttp）将获取的access_token作为参数发起get请求
                i.请求地址为：https://api.github.com/user?access_token=(accessToken)
                ii.将github返回json格式的字符串通过fastjson转成构建好的GithubUser对象
         */
        GithubUser githubUser = githubProvider.getUser(accessToken);


        // 六、构建数据库模型User对象，实例化User对象，通过Mapper的操作进行数据持久化

        // 七、将User对象中的token（UUID）作为唯一标识保存至cookie中，并重定向首页。

        // 八、首页控制器通过遍历cookie，匹配到token后，获取token的value拿去数据库查询user，查询成功后返回user保存到session域中

        if(githubUser != null && githubUser.getId() != null) {
            String user_token = UUID.randomUUID().toString();

            User user = new User();
            user.setAccount_id(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setToken(user_token);
            user.setCreate_time(System.currentTimeMillis());
            user.setAvatar_url(githubUser.getAvatar_url());


            if(user != null) {
                userMapper.inser(user);
                //将UUID作为标识，保存至cookie中
                response.addCookie(new Cookie("token", user_token));
                return "redirect:/";
            } else {
                System.out.println("登录失败！");

            }
        }
        return "hello";
    }
}
