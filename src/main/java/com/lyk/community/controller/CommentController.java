package com.lyk.community.controller;

import com.lyk.community.dto.CommentCreateDTO;
import com.lyk.community.dto.ResultDTO;
import com.lyk.community.exception.CustomizeErrorCode;
import com.lyk.community.model.Comment;
import com.lyk.community.model.User;
import com.lyk.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if(commentCreateDTO == null || commentCreateDTO.getContent() == null || "".equals(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.COMENT_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParent_id(commentCreateDTO.getParent_id());
        comment.setContent(commentCreateDTO.getContent());
        comment.setCommentator(user.getId());
        comment.setType(commentCreateDTO.getType());
        comment.setCreate_time(System.currentTimeMillis());

        commentService.insert(comment,user);

        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO comments(@PathVariable(name="id")Integer id) {
        List<CommentDTO> commentDTOs = commentService.selectByCommentId(id);
        return ResultDTO.okOf(commentDTOs);
    }
}
