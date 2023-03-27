package com.ljxy.score.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljxy.score.entity.Exam;
import com.ljxy.score.entity.Vo.GradecountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamMapper extends BaseMapper<Exam> {
    Page<Exam> examPage(Page<Exam> page,
                        @Param("name") String name,
                        @Param("gradeId")Integer gradeId);

    Page<Exam> examList(Page<Exam> page,@Param("gradeId")Integer gradeId);

    boolean updateStatus(Integer id, Integer status);

    /**
     * 查询各年级考试次数
     */
    List<GradecountVo> examCount();
}

