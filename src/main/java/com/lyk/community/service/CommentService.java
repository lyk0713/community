package com.lyk.community.service;

import com.lyk.community.controller.CommentDTO;
import com.lyk.community.enums.CommentTypeEnum;
import com.lyk.community.enums.NotificationStatusEnum;
import com.lyk.community.enums.NotificationTypeEnum;
import com.lyk.community.exception.CustomizeErrorCode;
import com.lyk.community.exception.CustomizeException;
import com.lyk.community.mapper.CommentMapper;
import com.lyk.community.mapper.NotificationMapper;
import com.lyk.community.mapper.QuestionMapper;
import com.lyk.community.mapper.UserMapper;
import com.lyk.community.model.Comment;
import com.lyk.community.model.Notification;
import com.lyk.community.model.Question;
import com.lyk.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User commentator) {
        if(comment.getParent_id() == null || comment.getParent_id() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if(comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            // 回复评论
            Comment dbComment = commentMapper.selectById(comment.getParent_id());
            if(dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);

            // 添加通知
            Notification notification = new Notification();
            notification.setNotifier(comment.getCommentator());
            notification.setReceiver(dbComment.getCommentator());
            notification.setType(NotificationTypeEnum.REPLY_COMMENT.getType());
            notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
            notification.setOuterId(comment.getParent_id());
            notification.setCreate_time(System.currentTimeMillis());
            notification.setNotifier_name(commentator.getName());
            notification.setOuter_title(comment.getContent());
            notificationMapper.insert(notification);
        } else {
            // 回复问题
            Question question = questionMapper.getById(comment.getParent_id());
            if(question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);


            // 添加通知
            Notification notification = new Notification();
            notification.setNotifier(comment.getCommentator());
            notification.setReceiver(question.getCreator());
            notification.setType(NotificationTypeEnum.REPLY_QUESTION.getType());
            notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
            notification.setOuterId(comment.getParent_id());
            notification.setCreate_time(System.currentTimeMillis());
            notification.setNotifier_name(commentator.getName());
            notification.setOuter_title(question.getTitle());
            notificationMapper.insert(notification);
        }

    }

    public List<CommentDTO> selectByQuestionId(int id) {
        List<Comment> comments = commentMapper.selectByQuestionId(id,CommentTypeEnum.Question.getType());

        List<CommentDTO> commentDTOs = new ArrayList<>();

        for(Comment comment : comments){
            User user = userMapper.selectById(comment.getCommentator());
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(user);
            commentDTOs.add(commentDTO);
        }

        return commentDTOs;
    }

    public List<CommentDTO> selectByCommentId(Integer id) {

        List<Comment> comments = commentMapper.selectByQuestionId(id,CommentTypeEnum.COMMENT.getType());

        List<CommentDTO> commentDTOs = new ArrayList<>();

        for(Comment comment : comments){
            User user = userMapper.selectById(comment.getCommentator());
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(user);
            commentDTOs.add(commentDTO);
        }

        return commentDTOs;
    }

    public Comment selectById(Integer outerId) {
        Comment comment = commentMapper.selectById(outerId);
        return comment;
    }
}
