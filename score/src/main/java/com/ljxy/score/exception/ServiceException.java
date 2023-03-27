package com.ljxy.score.exception;

import lombok.Getter;

/**
 * 自定义异常
 * @author SuSheng
 * @date 2022/3/28 17:24
 */

@Getter
public class ServiceException extends RuntimeException{
    private String code;

    public ServiceException(String code,String msg){
        super(msg);
        this.code = code;
    }
}
