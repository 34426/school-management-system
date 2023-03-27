package com.ljxy.score.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljxy.score.common.Result;
import com.ljxy.score.entity.*;
import com.ljxy.score.entity.Vo.GradecountVo;
import com.ljxy.score.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author SuSheng
 * @date 2022/3/2 0:30
 */
@Api(tags = "考试模块")
@RestController
@RequestMapping("/exam")
public class ExamController {
    @Resource
    ExamService examService;
    @Resource
    GradeCourseService gradeCourseService;
    @Resource
    StudentService studentService;
    @Resource
    EscoreService escoreService;

    /**
     * 删除考试
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除考试")
    @DeleteMapping("/delete/{id}")
    public Result<?> deleteExam(@PathVariable Long id) {
        if (examService.removeById(id)) return Result.success();
        return Result.error("-1", "删除失败");
    }


    /**
     * 分页查询考试成绩
     *
     * @param PageNum
     * @param PageSize
     * @param name
     * @param gradeId
     * @return
     */
    @ApiOperation(value = "考试信息（分页）")
    @GetMapping("/all")
    public Result<?> ExamList(@RequestParam(defaultValue = "1") Integer PageNum, @RequestParam(defaultValue = "10") Integer PageSize, String name, Integer gradeId) {
        System.out.println(name);
        System.out.println(gradeId);
        Page<Exam> list = examService.examPage(new Page<>(PageNum, PageSize), name, gradeId);
        return Result.success(list);
    }

    /**
     * 修改考试信息
     *
     * @param exam
     * @return
     */
    @ApiOperation(value = "修改考试信息")
    @PutMapping("/update")
    public Result<?> updateExam(@RequestBody Exam exam) {
        if (examService.updateById(exam)) {
            return Result.success(exam);
        }
        return Result.error("-1", "更新失败");
    }

    /**
     * 修改年级状态
     *
     * @param id
     * @param status
     * @return
     */
    @ApiOperation("修改考试状态")
    @PutMapping("/updateStatus")
    public Result<?> updateStatus(Integer id, Integer status) {
        System.out.println("考试ID" + id);
        System.out.println("考试状态" + status);
        examService.updateStatus(id, status);
        return Result.success();
    }


    /**
     * 添加考试
     * 初始化学生成绩信息
     */
    @ApiOperation(value = "添加考试")
    @PostMapping("/add")
    public Result<?> addExamAndInfo(@RequestBody Exam exam) {
//        设置考试状态为不可见
        Exam one = examService.getOne(Wrappers.<Exam>lambdaQuery().eq(Exam::getId, exam.getId()));
        if(one !=null){
            return Result.error("-2","编号重复");
        }
        exam.setStatus(0);
        if (examService.save(exam)) {
            //        年级id
            Integer gradeId = exam.getGradeId();
            //        根据年级查询课程
            List<GradeCourse> gradeCourseList = gradeCourseService.list(Wrappers.<GradeCourse>lambdaQuery().eq(GradeCourse::getGradeId, gradeId));
            //        查询年级学生
            List<Student> studentList = studentService.list(Wrappers.<Student>lambdaQuery().eq(Student::getGradeId, gradeId));

            for (int i = 0; i < gradeCourseList.size(); i++) {
                for (int j = 0; j < studentList.size(); j++) {


                    Escore escore = new Escore();
                    escore.setExamId(exam.getId());
                    escore.setGradeId(exam.getGradeId());
                    escore.setClazzId(studentList.get(j).getClazzId());
                    escore.setCourseId(gradeCourseList.get(i).getCourseId());
                    escore.setStudentId(studentList.get(j).getId());
                    escoreService.save(escore);
                }
            }

            return Result.success();
        }
        return Result.error("-1", "添加失败");

    }

    /**
     * 查询各年级考试次数
     */
    @ApiOperation("查询各年级考试次数")
    @GetMapping("/count")
    public Result<?> examCount(){
        List<GradecountVo> gradecountVos = examService.examCount();
        return Result.success(gradecountVos);
    }
}
