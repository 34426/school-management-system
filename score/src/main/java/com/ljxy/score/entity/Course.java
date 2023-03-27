package com.ljxy.score.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 课程实体类
 */
@Data
@TableName("course")
@ExcelTarget("course")
public class Course implements Serializable {

    @TableId(type = IdType.AUTO)
    @Excel(name = "编号")
    private Integer id;
    @Excel(name = "课程")
    private String name;

    @TableField(exist = false)
    private Escore escore;
}
