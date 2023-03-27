package com.ljxy.score.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljxy.score.common.Result;
import com.ljxy.score.entity.Course;
import com.ljxy.score.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "课程模块")
@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    CourseService courseService;


    @ApiOperation(value = "分页查询（课程管理）")
    @ApiImplicitParams({@ApiImplicitParam(name = "PageNum", value = "页码", required = true), @ApiImplicitParam(name = "PageSize", value = "页大小", required = true), @ApiImplicitParam(name = "search", value = "课程名称"),})
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer PageNum, @RequestParam(defaultValue = "10") Integer PageSize, @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Course> wrapper = Wrappers.<Course>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
        }
        Page<Course> studentPage = courseService.page(new Page<>(PageNum, PageSize), wrapper);
        System.out.println("studentPage = " + studentPage);
        return Result.success(studentPage);
    }

    @ApiOperation(value = "添加课程")
    @PostMapping("/addCourse")
    public Result<?> addCourse(@RequestBody Course course) {
        Course name = courseService.getOne(Wrappers.<Course>lambdaQuery().eq(Course::getName, course.getName()));
        Course id = courseService.getOne(Wrappers.<Course>lambdaQuery().eq(Course::getId, course.getId()));
        if (name != null) return Result.error("-1", "课程已存在");
        if (id != null) return Result.error("-2", "课程编号重复");
        courseService.save(course);
        return Result.success();
    }

    @ApiOperation(value = "删除课程")
    @ApiImplicitParam(name = "id", value = "课程编号", required = true)
    @DeleteMapping("/delete/{id}")
    public Result<?> deleteCourseById(@PathVariable Long id) {
        if (courseService.removeById(id)) {
            return Result.success();
        }
        return Result.error("-1", "删除失败");
    }

    @ApiOperation(value = "修改课程")
    @PutMapping("/updateCourse")
    public Result<?> updateCourse(@RequestBody Course course) {
        if (courseService.updateById(course)) return Result.success();
        return Result.error("-1", "更新失败");
    }

    @ApiOperation(value = "全部课程")
    @GetMapping("/all")
    public List<Course> getAllCourse() {
        List<Course> list = courseService.list();
        return list;
    }
}
