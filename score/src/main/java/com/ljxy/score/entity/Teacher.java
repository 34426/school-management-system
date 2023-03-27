package com.ljxy.score.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 老师信息实体类
 */
@TableName("teacher")
@Data
public class Teacher {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer tId;

    private String tName;

    private String sex;

    private String phone;

    @TableField(exist = false)
    private List<Clazz> clazzList;




}
