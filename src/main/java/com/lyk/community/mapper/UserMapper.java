package com.lyk.community.mapper;

import com.lyk.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    @Insert("insert into user(account_id, name, token, create_time, update_time, avatar_url) values(#{account_id}, #{name}, #{token}, #{create_time}, #{update_time}, #{avatar_url})")
    void inser(User user);

    @Select("select * from user where token=#{token}")
    User selectByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User selectById(@Param("id")int id);
}
