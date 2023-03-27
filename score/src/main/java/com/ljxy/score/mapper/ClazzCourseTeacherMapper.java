package com.ljxy.score.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljxy.score.entity.ClazzCourseTeacher;

import java.util.List;

public interface ClazzCourseTeacherMapper extends BaseMapper<ClazzCourseTeacher> {
    List<ClazzCourseTeacher> selectResult(Integer gradeId,Integer courseId,Integer clazzId,Integer teacherId);
}
