package com.lyk.community.controller;

import com.lyk.community.dto.NotificationDTO;
import com.lyk.community.dto.PaginationDTO;
import com.lyk.community.mapper.UserMapper;
import com.lyk.community.model.Question;
import com.lyk.community.model.User;
import com.lyk.community.service.NotificationService;
import com.lyk.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          Model model, HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1")int page,
                          @RequestParam(name = "size", defaultValue = "5")int size) {
        User user = (User) request.getSession().getAttribute("user");

        if(user == null) {
            return "redirect:/";
        }

        if("question".equals(action)) {
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", "我的发布");
            PaginationDTO paginationDTO = questionService.QuestionDTOList(user.getId(), page, size);
            model.addAttribute("paginationDTO", paginationDTO);

            PaginationDTO DTO = notificationService.list(user.getId(), page, size);
            List<NotificationDTO> DTOs = DTO.getDTOs();
            int unreadcount = 0;
            for (NotificationDTO notificationDTO : DTOs) {
                if(notificationDTO.getStatus() == 0) {
                    unreadcount++;
                }
            }
            model.addAttribute("unreadCount", unreadcount);
        }

        if("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("paginationDTO", paginationDTO);

            List<NotificationDTO> DTOs = paginationDTO.getDTOs();
            int unreadcount = 0;
            for (NotificationDTO notificationDTO : DTOs) {
                if(notificationDTO.getStatus() == 0) {
                    unreadcount++;
                }
            }
            model.addAttribute("unreadCount", unreadcount);
        }


        return "profile";
    }
}
