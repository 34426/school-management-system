package com.ljxy.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljxy.score.entity.Grade;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GradeService extends IService<Grade> {
     List<Grade> findAll(@Param("id") Integer id);
}
