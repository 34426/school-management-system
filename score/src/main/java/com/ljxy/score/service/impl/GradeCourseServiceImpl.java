package com.ljxy.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljxy.score.entity.Course;
import com.ljxy.score.entity.GradeCourse;
import com.ljxy.score.mapper.GradeCourseMapper;
import com.ljxy.score.service.GradeCourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GradeCourseServiceImpl extends ServiceImpl<GradeCourseMapper, GradeCourse> implements GradeCourseService {

    @Resource
    GradeCourseMapper gradeCourseMapper;

    @Override
    public List<GradeCourse> selectByGid(Integer gradeId, Integer courseId) {
        return gradeCourseMapper.selectByGid(gradeId,courseId);
    }
}
