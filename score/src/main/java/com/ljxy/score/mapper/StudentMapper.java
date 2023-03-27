package com.ljxy.score.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljxy.score.entity.Student;
import com.ljxy.score.entity.Vo.GradecountVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentMapper extends BaseMapper<Student> {
//    根据学生姓名分页查询
    Page<Student> findPage(Page<Student> page, @Param("name") String StuName);
//    多条件分页查询
    Page<Student> getPage(Page<Student> page,
                          @Param("stuName") String StuName,
                          @Param("gradeId") Integer gradeId,
                          @Param("clazzId") Integer clazzId,
                          @Param("stuId") String stuId);
//    @Select("select count(id) count, grade_id from student GROUP BY grade_id")
    @Select("select count(stu_id) count,grade.name from grade left join student on student.grade_id = grade.id GROUP BY grade.name order by grade.id")
    List<GradecountVo> countGrade();
}


