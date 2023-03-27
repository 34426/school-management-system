package com.ljxy.score.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljxy.score.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper  extends BaseMapper<User> {
//    多条件分页查询
    Page<User> findPage(Page<User> page,
                       @Param("account") String account,
                       @Param("type") Integer type);

    /**
     * 根据姓名修改账号
     * @param account
     * @param name
     * @return
     */
    boolean updateUser(String account, String name);

    /**
     * 重置密码
     * @param password
     * @param id
     * @return
     */
    boolean updatePwd(String password, Integer id);
}
