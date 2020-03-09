package com.lyk.community.service;

import com.lyk.community.mapper.UserMapper;
import com.lyk.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User douser = userMapper.selectByAccountId(user.getAccount_id());

        if(douser == null) {
            //插入用户
            user.setCreate_time(System.currentTimeMillis());
            userMapper.insert(user);
        } else {
            // 更新用户
            user.setUpdate_time(System.currentTimeMillis());
            user.setName(user.getName());
            user.setToken(user.getToken());
            user.setAvatar_url(user.getAvatar_url());
            userMapper.update(user);
        }
    }
}
