package com.lyk.community.service;

import com.lyk.community.controller.CommentDTO;
import com.lyk.community.dto.QuestionDTO;
import com.lyk.community.enums.CommentTypeEnum;
import com.lyk.community.exception.CustomizeErrorCode;
import com.lyk.community.exception.CustomizeException;
import com.lyk.community.mapper.CommentMapper;
import com.lyk.community.mapper.QuestionMapper;
import com.lyk.community.mapper.UserMapper;
import com.lyk.community.model.Comment;
import com.lyk.community.model.Question;
import com.lyk.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {
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
        } else {
            // 回复问题
            Question question = questionMapper.getById(comment.getParent_id());
            if(question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);

//            questionMapper.updateCommentCount();//评论数+1

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
}
