package com.ljxy.score.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljxy.score.entity.Exam;
import com.ljxy.score.entity.Vo.GradecountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamService extends IService<Exam> {
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
