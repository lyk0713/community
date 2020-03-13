package com.lyk.community.mapper;

import com.lyk.community.controller.CommentDTO;
import com.lyk.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CommentMapper {
    @Insert("insert into comment(parent_id, type, commentator, create_time, content) values(#{parent_id}, #{type}, #{commentator}, #{create_time}, #{content})")
    void insert(Comment comment);

    @Select("select * from comment where id=#{id}")
    Comment selectById(@Param("id") Integer id);

    @Select("select * from comment where parent_id=#{id} and type=#{type}")
    List<Comment> selectByQuestionId(@Param("id") int id, @Param("type") int type);

}
