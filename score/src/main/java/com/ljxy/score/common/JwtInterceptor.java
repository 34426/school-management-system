package com.ljxy.score.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ljxy.score.entity.User;
import com.ljxy.score.exception.ServiceException;
import com.ljxy.score.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author SuSheng
 * @date 2022/3/28 14:58
 */

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
//        获取请求头的token
        String token = request.getHeader("token");
//        如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
//        执行认证
        if (StrUtil.isBlank(token)){
            throw   new ServiceException(Constants.CODE_401,"无token，请重新登录");
        }
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException e) {
            throw new ServiceException(Constants.CODE_401,"token验证失败");
        }
//        根据token中的userid查询数据库
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount,userId));
        if (user ==null){
            throw new ServiceException(Constants.CODE_401,"用户不存在,请重新登录");
        }
//        验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_401,"token验证失败");
        }
        return true;
    }
}
