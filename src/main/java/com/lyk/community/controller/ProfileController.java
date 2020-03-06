package com.lyk.community.controller;

import com.lyk.community.dto.PaginationDTO;
import com.lyk.community.mapper.UserMapper;
import com.lyk.community.model.Question;
import com.lyk.community.model.User;
import com.lyk.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          Model model, HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1")int page,
                          @RequestParam(name = "size", defaultValue = "5")int size) {
        User user = null;

        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();

                    user = userMapper.selectByToken(token);

                    if (user != null) {
                        //将user对象放入session域中
                        request.getSession().setAttribute("user", user);
                    }

                    break;
                }
            }
        }

        if(user == null) {
            return "redirect:/";
        }

        if("question".equals(action)) {
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", "我的发布");
        }

        if("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        PaginationDTO paginationDTO = questionService.QuestionDTOList(user.getId(), page, size);

        model.addAttribute("paginationDTO", paginationDTO);

        return "profile";
    }
}
