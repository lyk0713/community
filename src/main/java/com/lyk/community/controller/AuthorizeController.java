package com.lyk.community.controller;

import com.lyk.community.dto.AccessTokenDTO;
import com.lyk.community.dto.GithubUser;
import com.lyk.community.mapper.UserMapper;
import com.lyk.community.model.User;
import com.lyk.community.provider.GithubProvider;
import com.lyk.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
    private UserService userService;

    /**
     * GitHub第三方登录实现
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code, @RequestParam(name="state")String state, HttpServletResponse response) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);

        GithubUser githubUser = githubProvider.getUser(accessToken);

        if(githubUser != null && githubUser.getId() != null) {
            String user_token = UUID.randomUUID().toString();
            User user = new User();
            user.setAccount_id(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setToken(user_token);
            user.setAvatar_url(githubUser.getAvatar_url());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token", user_token));
            return "redirect:/";
        } else {
            System.out.println("登录失败！");
        }
        return "hello";
    }

    /* 退出登录 */
    @GetMapping("/loginout")
    public String loginout(HttpServletRequest request,HttpServletResponse response){

        request.getSession().removeAttribute("user");

        //删除cookie
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
