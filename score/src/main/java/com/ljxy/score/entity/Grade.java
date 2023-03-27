package com.ljxy.score.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 年级实体类
 */
@Data
@TableName("grade")
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false,of = "name")
public class Grade implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @Excel(name = "年级")
    @NonNull
    private String name;

    @TableField(exist = false)
    private List<Course> courseList;

}
