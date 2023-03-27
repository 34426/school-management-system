package com.ljxy.score.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljxy.score.entity.Escore;
import com.ljxy.score.entity.Vo.ClazzAvgVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author SuSheng
 * @date 2022/3/3 14:53
 */
public interface EscoreMapper extends BaseMapper<Escore> {
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
     * 分页查询成绩（老师）
     * @param page
     * @param examId
     * @param studentId
     * @param gradeId
     * @param clazzId
     * @param courseId
     * @return
     */
    Page<Escore> findPage(Page<Escore> page,@Param("examId") Integer examId, @Param("studentId") Integer studentId,@Param("gradeId") Integer gradeId,@Param("clazzId") Integer clazzId,@Param("courseId") Integer courseId);

    /**
     * 根据id更新成绩
     */
    boolean updateScore(Integer id ,Integer score);

    float selectAvg(Integer examId,Integer gradeId,Integer clazzId,Integer courseId);

//    查询每门考试各班的平均分
    List<ClazzAvgVo> clazzAvg(Integer examId,Integer gradeId,Integer courseId);
//  查询各班考试的最高分
    float heightScore(Integer examId,Integer gradeId,Integer courseId,Integer clazzId);
}
