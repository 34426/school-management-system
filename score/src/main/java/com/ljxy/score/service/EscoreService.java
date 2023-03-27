package com.ljxy.score.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljxy.score.entity.Escore;
import com.ljxy.score.entity.Vo.ClazzAvgVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author SuSheng
 * @date 2022/3/3 14:54
 */
public interface EscoreService extends IService<Escore> {
    /**
     * 查询成绩（学生）
     * @param examId
     * @param studentId
     * @param gradeId
     * @param clazzId
     * @param courseId
     * @return
     */
    List<Escore> selectScore(@Param("examId") Integer examId, @Param("studentId") Integer studentId,@Param("gradeId") Integer gradeId,@Param("clazzId") Integer clazzId,@Param("courseId") Integer courseId);

    /**
     * 查询成绩（老师）
     * @param page
     * @param examId
     * @param studentId
     * @param gradeId
     * @param clazzId
     * @param courseId
     * @return
     */
    Page<Escore> findPage(Page<Escore> page, @Param("examId") Integer examId, @Param("studentId") Integer studentId, @Param("gradeId") Integer gradeId, @Param("clazzId") Integer clazzId, @Param("courseId") Integer courseId);


    /**
     * 根据id更新成绩
     */
    boolean updateScore(Integer id ,Integer score);

    float selectAvg(Integer examId,Integer gradeId,Integer clazzId,Integer courseId);

    List<ClazzAvgVo> clazzAvg(Integer examId, Integer gradeId, Integer courseId);

    float heightScore(Integer examId,Integer gradeId,Integer courseId,Integer clazzId);
}
