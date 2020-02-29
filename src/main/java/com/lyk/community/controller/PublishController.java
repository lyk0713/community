package com.lyk.community.controller;

import com.lyk.community.mapper.QuestionMapper;
import com.lyk.community.mapper.UserMapper;
import com.lyk.community.model.Question;
import com.lyk.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class PublishController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish() {

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title")String title, @RequestParam("description")String description, @RequestParam("tag")String tag, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        Question question = new Question();

        User user = null;

        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    user = userMapper.selectByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        if(user == null) {
            System.out.println("用户未登录！");
            return "publish";
        }
        System.out.println(user.getAccount_id());
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setCreate_time(System.currentTimeMillis());
        question.setUpdate_time(user.getCreate_time());

        questionMapper.insert(question);

        return "redirect:/";
    }
}
