package com.ljxy.score.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ljxy.score.common.Result;
import com.ljxy.score.entity.GradeCourse;
import com.ljxy.score.service.GradeCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "年级-课程模块")
@RestController
@RequestMapping("/gradecourse")
public class GradeCourseController {

    @Resource
    GradeCourseService gradeCourseService;

    @ApiOperation(value = "全部年级-课程")
    @GetMapping("/all")
    public Result<?> getAll() {
        List<GradeCourse> res = gradeCourseService.list();
        return Result.success(res);
    }

    @ApiOperation(value = "增加年级-课程")
    @PostMapping("/add")
    public Result<?> increase(@RequestBody GradeCourse gradeCourse) {
        GradeCourse res = gradeCourseService.getOne(Wrappers.<GradeCourse>lambdaQuery().eq(GradeCourse::getGradeId, gradeCourse.getGradeId()).eq(GradeCourse::getCourseId, gradeCourse.getCourseId()));
        if (res != null){
            return Result.error("-1","课程已存在");
        }
        gradeCourseService.save(gradeCourse);
        return Result.success();
    }

    @ApiOperation(value = "删除年级课程")
    @ApiImplicitParam(name = "id", value = "年级-班级ID", required = true)
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        gradeCourseService.removeById(id);
        return Result.success();
    }

    @ApiOperation(value = "查id")
    @GetMapping("/selectId")
    public Result<?> selectId(Integer gradeId,Integer courseId) {

        List<GradeCourse> res = gradeCourseService.selectByGid(gradeId, courseId);
        System.out.println(res);
        return Result.success(res);
    }

}
