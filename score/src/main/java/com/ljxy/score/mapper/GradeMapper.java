package com.ljxy.score.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljxy.score.entity.Grade;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GradeMapper extends BaseMapper<Grade> {
    /**
     * 查询所有的年级信息，并且加上所选的课程
     */
     List<Grade> findAll(@Param("id") Integer id);
}
