package com.lyk.community.controller;

import com.lyk.community.mapper.UserMapper;
import com.lyk.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        for(Cookie cookie : cookies) {
            if("token".equals(cookie.getName())) {
                String token = cookie.getValue();

                User user = userMapper.selectByToken(token);

                if(user != null){
                    //将user对象放入session域中
                    request.getSession().setAttribute("user", user);
                }

                break;
            }
        }

        return "index";
    }
}
