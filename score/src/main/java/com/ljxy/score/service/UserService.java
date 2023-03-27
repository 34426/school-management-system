package com.ljxy.score.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljxy.score.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserService extends IService<User> {
    Page<User> findPage(Page<User> page,
                        @Param("account") String account,
                        @Param("type") Integer type);

    boolean updateUser(String account,String name);

    boolean updatePwd(String password, Integer id);
}
