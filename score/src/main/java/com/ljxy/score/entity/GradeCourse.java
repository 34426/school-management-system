package com.ljxy.score.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class GradeCourse {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer gradeId;

    private Integer courseId;

//    @TableField(exist = false)
//    private List<Grade> gradeList;
//
//    @TableField(exist = false)
//    private List<Course> courseList;

}
