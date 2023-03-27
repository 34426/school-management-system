package com.ljxy.score.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 老师-年级-班级-课程实体类
 */
@Data
@TableName("clazz_course_teacher")
public class ClazzCourseTeacher {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer clazzId;

    private Integer gradeId;

    private Integer courseId;

    private Integer teacherId;
}
