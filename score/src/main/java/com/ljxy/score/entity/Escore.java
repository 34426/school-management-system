package com.ljxy.score.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author SuSheng
 * @date 2022/3/3 14:48
 */
@Data
@TableName("escore")
public class Escore {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer examId;

    private Integer clazzId;

    @Excel(name = "学号")
    private Integer studentId;

    private Integer gradeId;

    private Integer courseId;
    @Excel(name = "成绩")
    private Integer score;

    @TableField(exist = false)
    private Student student;

    @TableField(exist = false)
    private Course course;

    @TableField(exist = false)
    private Clazz clazz;
}
