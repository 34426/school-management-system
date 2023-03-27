package com.ljxy.score.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ljxy.score.common.Result;
import com.ljxy.score.entity.*;
import com.ljxy.score.entity.Vo.GradeVo;
import com.ljxy.score.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "年级模块")
@RestController
@RequestMapping("/grade")
public class GradeController {
    @Resource
    GradeService gradeService;
    @Resource
    GradeCourseService gradeCourseService;
    @Resource
    ClazzService clazzService;
    @Resource
    StudentService studentService;
    @Resource
    UserService userService;


    @ApiOperation(value = "全部年级")
    @GetMapping("/all")
    public Result<?> getGrade() {
        List<Grade> list = gradeService.list();
        return Result.success(list);
    }

    @ApiOperation(value = "删除年级")
    @ApiImplicitParam(name = "id", value = "年级编号", required = true)
    @DeleteMapping("/delete/{id}")
    public Result<?> deleteGrade(@PathVariable Long id) {

//        查找年级下课程
        List<GradeCourse> list = gradeCourseService.list(Wrappers.<GradeCourse>lambdaQuery().eq(GradeCourse::getGradeId, id));
//        查找年级下的班级
        List<Clazz> clazzList = clazzService.list(Wrappers.<Clazz>lambdaQuery().eq(Clazz::getGradeId, id));
//        查找年级下的学生
        List<Student> studentList = studentService.list(Wrappers.<Student>lambdaQuery().eq(Student::getGradeId, id));
        for (int k = 0; k < studentList.size(); k++) {
            userService.remove(Wrappers.<User>lambdaQuery().eq(User::getAccount,studentList.get(k).getStuId()));
        }
        System.out.println(clazzList);
        System.out.println(list);
//        删除班级
        for (int j = 0; j < list.size(); j++) clazzService.removeById(list.get(j).getId());
//        删除课程
        for (int i = 0; i < list.size(); i++) {
            System.out.println("年级-课程ID" + list.get(i).getId());
            gradeCourseService.removeById(list.get(i).getId());
        }
//        删除年级
        if (gradeService.removeById(id)) return Result.success();
        return Result.error("-1", "删除失败");
    }

    @ApiOperation(value = "修改年级")
    @PutMapping("/update")
    public Result<?> updateGrade(@RequestBody GradeVo gradeVo) {
//        前端课程列表
        List<Integer> courseList = gradeVo.getCourseList();
//      更新年级信息
        Grade grade = new Grade();
        grade.setId(gradeVo.getId());
        grade.setName(gradeVo.getName());
        gradeService.updateById(grade);

        List list = new ArrayList();
//        查找年级下的课程信息
        List<GradeCourse> list1 = gradeCourseService.list(Wrappers.<GradeCourse>lambdaQuery().eq(GradeCourse::getGradeId, gradeVo.getId()));
        for (int i = 0; i < list1.size(); i++) {
//            把数据库的课程id加到列表中
            list.add(list1.get(i).getCourseId());
        }
//        数据库的课程列表
        System.out.println(list);
        //      前端课程的列表和数据库课程列表的差值（添加的课程）
        List subtract = (List) CollectionUtils.subtract(courseList, list);
        System.out.println("添加的课程"+subtract);
        for (int j = 0; j < subtract.size(); j++) {
            GradeCourse gradeCourse = new GradeCourse();
            gradeCourse.setGradeId(gradeVo.getId());
            gradeCourse.setCourseId((Integer) subtract.get(j));
            gradeCourseService.save(gradeCourse);
        }
//      数据库的列表和前端的差值（删除的课程）
        List subtract1 = (List) CollectionUtils.subtract(list, courseList);
        System.out.println("删除的课程"+subtract1);
        for (int k = 0; k < subtract1.size(); k++) {
            gradeCourseService.remove(Wrappers.<GradeCourse>lambdaQuery().eq(GradeCourse::getCourseId,subtract1.get(k)).eq(GradeCourse::getGradeId,gradeVo.getId()));
        }
        return Result.success();
    }

    @ApiOperation(value = "年级-课程")
    @GetMapping("/list")
    public Result<?> findGradeAndClazz(@RequestParam(defaultValue = "") Integer id) {

        List<Grade> list = gradeService.findAll(id);
        return Result.success(list);
    }

    @ApiOperation("添加年级课程")
    @PostMapping("/add")
    public Result<?> add(@RequestBody GradeVo gradeVo){

        System.out.println(gradeVo);
        Grade id = gradeService.getOne(Wrappers.<Grade>lambdaQuery().eq(Grade::getId, gradeVo.getId()));
        Grade one = gradeService.getOne(Wrappers.<Grade>lambdaQuery().eq(Grade::getName, gradeVo.getName()));
        if (one !=null){
            return Result.error("-1",gradeVo.getName()+"已存在");
        }
        if (id !=null){
            return Result.error("-1",gradeVo.getId()+"编号已存在");
        }
//        new grade对象
        Grade grade = new Grade();
        grade.setId(gradeVo.getId());
        grade.setName(gradeVo.getName());
//        插入年级
        gradeService.save(grade);
//        遍历courseList
        List<Integer> list = gradeVo.getCourseList();
        for (int i = 0; i < gradeVo.getCourseList().size(); i++) {
            //new gradecourse 对象
            GradeCourse course = new GradeCourse();
            course.setGradeId(gradeVo.getId());
            course.setCourseId(list.get(i));
            //插入数据
            gradeCourseService.save(course);
        }
        return Result.success();
    }
}
