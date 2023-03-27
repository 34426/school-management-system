package com.ljxy.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljxy.score.entity.Course;
import com.ljxy.score.mapper.CourseMapper;
import com.ljxy.score.service.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
}
