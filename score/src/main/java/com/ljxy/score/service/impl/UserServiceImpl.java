package com.ljxy.score.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljxy.score.entity.User;
import com.ljxy.score.mapper.UserMapper;
import com.ljxy.score.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public Page<User> findPage(Page<User> page, String account, Integer type) {
        return userMapper.findPage(page,account,type);
    }

    @Override
    public boolean updateUser(String account, String name) {
        return userMapper.updateUser(account,name);
    }

    @Override
    public boolean updatePwd(String password, Integer id) {
        return userMapper.updatePwd(password,id);
    }


}
