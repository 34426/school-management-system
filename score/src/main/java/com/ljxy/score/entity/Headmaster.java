package com.ljxy.score.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author SuSheng
 * @date 2022/3/11 22:48
 * 班主任实体类
 */

@Data
@TableName("headmaster")
public class Headmaster {
    @TableId(type = IdType.AUTO)
    private  Integer id;
    private Integer headmasterId;
    private  String headmasterName;
    private String sex;
    private Integer age;
    private String phone;
    private Integer clazzId;
    private Integer gradeId;

    @TableField(exist = false)
    private Clazz clazz;

    @TableField(exist = false)
    private Grade grade;
}
