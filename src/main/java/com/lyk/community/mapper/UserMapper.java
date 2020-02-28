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

    @Insert("insert into user(account_id, name, token, create_time, update_time) values(#{account_id}, #{user_name}, #{user_token}, #{user_create_time}, #{user_update_time})")
    void inser(User user);

    @Select("select * from user where token=#{token}")
    User selectByToken(@Param("token") String token);
}
