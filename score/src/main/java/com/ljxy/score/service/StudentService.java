package com.ljxy.score.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljxy.score.entity.Student;
import com.ljxy.score.entity.Vo.GradecountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentService extends IService<Student> {
    Page<Student> findPage(Page<Student> page, @Param("name") String StuName);
    Page<Student> getPage(Page<Student> page,
                          @Param("stuName") String StuName,
                          @Param("gradeId") Integer gradeId,
                          @Param("clazzId") Integer clazzId,
                          @Param("stuId") String stuId);
    List<GradecountVo> countGrade();
}
