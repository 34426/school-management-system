package com.ljxy.score.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljxy.score.entity.Teacher;
import com.ljxy.score.mapper.TeacherMapper;
import com.ljxy.score.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Resource
    TeacherMapper teacherMapper;


    @Override
    public Page<Teacher> findList(Page<Teacher> page, String tName, Integer gradeId, Integer clazzId, Integer courseId) {
        return teacherMapper.findList(page,tName,gradeId,clazzId,courseId);
    }

    @Override
    public List<Teacher> findCourse(Integer tId) {
        return teacherMapper.findCourse(tId);
    }
}
