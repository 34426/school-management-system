package com.ljxy.score.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 学生信息实体类
 */
@TableName("student")
@Data
public class Student {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @Excel(name = "学号")
    private String stuId;

    @Excel(name = "姓名")
    private String stuName;

    @Excel(name = "性别")
    private String sex;

    @Excel(name = "年龄")
    private Integer age;

    @Excel(name = "联系方式")
    private String phone;

//    @Excel(name = "班级")
    private Integer clazzId;

    private Integer gradeId;

    @TableField(exist = false)
    private List<Grade> gradeList;

    @TableField(exist = false)
    private List<Clazz> clazzList;

    @TableField(exist = false)
    @ExcelEntity(name = "年级")
    private Grade grade;

    @TableField(exist = false)
    @ExcelEntity(name = "班级")
    private Clazz clazz;

    @TableField(exist = false)
    private List<Course> courseList;

}
