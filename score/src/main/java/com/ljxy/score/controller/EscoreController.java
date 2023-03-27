package com.ljxy.score.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljxy.score.common.Result;
import com.ljxy.score.entity.Escore;

import com.ljxy.score.entity.Vo.ClazzAvgVo;

import com.ljxy.score.service.EscoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author SuSheng
 * @date 2022/3/3 16:25
 */

@Api(tags = "成绩模块")
@RestController
@RequestMapping("/escore")
public class EscoreController {

    @Resource
    EscoreService escoreService;



    @ApiOperation("查询学生成绩")
    @GetMapping("/studentscore")
    public Result<?> studentScoreList(Integer examId, Integer studentId) {

        List<Escore> scoreList = escoreService.selectScore(examId, studentId, null, null, null);
        return Result.success(scoreList);
    }

    @ApiOperation("管理员查看学生成绩")
    @GetMapping("/admin/student/score")
    public Result<?> adminScore(@RequestParam(defaultValue = "1") Integer PageNum, @RequestParam(defaultValue = "10") Integer PageSize,Integer examId, Integer studentId, Integer courseId, Integer clazzId){
        Page<Escore> page = escoreService.findPage(new Page<>(PageNum, PageSize), examId, studentId,null, clazzId, courseId);
        return Result.success(page);
    }
    /**
     * 查询学生成绩（老师）
     *
     * @param PageNum
     * @param PageSize
     * @param examId
     * @param clazzId
     * @param gradeId
     * @param courseId
     * @return
     */
    @ApiOperation("老师查询学生成绩")
    @GetMapping("/teacherscore")
    public Result<?> teacherScoreList(@RequestParam(defaultValue = "1") Integer PageNum, @RequestParam(defaultValue = "10") Integer PageSize, Integer examId, Integer clazzId, Integer gradeId, Integer courseId,Integer studentId) {


        Page<Escore> page = escoreService.findPage(new Page<>(PageNum, PageSize), examId, studentId, gradeId, clazzId, courseId);
        return Result.success(page);
    }

    /**
     * 修改学生成绩
     *
     * @param escore
     * @return
     */
    @ApiOperation("修改学生成绩")
    @PutMapping("/update")
    public Result<?> updateExamScore(@RequestBody Escore escore) {
        if (escoreService.updateById(escore)) {
            return Result.success();
        }
        return Result.error("-1", "修改失败");
    }

    /**
     * 导入学生成绩
     */
    @ApiOperation("导入学生成绩")
    @PostMapping("/upload")
    public Result<?> importScore(MultipartFile file, Integer examId, Integer gradeId, Integer clazzId, Integer courseId) {
        System.out.println(examId);
        System.out.println(gradeId);
        System.out.println(clazzId);
        System.out.println(courseId);

        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        try {
            List<Escore> list = ExcelImportUtil.importExcel(file.getInputStream(), Escore.class, params);
            list.forEach(escore -> {
                System.out.println(escore);
//               查找学生成绩对应的id
                List<Escore> escores = escoreService.selectScore(examId, escore.getStudentId(), gradeId, clazzId, courseId);
                System.out.println(escores);
                Integer escoreId = escores.get(0).getId();
                System.out.println("成绩id"+escoreId);
                escoreService.updateScore(escoreId, escore.getScore());
            });
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("-1", "上传失败");

    }

    @ApiOperation("查询考试平均分")
    @GetMapping("/clazzavg")
    public Result<?> test(Integer examId,Integer gradeId,Integer clazzId,Integer courseId){
        float v = escoreService.selectAvg(examId,gradeId,clazzId,courseId);
        System.out.println(v);
        return Result.success(v);
    }

    @ApiOperation("各班平均分")
    @GetMapping("/test")
    public Result<?> result(Integer examId,Integer gradeId,Integer courseId){
        List<ClazzAvgVo> clazzAvgVos = escoreService.clazzAvg(examId, gradeId, courseId);
        return Result.success(clazzAvgVos);
    }

    @ApiOperation("最高分")
    @GetMapping("/height/score")
    public Result<?> heightScore(Integer examId,Integer gradeId,Integer courseId,Integer clazzId){
        float score = escoreService.heightScore(examId, gradeId, courseId,clazzId);
        return Result.success(score);
    }

}
