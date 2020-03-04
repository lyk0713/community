package com.lyk.community.mapper;

import com.lyk.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
}
