package com.ljxy.score.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljxy.score.entity.Clazz;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClazzMapper extends BaseMapper<Clazz> {
//    分页查询
    Page<Clazz> findPage(Page<Clazz> page, @Param("name") String name);
//    根据年级id查询班级名称
    List<Clazz> findClazz(Integer gradeId);
//    根据班级名称、年级id查询课程编号
    public Integer findId(String name,Integer gradeId);
}
