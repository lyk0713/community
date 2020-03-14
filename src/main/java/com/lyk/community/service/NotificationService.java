package com.lyk.community.service;

import com.lyk.community.dto.NotificationDTO;
import com.lyk.community.dto.PaginationDTO;
import com.lyk.community.enums.NotificationTypeEnum;
import com.lyk.community.mapper.CommentMapper;
import com.lyk.community.mapper.NotificationMapper;
import com.lyk.community.mapper.QuestionMapper;
import com.lyk.community.model.Comment;
import com.lyk.community.model.Notification;
import com.lyk.community.model.Question;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        List<NotificationDTO> notificationDTOs = new ArrayList<>();

        int step = (page - 1) * size; //开始查询的记录数
        List<Notification> notifications = notificationMapper.selectAll(step, size);

        for(Notification notification : notifications) {
            if(notification.getType() == 1) {
                //如果是回复了文章，判断文章是否属于用户
                Question question = questionMapper.getById(notification.getOuterId());
                if(question.getCreator() == userId){
                    NotificationDTO notificationDTO = new NotificationDTO();
                    BeanUtils.copyProperties(notification, notificationDTO);
                    notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
                    notificationDTOs.add(notificationDTO);
                }
            } else { //如果是回复了评论，判断评论是否属于用户
                Comment comment = commentMapper.selectById(notification.getOuterId());
                if(comment.getCommentator() == userId){
                    NotificationDTO notificationDTO = new NotificationDTO();
                    BeanUtils.copyProperties(notification, notificationDTO);
                    notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
                    notificationDTOs.add(notificationDTO);
                }
            }
        }


        paginationDTO.setDTOs(notificationDTOs);

        int totalCount = notificationMapper.selectCount();//获取总记录数
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

    public NotificationDTO read(Integer id) {
//        Notification notification = notificationMapper.selectById(id);
//        notification.setStatus(1);//标记已读
        notificationMapper.updateNotification(id);
        Notification notification = notificationMapper.selectById(id);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        return notificationDTO;
    }
}
