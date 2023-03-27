package com.ljxy.score.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljxy.score.common.Result;
import com.ljxy.score.entity.ClazzCourseTeacher;
import com.ljxy.score.entity.Exam;
import com.ljxy.score.entity.Teacher;
import com.ljxy.score.entity.User;
import com.ljxy.score.service.ClazzCourseTeacherService;
import com.ljxy.score.service.ExamService;
import com.ljxy.score.service.TeacherService;
import com.ljxy.score.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "老师模块")
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    TeacherService teacherService;
    @Resource
    UserService userService;
    @Resource
    ClazzCourseTeacherService clazzCourseTeacherService;
    @Resource
    ExamService examService;

    /**
     * 分页查询老师信息
     * @param PageNum
     * @param PageSize
     * @param search 姓名（模糊查询）
     * @param gradeId 年级id
     * @param clazzId 班级id
     * @param courseId 课程id
     * @return
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    public Result<?> teacherPage(@RequestParam(defaultValue = "1") Integer PageNum, @RequestParam(defaultValue = "20") Integer PageSize, String search,Integer gradeId,Integer clazzId,Integer courseId) {
        Page<Teacher> teacherPage = teacherService.findList(new Page<>(PageNum, PageSize), search,gradeId,clazzId,courseId);
        return Result.success(teacherPage);
    }

    /**
     * 删除老师信息
     * 删除老师的课程
     * @param id
     * @return
     */
    @ApiOperation(value = "删除老师信息")
    @DeleteMapping("/delete/{id}")
    public Result<?> deleteTeacher(@PathVariable Long id) {
        List<ClazzCourseTeacher> list = clazzCourseTeacherService.list(Wrappers.<ClazzCourseTeacher>lambdaQuery().eq(ClazzCourseTeacher::getTeacherId, id));
        for (int i = 0; i < list.size(); i++) {
//            获取中间表的id
            Integer teacherClazzId = list.get(i).getId();
//            删除老师对应的课程信息
            clazzCourseTeacherService.removeById(teacherClazzId);
        }
        String account = String.valueOf(teacherService.getById(id).getTId());
        System.out.println("用户账号");
        System.out.println(account);
        if (teacherService.removeById(id)) {
//            删除用户表对应的老师账号
            userService.remove(Wrappers.<User>lambdaQuery().eq(User::getAccount, account));
            return Result.success();
        }
        return Result.error("-1", "删除失败");
    }

    /**
     * 修改老师信息
     * @param teacher
     * @return
     */
    @ApiOperation(value = "修改老师信息")
    @PutMapping("/update")
    public Result<?> updateStudent(@RequestBody Teacher teacher) {
        if (teacherService.updateById(teacher)) return Result.success(teacher);
        return Result.error("-1", "修改失败");
    }

    /**
     * 添加老师信息
     * 添加用户信息
     * @param teacher
     * @return
     */
    @ApiOperation(value = "添加老师信息")
    @PostMapping("/add")
    public Result<?> addUser(@RequestBody Teacher teacher) {
        Teacher res = teacherService.getOne(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getTId, teacher.getTId()));
        if (res != null) return Result.error("-1", "账号已存在");
        teacherService.save(teacher);
        User user = new User();
        user.setAccount(String.valueOf(teacher.getTId()));
        user.setName(teacher.getTName());
        user.setType(2);
        user.setPassword("e10adc3949ba59abbe56e057f20f883e");
        userService.save(user);
        return Result.success(teacher);
    }

    /**
     * 批量删除老师信息
     * 批量删除老师用户信息
     * 删除对应课程信息
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        System.out.println("11111111数组");
        System.out.println(ids);
        for (int i = 0; i < ids.size(); i++) {
            Integer id = ids.get(i);
            System.out.println("22222222222遍历");
            System.out.println(id);
            String account = String.valueOf(teacherService.getById(id).getTId());
            userService.remove(Wrappers.<User>lambdaQuery().eq(User::getAccount, account));
            List<ClazzCourseTeacher> list = clazzCourseTeacherService.list(Wrappers.<ClazzCourseTeacher>lambdaQuery().eq(ClazzCourseTeacher::getTeacherId, id));
            for (int j = 0; j < list.size(); j++) {
                Integer clazzTeacherId = list.get(j).getId();
                clazzCourseTeacherService.removeById(clazzTeacherId);
            }
        }

        if (teacherService.removeByIds(ids)) return Result.success();
        return Result.error("-1", "删除失败");
    }



    @ApiOperation(value = "查询老师课程")
    @GetMapping("/findBytId")
    public Result<?> findCourseInfo(Integer tId){
        List<Teacher> course = teacherService.findCourse(tId);
        return Result.success(course);
    }

    /**
     * 查询教师下的考试列表
     * @param teacherId
     * @return
     */
    @ApiOperation(value = "查询教师的考试列表")
    @GetMapping("/examlist")
    public Result<?> eaxmList(@RequestParam(defaultValue = "1") Integer PageNum, @RequestParam(defaultValue = "10") Integer PageSize, String teacherId){
//       查询老师信息
        List<Teacher> teacherList = teacherService.list(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getTId, teacherId));
//        老师Id
        Integer id = teacherList.get(0).getId();
//        查询老师年级信息
        List<ClazzCourseTeacher> clazzCourseTeacherList = clazzCourseTeacherService.list(Wrappers.<ClazzCourseTeacher>lambdaQuery().eq(ClazzCourseTeacher::getTeacherId, id));
//        年级ID
        Integer gradeId = clazzCourseTeacherList.get(0).getGradeId();
//        根据年级ID查询考试列表
//        List<Exam> examList = examService.examList(gradeId);
        Page<Exam> examPage = examService.examList(new Page<>(PageNum, PageSize), gradeId);
        return Result.success(examPage);
    }
}
