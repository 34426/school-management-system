package com.ljxy.score.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljxy.score.entity.Headmaster;
import com.ljxy.score.mapper.HeadmasterMapper;
import com.ljxy.score.service.HeadmasterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author SuSheng
 * @date 2022/3/11 22:55
 */
@Service
public class HeadmasterServiceImpl extends ServiceImpl<HeadmasterMapper, Headmaster> implements HeadmasterService {

    @Resource
    HeadmasterMapper headmasterMapper;
    @Override
    public Page<Headmaster> findPge(Page<Headmaster> page, String headmasterName, Integer gradeId, Integer clazzId) {
        return headmasterMapper.findPge(page,headmasterName,gradeId,clazzId);
    }
}
