package com.ljxy.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljxy.score.entity.ClazzCourseTeacher;

import java.util.List;

public interface ClazzCourseTeacherService extends IService<ClazzCourseTeacher> {
    List<ClazzCourseTeacher> selectResult(Integer gradeId, Integer courseId, Integer clazzId, Integer teacherId);
}
