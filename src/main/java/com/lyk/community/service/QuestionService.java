package com.lyk.community.service;

import com.lyk.community.dto.PaginationDTO;
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

    /*
    * 创建或更新发布内容
    * */
    public void CreateOrUpdate(Question question) {
        if(question.getId() == null) {
            question.setCreate_time(System.currentTimeMillis());

            questionMapper.insert(question);
        } else {
            question.setUpdate_time(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }

    /*
     * 首页的问题显示列表
     * 分页的实现
     */
    public PaginationDTO QuestionDTOList(int page, int size) {
        List<QuestionDTO> questionDTOs = new ArrayList<QuestionDTO>();

        int step = (page - 1) * size; //开始查询的记录数

        List<Question> questions = questionMapper.selectAll(step, size);

        // 将查询出来的记录转换成DTO
        for(Question question : questions) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }

        /******************** 实现分页 ********************/
        PaginationDTO paginationDTO = new PaginationDTO();

        paginationDTO.setQuestionDTOs(questionDTOs);

        int totalCount = questionMapper.selectCount();//获取总记录数
        int totalPage = 0;
        if(totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        //设置总页数
        paginationDTO.setTotalPage(totalPage);

        //设置显示的页码
        List<Integer> pages = new ArrayList<>();//显示页码数
        if(totalPage <= 7) {
            for(int i = 1; i <= totalPage; i++) {
                if(page - i > 0)
                    pages.add(0, page-i);
                else if (page - i < 0)
                    pages.add(page - (page-i));
                else
                    pages.add(page);
            }
        } else {

            if(page <= 4) {
                for(int i = 1; i <= 7; i++){
                    if(page-i > 0)
                        pages.add(0, page-i);
                    else if(page-i < 0)
                        pages.add(page - (page-i));
                    else
                        pages.add(page);
                }
            } else {
                //2,3,4,5,6,7,8
                //3,4,5,6,7,8,9
                if(page + 3 > totalPage){
                    for(int i = totalPage-7+1; i <= totalPage; i++){
                        pages.add(i);
                    }
                } else {
                    pages.add(page);
                    for(int i = 1; i <= 3; i++)
                        pages.add(0,page - i);
                    for(int i = 1; i <= 3; i++)
                        pages.add(page+i);
                }

            }
        }
        paginationDTO.setPages(pages);

        //设置是否展示上一页
        if(page == 1) {
            paginationDTO.setShowPrevious(false);
        } else {
            paginationDTO.setShowPrevious(true);
        }

        //设置是否展示下一页
        if (page == totalPage) {
            paginationDTO.setShowNext(false);
        } else {
            paginationDTO.setShowNext(true);
        }

        //设置是否展示首页
        if(pages.contains(1)) {
            paginationDTO.setShowFirstPage(false);
        } else {
            paginationDTO.setShowFirstPage(true);
        }

        //设置是否展示尾页
        if(pages.contains(totalPage)){
            paginationDTO.setShowLastPage(false);
        } else {
            paginationDTO.setShowLastPage(true);
        }

        paginationDTO.setPage(page);

        return paginationDTO;
    }

    /*
     * 我的问题显示列表
     * 分页的实现
     */
    public PaginationDTO QuestionDTOList(int userid, int page, int size) {
        List<QuestionDTO> questionDTOs = new ArrayList<QuestionDTO>();

        int step = (page - 1) * size; //开始查询的记录数

        List<Question> questions = questionMapper.selectByUser(userid, step, size);

        // 将查询出来的记录转换成DTO
        for(Question question : questions) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }

        /******************** 实现分页 ********************/
        PaginationDTO paginationDTO = new PaginationDTO();

        paginationDTO.setQuestionDTOs(questionDTOs);

        int totalCount = questionMapper.selectCountByUser(userid);//获取总记录数
        int totalPage = 0;
        if(totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        //设置总页数
        paginationDTO.setTotalPage(totalPage);

        //设置显示的页码
        List<Integer> pages = new ArrayList<>();//显示页码数
        if(totalPage <= 7) {
            for(int i = 1; i <= totalPage; i++) {
                if(page - i > 0)
                    pages.add(0, page-i);
                else if (page - i < 0)
                    pages.add(page - (page-i));
                else
                    pages.add(page);
            }
        } else {

            if(page <= 4) {
                for(int i = 1; i <= 7; i++){
                    if(page-i > 0)
                        pages.add(0, page-i);
                    else if(page-i < 0)
                        pages.add(page - (page-i));
                    else
                        pages.add(page);
                }
            } else {
                //2,3,4,5,6,7,8
                //3,4,5,6,7,8,9
                if(page + 3 > totalPage){
                    for(int i = totalPage-7+1; i <= totalPage; i++){
                        pages.add(i);
                    }
                } else {
                    pages.add(page);
                    for(int i = 1; i <= 3; i++)
                        pages.add(0,page - i);
                    for(int i = 1; i <= 3; i++)
                        pages.add(page+i);
                }

            }
        }
        paginationDTO.setPages(pages);

        //设置是否展示上一页
        if(page == 1) {
            paginationDTO.setShowPrevious(false);
        } else {
            paginationDTO.setShowPrevious(true);
        }

        //设置是否展示下一页
        if (page == totalPage) {
            paginationDTO.setShowNext(false);
        } else {
            paginationDTO.setShowNext(true);
        }

        //设置是否展示首页
        if(pages.contains(1)) {
            paginationDTO.setShowFirstPage(false);
        } else {
            paginationDTO.setShowFirstPage(true);
        }

        //设置是否展示尾页
        if(pages.contains(totalPage)){
            paginationDTO.setShowLastPage(false);
        } else {
            paginationDTO.setShowLastPage(true);
        }

        paginationDTO.setPage(page);

        return paginationDTO;
    }

    /*
    * 根据用户的id显示问题详情页面
    * */
    public QuestionDTO getById(int id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
}
