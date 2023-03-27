package com.ljxy.score.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljxy.score.entity.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherService extends IService<Teacher> {
    /**
     * 根据老师姓名分页查询老师课程信息
     * @param page
     * @param tName
     * @return
     */
    Page<Teacher> findList(Page<Teacher> page, @Param("tName") String tName, @Param("gradeId") Integer gradeId, @Param("clazzId")Integer clazzId, @Param("courseId")Integer courseId);

    /**
     * 根据t_id 查询老师课程信息
     * @param tId
     * @return
     */
    List<Teacher> findCourse(@Param("tId") Integer tId);
}
