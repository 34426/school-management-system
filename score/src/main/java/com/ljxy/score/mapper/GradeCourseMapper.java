package com.ljxy.score.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljxy.score.entity.GradeCourse;

import java.util.List;

public interface GradeCourseMapper extends BaseMapper<GradeCourse> {
    //根据年级id和课程id查询主键
    List<GradeCourse> selectByGid(Integer gradeId,Integer courseId);
}
