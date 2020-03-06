package com.lyk.community.mapper;

import com.lyk.community.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title, description, creator, tag, create_time, update_time) values(#{title}, #{description}, #{creator}, #{tag}, #{create_time}, #{update_time})")
    void insert(Question question);

    @Select("select * from question limit #{step}, #{size}")
    List<Question> selectAll(@Param("step")int step, @Param("size")int size);

    @Select("select count(1) from question")
    int selectCount();

    @Select("select * from question where creator=#{userid} limit #{step}, #{size}")
    List<Question> selectByUser(@Param("userid") int userid, @Param("step") int step, @Param("size") int size);

    @Select("select count(1) from question where creator=#{userid}")
    int selectCountByUser(@Param("userid") int userid);

    @Select("select * from question where id=#{id}")
    Question getById(@Param("id")int id);

    @Update("update question set title=#{title}, description=#{description}, tag=#{tag}, update_time=#{update_time} where id=#{id}")
    void update(Question question);
}
