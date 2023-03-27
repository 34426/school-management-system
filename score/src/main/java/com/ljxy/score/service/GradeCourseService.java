package com.ljxy.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljxy.score.entity.GradeCourse;

import java.util.List;

public interface GradeCourseService extends IService<GradeCourse> {

    List<GradeCourse> selectByGid(Integer gradeId,Integer courseId);
}
