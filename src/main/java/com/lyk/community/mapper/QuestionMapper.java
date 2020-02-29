package com.lyk.community.mapper;

import com.lyk.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title, description, creator, tag, create_time, update_time) values(#{title}, #{description}, #{creator}, #{tag}, #{create_time}, #{update_time})")
    void insert(Question question);

    @Select("select * from question")
    List<Question> selectAll();
}
