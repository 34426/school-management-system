package com.ljxy.score.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljxy.score.common.Result;
import com.ljxy.score.entity.*;
import com.ljxy.score.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author SuSheng
 * @date 2022/3/11 23:11
 */
@Api(tags ="班主任模块")
@RestController
@RequestMapping("/headmaster")
public class HeadmasterController {
    @Resource
    HeadmasterService headmasterService;

    @Resource
    UserService userService;

    @Resource
    StudentService studentService;

    @Resource
    ExamService examService;

    @Resource
    EscoreService escoreService;

    @Resource
    ClazzService clazzService;


    @ApiOperation("查询班主任信息")
    @GetMapping("/page")
    public Result<?> page (@RequestParam(defaultValue = "1") Integer PageNum, @RequestParam(defaultValue = "10") Integer PageSize, String headmasterName, Integer gradeId, Integer clazzId){
        Page<Headmaster> headmasterPage = headmasterService.findPge(new Page<>(PageNum, PageSize), headmasterName, gradeId, clazzId);

        return Result.success(headmasterPage);
    }

    @ApiOperation("删除班主任信息")
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id){
        if (headmasterService.removeById(id)) {
            return Result.success();

        }
        return Result.error("-1","删除失败");
    }

    @ApiOperation("修改班主任信息")
    @PutMapping("/update")
    public Result<?> update(@RequestBody Headmaster headmaster){
        if (headmasterService.updateById(headmaster)) {
            return Result.success();
        }
        return Result.error("-1","更新失败");
    }

    @ApiOperation("添加班主任信息")
    @PostMapping("add")
    public Result<?> add(@RequestBody Headmaster headmaster){
        Headmaster res = headmasterService.getOne(Wrappers.<Headmaster>lambdaQuery().eq(Headmaster::getGradeId, headmaster.getGradeId()).eq(Headmaster::getClazzId, headmaster.getClazzId()));
        if (res !=null){
            return Result.error("-1","该班级已有班主任！请重新选择");
        }
        System.out.println("res = " + res);
        if (headmasterService.save(headmaster)) {
            User user = new User();
            user.setAccount(String.valueOf(headmaster.getHeadmasterId()));
            user.setName(headmaster.getHeadmasterName());
            user.setPassword("e10adc3949ba59abbe56e057f20f883e");
            user.setType(4);
            userService.save(user);
            return Result.success();
        }
        return Result.error("-1","添加失败");
    }

    @ApiOperation("根据班主任信息查询学生信息")
    @GetMapping("/list")
    public Result<?> list( @RequestParam(defaultValue = "1") Integer PageNum, @RequestParam(defaultValue = "10") Integer PageSize,String account,String stuName){
        List<Headmaster> list = headmasterService.list(Wrappers.<Headmaster>lambdaQuery().eq(Headmaster::getHeadmasterId, account));
        System.out.println(list);
        Integer gradeId = list.get(0).getGradeId();
        Integer clazzId = list.get(0).getClazzId();
        Page<Student> page = studentService.getPage(new Page<>(PageNum, PageSize), stuName, gradeId, clazzId, null);
        return Result.success(page);
    }

    @ApiOperation("查询考试列表")
    @GetMapping("/examlist")
    public Result<?> examList(@RequestParam(defaultValue = "1") Integer PageNum, @RequestParam(defaultValue = "10") Integer PageSize,String account,String name){
        List<Headmaster> list = headmasterService.list(Wrappers.<Headmaster>lambdaQuery().eq(Headmaster::getHeadmasterId, account));
        Integer gradeId = list.get(0).getGradeId();
        Page<Exam> examPage = examService.examPage(new Page<>(PageNum, PageSize), name, gradeId);
        return Result.success(examPage);
    }

    @ApiOperation("查询考试成绩")
    @GetMapping("/scorelist")
    public Result<?> scoreList(@RequestParam(defaultValue = "1") Integer PageNum, @RequestParam(defaultValue = "10") Integer PageSize,String account,Integer examId,Integer courseId,Integer studentId){
        List<Headmaster> list = headmasterService.list(Wrappers.<Headmaster>lambdaQuery().eq(Headmaster::getHeadmasterId, account));
        Integer gradeId = list.get(0).getGradeId();
        Integer clazzId = list.get(0).getClazzId();
        Page<Escore> page = escoreService.findPage(new Page<>(PageNum, PageSize), examId, studentId, gradeId, clazzId, courseId);
        return Result.success(page);
    }

    @ApiOperation("查询班主任班级")
    @GetMapping("getclazz")
    public Result<?> getClazz(String account){
        List<Headmaster> list = headmasterService.list(Wrappers.<Headmaster>lambdaQuery().eq(Headmaster::getHeadmasterId, account));
        Integer clazzId = list.get(0).getClazzId();
        String name = clazzService.getById(clazzId).getName();
        return Result.success(name);
    }

    @ApiOperation("查询班主任的年级和班级")
    @GetMapping("/findGradeAndClazz")
    public Result<?> findGradeAndClazz(String account){
        List<Headmaster> list = headmasterService.list(Wrappers.<Headmaster>lambdaQuery().eq(Headmaster::getHeadmasterId, account));
        return Result.success(list);
    }

}
