package com.ljxy.score.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljxy.score.entity.Headmaster;
import com.ljxy.score.mapper.HeadmasterMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author SuSheng
 * @date 2022/3/11 22:54
 */
public interface HeadmasterService extends IService<Headmaster> {
    Page<Headmaster> findPge(Page<Headmaster> page,
                             @Param("headmasterName") String headmasterName,
                             @Param("gradeId")Integer gradeId,
                             @Param("clazzId") Integer clazzId);
}
