package com.ljxy.score.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljxy.score.entity.Clazz;
import org.apache.ibatis.annotations.Param;


import java.util.List;


public interface ClazzService extends IService<Clazz> {
    Page<Clazz> findPage(Page<Clazz> page, @Param("name") String name);
    List<Clazz> findClazz(Integer gradeId);
    Integer findId(String name,Integer gradeId);
}
