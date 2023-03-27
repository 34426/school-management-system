package com.ljxy.score.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户表
 */
@TableName("user")
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String account;
    private String password;
    private String name;
    private Integer type;
}
