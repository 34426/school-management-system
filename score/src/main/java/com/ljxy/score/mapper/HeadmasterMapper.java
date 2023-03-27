package com.ljxy.score.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljxy.score.entity.Headmaster;
import org.apache.ibatis.annotations.Param;

/**
 * @author SuSheng
 * @date 2022/3/11 22:53
 */

public interface HeadmasterMapper extends BaseMapper<Headmaster> {
    Page<Headmaster> findPge(Page<Headmaster> page,
                             @Param("headmasterName") String headmasterName,
                             @Param("gradeId")Integer gradeId,
                             @Param("clazzId") Integer clazzId);
}
