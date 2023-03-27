package com.ljxy.score.entity.Vo;

import lombok.Data;

/**
 * 查询考试各班平均分
 * @author SuSheng
 * @date 2022/3/14 22:24
 */

@Data
public class ClazzAvgVo {
    private String clazzName;
    private float clazzAvg;
}

