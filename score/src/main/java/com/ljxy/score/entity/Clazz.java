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
 * 班级实体类
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false,of = "name")
@TableName("clazz")
public class Clazz implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @Excel(name = "班级")
    @NonNull
    private String name;

    private Integer gradeId;

    @TableField(exist = false)
    private Grade gradeList;

}
