package com.lyk.community.service;

import com.lyk.community.dto.QuestionDTO;
import com.lyk.community.mapper.QuestionMapper;
import com.lyk.community.mapper.UserMapper;
import com.lyk.community.model.Question;
import com.lyk.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    //通过数据库查询所有question记录
    //根据creator查询user，封装成新的questionDTO传输对象
    public List<QuestionDTO> QuestionDTOList() {
        List<QuestionDTO> questionDTOs = new ArrayList<QuestionDTO>();

        List<Question> questions = questionMapper.selectAll();

        for(Question question : questions) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            System.out.println(user);
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }
        return questionDTOs;
    }
}
