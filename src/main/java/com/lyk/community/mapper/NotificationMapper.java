package com.lyk.community.mapper;

import com.lyk.community.model.Notification;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface NotificationMapper {

    @Insert("insert into notification(notifier, receiver, outerId, type, status, create_time, notifier_name, outer_title) values(#{notifier}, #{receiver}, #{outerId}, #{type}, #{status}, #{create_time}, #{notifier_name}, #{outer_title})")
    void insert(Notification notification);

    @Select("select count(1) from notification")
    int selectCount();

    @Select("select * from notification limit #{step},#{size}")
    List<Notification> selectAll(@Param("step") int step, @Param("size") Integer size);

    @Select("select * from notification where id=#{id}")
    Notification selectById(@Param("id") Integer id);

    @Update("update notification set status=1 where id=#{id}")
    void updateNotification(@Param("id") Integer id);
}
