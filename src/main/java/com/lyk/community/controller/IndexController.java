package com.lyk.community.controller;

import com.lyk.community.dto.PaginationDTO;
import com.lyk.community.dto.QuestionDTO;
import com.lyk.community.mapper.UserMapper;
import com.lyk.community.model.User;
import com.lyk.community.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name="page", defaultValue = "1")int page,
                        @RequestParam(name = "size", defaultValue = "5")int size) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();

                    User user = userMapper.selectByToken(token);

                    if (user != null) {
                        //将user对象放入session域中
                        request.getSession().setAttribute("user", user);
                    }

                    break;
                }
            }
        }

        PaginationDTO paginationDTO = questionService.QuestionDTOList(page, size);

        model.addAttribute("paginationDTO",paginationDTO);

        return "index";
    }
}
