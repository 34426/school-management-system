package com.ljxy.score.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ljxy.score.common.Result;
import com.ljxy.score.entity.ClazzCourseTeacher;
import com.ljxy.score.service.ClazzCourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author SuSheng
 */
@Api(tags = "老师-班级-课程模块")
@RestController
@RequestMapping("/clazz/course/teacher")
public class ClazzCourseTeacherController {
    @Resource
    ClazzCourseTeacherService clazzCourseTeacherService;

    @ApiOperation(value = "删除班级年级课程")
    @ApiImplicitParam(name = "id", value = "编号", required = true)
    @DeleteMapping("/delete/{id}")
    public Result<?> deleteClazzCourseTeacher(@PathVariable Long id){
        if (clazzCourseTeacherService.removeById(id)){
            return Result.success();
        }
        return Result.error("-1","删除失败");
    }

    @ApiOperation(value = "添加年级班级课程")
    @PostMapping("/add")
    public Result<?> add (@RequestBody ClazzCourseTeacher clazzCourseTeacher){
        ClazzCourseTeacher one = clazzCourseTeacherService.getOne(Wrappers.<ClazzCourseTeacher>lambdaQuery()
                .eq(ClazzCourseTeacher::getGradeId, clazzCourseTeacher.getGradeId())
                .eq(ClazzCourseTeacher::getClazzId, clazzCourseTeacher.getClazzId())
                .eq(ClazzCourseTeacher::getCourseId, clazzCourseTeacher.getCourseId()));
        if (one !=null){
            return Result.error("-2","课程已存在");
        }
        if (clazzCourseTeacherService.save(clazzCourseTeacher)) {
            return Result.success();
        }
        return Result.error("-1","添加失败");
    }

    @ApiOperation(value="查ID")
    @GetMapping("/list")
    public Result<?> getOne(Integer gradeId,Integer courseId,Integer clazzId,Integer teacherId){
        List<ClazzCourseTeacher> list = clazzCourseTeacherService.selectResult(gradeId, courseId, clazzId, teacherId);
        return Result.success(list);
    }

}
