package com.ljxy.score.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljxy.score.entity.Clazz;
import com.ljxy.score.mapper.ClazzMapper;
import com.ljxy.score.service.ClazzService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    @Resource
    ClazzMapper clazzMapper;

    @Override
    public Page<Clazz> findPage(Page<Clazz> page, String name) {
        return clazzMapper.findPage(page,name);
    }

    @Override
    public List<Clazz> findClazz(Integer gradeId) {
        return clazzMapper.findClazz(gradeId);
    }

    @Override
    public Integer findId(String name, Integer gradeId) {
        return clazzMapper.findId(name, gradeId);
    }
}

