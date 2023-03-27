package com.ljxy.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljxy.score.entity.ClazzCourseTeacher;
import com.ljxy.score.mapper.ClazzCourseTeacherMapper;
import com.ljxy.score.service.ClazzCourseTeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClazzCourseTeacherServiceImpl extends ServiceImpl<ClazzCourseTeacherMapper, ClazzCourseTeacher> implements ClazzCourseTeacherService {

    @Resource
    ClazzCourseTeacherMapper clazzCourseTeacherMapper;
    @Override
    public List<ClazzCourseTeacher> selectResult(Integer gradeId, Integer courseId, Integer clazzId, Integer teacherId) {
        return clazzCourseTeacherMapper.selectResult(gradeId,courseId,clazzId,teacherId);
    }
}
