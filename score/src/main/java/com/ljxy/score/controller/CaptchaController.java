package com.ljxy.score.controller;

import com.wf.captcha.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "验证码模块")
@RestController
public class CaptchaController {

    @ApiOperation("生成验证码")
    @GetMapping(value = "/getcode" ,produces = "image/jpeg")
    public void getCode(HttpServletResponse response ,HttpServletRequest request) throws Exception{
        ServletOutputStream outputStream = response.getOutputStream();
        //算术验证码 数字加减乘除，建议2位运算就行：captcha.setLen(2)
//        ArithmeticCaptcha captcha = new ArithmeticCaptcha(120,40);

        //中文验证码
//        ChineseCaptcha captcha =new ChineseCaptcha(120,40);

        //英文与数字验证码
//        SpecCaptcha captcha = new SpecCaptcha();

        //英文与数字动态验证码
        GifCaptcha captcha = new GifCaptcha(120, 40);

        //中文动态验证码
//        ChineseGifCaptcha captcha = new ChineseGifCaptcha(120, 40);

        //设置长度
        captcha.setLen(4);

        //自定义设置
//        captcha.setFont(font,style,size);
//        captcha.setHeight(height);
//        captcha.setWidth(width);

        //算术专属
//        System.out.println(captcha.getArithmeticString()); //获取运算的公式：3+2=？

        String result = captcha.text();
        System.out.println(result);
        request.getSession().setAttribute("captcha",result);
        captcha.out(outputStream);
    }

}
