package com.ljxy.score.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljxy.score.entity.Escore;
import com.ljxy.score.entity.Vo.ClazzAvgVo;
import com.ljxy.score.mapper.EscoreMapper;
import com.ljxy.score.service.EscoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author SuSheng
 * @date 2022/3/3 14:54
 */
@Service
public class EscoreServiceImpl  extends ServiceImpl<EscoreMapper, Escore> implements EscoreService {

    @Resource
    EscoreMapper escoreMapper;

    @Override
    public List<Escore> selectScore(Integer examId, Integer studentId, Integer gradeId, Integer clazzId, Integer courseId) {
        return escoreMapper.selectScore(examId,studentId,gradeId,clazzId,courseId);
    }

    @Override
    public Page<Escore> findPage(Page<Escore> page, Integer examId, Integer studentId, Integer gradeId, Integer clazzId, Integer courseId) {
        return escoreMapper.findPage(page,examId,studentId,gradeId,clazzId,courseId);
    }

    @Override
    public boolean updateScore(Integer id, Integer score) {
        return escoreMapper.updateScore(id,score);
    }

    @Override
    public float selectAvg(Integer examId, Integer gradeId, Integer clazzId, Integer courseId) {
        return escoreMapper.selectAvg(examId,gradeId,clazzId,courseId);
    }

    @Override
    public List<ClazzAvgVo> clazzAvg(Integer examId, Integer gradeId, Integer courseId) {
        return escoreMapper.clazzAvg(examId,gradeId,courseId);
    }

    @Override
    public float heightScore(Integer examId, Integer gradeId, Integer courseId,Integer clazzId) {
        return escoreMapper.heightScore(examId,gradeId,courseId,clazzId);
    }


}
