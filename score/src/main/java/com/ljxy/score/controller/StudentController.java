package com.ljxy.score.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljxy.score.common.Result;
import com.ljxy.score.entity.*;
import com.ljxy.score.entity.Vo.GradecountVo;
import com.ljxy.score.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "学生模块")
@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    StudentService studentService;
    @Resource
    GradeService gradeService;
    @Resource
    ClazzService clazzService;
    @Resource
    UserService userService;
    @Resource
    ExamService examService;

    /**
     * 分页查询
     * @param PageNum
     * @param PageSize
     * @param search @return
     */
    @ApiOperation(value = "学生分页查询")
    @GetMapping("/list")
    public Result<?> studentList(@RequestParam(defaultValue = "1") Integer PageNum, @RequestParam(defaultValue = "10") Integer PageSize, @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Student> wrapper = Wrappers.<Student>lambdaQuery();
        if (StrUtil.isNotBlank(search)) wrapper.like(Student::getStuName, search);
        Page<Student> studentPage = studentService.findPage(new Page<>(PageNum, PageSize), search);
        return Result.success(studentPage);
    }

    /**
     * 添加学生信息
     * @param student
     * @return
     */
    @ApiOperation(value = "添加学生")
    @PostMapping("/add")
    public Result<?> addStudent(@RequestBody Student student) {
        Student res = studentService.getOne(Wrappers.<Student>lambdaQuery().eq(Student::getStuId, student.getStuId()));
        if (res != null) return Result.error("-1", student.getStuId()+"已存在");
        studentService.save(student);
        User user = new User();
        user.setAccount(student.getStuId());
        user.setName(student.getStuName());
        user.setType(3);
        user.setPassword("e10adc3949ba59abbe56e057f20f883e");
        userService.save(user);
        return Result.success(student);
    }

    /**
     * 修改学生信息
     * @param student
     * @return
     */
    @ApiOperation(value = "修改学生信息")
    @PutMapping("/update")
    public Result<?> updateStudent(@RequestBody Student student) {


        if (studentService.updateById(student)) {
            userService.updateUser(student.getStuId(),student.getStuName());
            return Result.success(student);
        }
        return Result.error("-1", "修改失败");
    }

    /**
     * 删除学生信息
     * @param id
     * @return
     */
    @ApiOperation(value = "删除学生信息")

    @DeleteMapping("/delete/{id}")
    public Result<?> deleteStudent(@PathVariable Long id) {
        String accout = studentService.getById(id).getStuId();
        System.out.println(accout);
        if (studentService.removeById(id)) {
            userService.remove(Wrappers.<User>lambdaQuery().eq(User::getAccount, accout));
            return Result.success();
        }
        return Result.error("-1", "删除失败");
    }

    /**
     * 根据学生姓名、年级、班级查询
     *
     * @param PageNum
     * @param PageSize
     * @param stuName
     * @param gradeId
     * @param clazzId
     * @return
     */
    @ApiOperation(value = "多条件查询")
    @GetMapping("/studentQuery")
    public Result<?> studentQuery(@RequestParam(defaultValue = "1") Integer PageNum, @RequestParam(defaultValue = "10") Integer PageSize, String stuName, Integer gradeId, Integer clazzId,String stuId) {
        Page<Student> studentPage = studentService.getPage(new Page<>(PageNum, PageSize), stuName, gradeId, clazzId,stuId);
        return Result.success(studentPage);
    }

    /**
     * 批量删除学生信息
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/deleteBatch")
    public Result<?> deleteByStudentByIds(@RequestBody List<Integer> ids) {
//        遍历
        for (int i = 0; i < ids.size(); i++) {
            Integer res = ids.get(i);
//            获取学号
            String account = studentService.getById(res).getStuId();
//            删除用户信息
            userService.remove(Wrappers.<User>lambdaQuery().eq(User::getAccount, account));
            System.out.println(res);
        }
//        删除学生信息
        if (studentService.removeByIds(ids)) {
            return Result.success();
        }
        return Result.error("-1", "删除失败");
    }

    /**
     * 批量导入学生信息
     * @param file
     * @return
     */
    @ApiOperation(value = "导入学生信息")
    @PostMapping("/upload")
    public Result<?> importStudent(MultipartFile file) {
        ImportParams params = new ImportParams();
        List<Grade> gradeLists = gradeService.list();
        List<Clazz> clazzLists = clazzService.list();
        params.setTitleRows(0);

        try {
            List<Student> list = ExcelImportUtil.importExcel(file.getInputStream(), Student.class, params);

            for (int i = 0; i < list.size(); i++) {
                Student one = studentService.getOne(Wrappers.<Student>lambdaQuery().eq(Student::getStuId, list.get(i).getStuId()));
                if (one !=null){
                    return Result.error("-2",list.get(i).getStuId()+"已存在");
                }
                System.out.println(list);
                /*                获取年级id*/
                Integer id = gradeLists.get(gradeLists.indexOf(new Grade(list.get(i).getGrade().getName()))).getId();
                list.get(i).setGradeId(id);
                System.out.println(id);
                /*                get班级名称*/
                String name = clazzLists.get(clazzLists.indexOf(new Clazz(list.get(i).getClazz().getName()))).getName();
                System.out.println("班级名称" + name);
                /*                查询年级班级对应的id*/
                Integer clazzId = clazzService.findId(name, id);
                if (clazzId ==null){
                    return Result.error("-2",list.get(i).getGrade().getName()+name+"不存在");
                }
                System.out.println("班级id" + clazzId);
                list.get(i).setClazzId(clazzId);
                System.out.println(list.get(i).getStuName());
//              批量插入用户信息
                User user = new User();
                user.setAccount(list.get(i).getStuId());
                user.setName(list.get(i).getStuName());
                user.setType(3);
                user.setPassword("e10adc3949ba59abbe56e057f20f883e");
                userService.save(user);
            }
            if (studentService.saveBatch(list)) return Result.error("-1", "导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-2", "导入失败");
        }
        return Result.error("-2", "导入失败");
    }

    /**
     * 查询各年级人数数量
     * @return
     */
    @ApiOperation(value = "各年级学生人数")
    @GetMapping("/count")
    public Result<?> count(){
        List<GradecountVo> list = studentService.countGrade();
        System.out.println(list);
        return Result.success(list);
    }

    /**
     * 查询学生考试列表
     * @param studentId
     * @return
     */
    @ApiOperation(value = "查询学生的考试列表")
    @GetMapping("/test")
    public Result<?> getExamList(String studentId){
        List<Student> list = studentService.list(Wrappers.<Student>lambdaQuery().eq(Student::getStuId, studentId));
        Integer gradeId = list.get(0).getGradeId();
        Page<Exam> examPage = examService.examPage(new Page<>(1, 100), null, gradeId);
        return Result.success(examPage);
    }

    /**
     * 查询个人信息
     * @param studentId
     * @return
     */
    @ApiOperation(value = "查询学生信息(个人)")
    @GetMapping("/person")
    public Result<?> person(String studentId){
        Page<Student> page = studentService.getPage(new Page<>(1, 100), null, null, null, studentId);
        return Result.success(page);
    }
}
