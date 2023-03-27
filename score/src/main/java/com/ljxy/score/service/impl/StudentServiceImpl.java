package com.ljxy.score.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljxy.score.entity.Student;
import com.ljxy.score.entity.Vo.GradecountVo;
import com.ljxy.score.mapper.StudentMapper;
import com.ljxy.score.service.StudentService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements StudentService {

    @Resource
    StudentMapper studentMapper;

    @Override
    public Page<Student> findPage(Page<Student> page, String StuName) {
        return studentMapper.findPage(page,StuName);
    }

    @Override
    public Page<Student> getPage(Page<Student> page, String StuName, Integer gradeId, Integer clazzId,String stuId) {
        return studentMapper.getPage(page,StuName,gradeId,clazzId,stuId);
    }

    @Override
    public List<GradecountVo> countGrade() {
        return studentMapper.countGrade();
    }
}
