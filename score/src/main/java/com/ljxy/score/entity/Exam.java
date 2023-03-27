package com.ljxy.score.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Author SuSheng
 */
@Data
@TableName("exam")
public class Exam {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date time;

    private String remark;

    private Integer type;

    private Integer gradeId;

    private Integer clazzId;

    private Integer courseId;

    private Integer status;

    @TableField(exist = false)
    private Grade gradeList;
}
