package com.lyk.community.controller;

import com.lyk.community.dto.PaginationDTO;
import com.lyk.community.mapper.UserMapper;
import com.lyk.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1")int page,
                        @RequestParam(name = "size", defaultValue = "5")int size) {

        PaginationDTO paginationDTO = questionService.QuestionDTOList(page, size);
        model.addAttribute("paginationDTO",paginationDTO);

        return "index";
    }
}
