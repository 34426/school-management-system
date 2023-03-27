package com.ljxy.score.entity.Vo;

import lombok.Data;

import java.util.List;

/**
 * @author SuSheng
 * @date 2022/3/23 17:47
 */

@Data
public class GradeVo {
    private Integer id;
    private String name;
    private List<Integer> courseList;
}
