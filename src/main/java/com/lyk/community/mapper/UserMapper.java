package com.lyk.community.mapper;

import com.lyk.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    @Insert("insert into user(account_id, name, token, create_time, avatar_url) values(#{account_id}, #{name}, #{token}, #{create_time}, #{avatar_url})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User selectByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User selectById(@Param("id")int id);

    @Select("select * from user where account_id=#{account_id}")
    User selectByAccountId(@Param("account_id")String account_id);

    @Update("update user set name=#{name}, token=#{token}, update_time=#{update_time}, avatar_url=#{avatar_url} where account_id=#{account_id}")
    void update(User user);
}
