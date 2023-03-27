package com.ljxy.score.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljxy.score.common.Result;
import com.ljxy.score.entity.Clazz;
import com.ljxy.score.entity.Grade;
import com.ljxy.score.entity.Student;
import com.ljxy.score.entity.User;
import com.ljxy.score.service.ClazzService;
import com.ljxy.score.service.GradeService;
import com.ljxy.score.service.StudentService;
import com.ljxy.score.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "班级模块")
@RestController
@RequestMapping("/clazz")
public class ClazzController {
    @Resource
    ClazzService clazzService;
    @Resource
    GradeService gradeService;
    @Resource
    StudentService studentService;
    @Resource
    UserService userService;

    @ApiOperation(value = "全部年级")
    @GetMapping("/all")
    public Result<?> getAllClazz() {
        List<Clazz> list = clazzService.list();
        return Result.success(list);
    }

    @ApiOperation(value = "分页查询（班级管理）")
    @GetMapping("/list")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer PageNum, @RequestParam(defaultValue = "10") Integer PageSize, @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Clazz> wrapper = Wrappers.<Clazz>lambdaQuery();
        if (StrUtil.isNotBlank(search)) wrapper.like(Clazz::getName, search);
        Page<Clazz> clazzPage = clazzService.findPage(new Page<>(PageNum, PageSize), search);
        return Result.success(clazzPage);
    }

    @ApiOperation(value = "添加班级")
    @PostMapping("/add")
    public Result<?> addClazz(@RequestBody Clazz clazz) {
        Grade one = gradeService.getOne(Wrappers.<Grade>lambdaQuery().eq(Grade::getId, clazz.getGradeId()));
        String GradeName = one.getName();
        Clazz result = clazzService.getOne(Wrappers.<Clazz>lambdaQuery().eq(Clazz::getId, clazz.getId()));
        Clazz res = clazzService.getOne(Wrappers.<Clazz>lambdaQuery().eq(Clazz::getName, clazz.getName()).eq(Clazz::getGradeId, clazz.getGradeId()));
        System.out.println("res = " + res);
        System.out.println("result = " + result);
        if (res != null) return Result.error("-1", GradeName+clazz.getName()+"已存在");
        if (result != null) return Result.error("-2", "编号已存在");
        clazzService.save(clazz);
        System.out.println(clazz);
        return Result.success(clazz);
    }

    @ApiOperation(value = "删除班级")
    @ApiImplicitParam(name = "id", value = "班级编号", required = true)
    @DeleteMapping("/delete/{id}")
    public Result<?> deleteClazz(@PathVariable Long id) {
        Integer gradeId = clazzService.getById(id).getGradeId();
        List<Student> list = studentService.list(Wrappers.<Student>lambdaQuery().eq(Student::getClazzId, id).eq(Student::getGradeId, gradeId));
        for (int i = 0; i < list.size(); i++) {
            userService.remove(Wrappers.<User>lambdaQuery().eq(User::getAccount,list.get(i).getStuId()));
        }
        if (clazzService.removeById(id)) return Result.success();
        return Result.error("-1", "删除失败");
    }

    @ApiOperation(value = "修改班级")
    @PutMapping("/update")
    public Result<?> update(@RequestBody Clazz clazz) {
        if (clazzService.updateById(clazz)) return Result.success();
        return Result.error("-1", "修改失败");
    }

    @ApiOperation(value = "根据年级ID查询班级")
    @GetMapping("/findClazzByGid")
    public Result<?> findByGid(Integer gradeId) {
        List<Clazz> res = clazzService.findClazz(gradeId);
        return Result.success(res);
    }

    @ApiOperation(value = "查id")
    @GetMapping("/findid")
    public void getById(String name, Integer gradeId) { /*        Integer res = gradeMapper.selectGrade(name); System.out.println(res);*/
        Integer id = clazzService.findId(name, gradeId);
        System.out.println(id);
    }
}
