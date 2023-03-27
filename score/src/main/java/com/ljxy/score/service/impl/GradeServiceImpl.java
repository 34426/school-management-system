package com.ljxy.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljxy.score.entity.Grade;
import com.ljxy.score.mapper.GradeMapper;
import com.ljxy.score.service.GradeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Resource
    GradeMapper gradeMapper;
    @Override
    public List<Grade> findAll(@Param("id") Integer id) {
        return gradeMapper.findAll(id);
    }
}
