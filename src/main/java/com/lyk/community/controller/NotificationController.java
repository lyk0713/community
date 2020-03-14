package com.lyk.community.controller;

import com.lyk.community.dto.NotificationDTO;
import com.lyk.community.enums.NotificationTypeEnum;
import com.lyk.community.mapper.CommentMapper;
import com.lyk.community.mapper.NotificationMapper;
import com.lyk.community.model.Comment;
import com.lyk.community.service.CommentService;
import com.lyk.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/notification/{id}")
    public String notification (@PathVariable(name = "id")Integer id) {

        NotificationDTO notificationDTO = notificationService.read(id);

        if(notificationDTO.getType() == NotificationTypeEnum.REPLY_COMMENT.getType()) {
            Comment comment = commentService.selectById(notificationDTO.getOuterId());
            return "redirect:/question/" + comment.getParent_id();
        } else {
            return "redirect:/question/" + notificationDTO.getOuterId();
        }

    }
}
