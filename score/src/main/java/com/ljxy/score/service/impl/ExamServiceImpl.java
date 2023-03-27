package com.ljxy.score.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljxy.score.entity.Exam;
import com.ljxy.score.entity.Vo.GradecountVo;
import com.ljxy.score.mapper.ExamMapper;
import com.ljxy.score.service.ExamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * Author SuSheng
 */
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {

    @Resource
    ExamMapper examMapper;


    @Override
    public Page<Exam> examPage(Page<Exam> page, String name, Integer gradeId) {
        return examMapper.examPage(page,name,gradeId);
    }

    @Override
    public Page<Exam> examList(Page<Exam> page, Integer gradeId) {
        return examMapper.examList(page,gradeId);
    }


    @Override
    public boolean updateStatus(Integer id, Integer status) {
        return examMapper.updateStatus(id,status);
    }

    @Override
    public List<GradecountVo> examCount() {
        return examMapper.examCount();
    }
}
