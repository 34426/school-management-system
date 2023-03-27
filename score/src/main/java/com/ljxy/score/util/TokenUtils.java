package com.ljxy.score.util;


import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

/**
 * @author SuSheng
 * @date 2022/3/20 23:48
 */

public class TokenUtils {

    /**
     * 生成token
     * @return
     */
    public static String getToken(String userId,String sign){
        return JWT.create().withAudience(userId) //将userId保存到token里面，作为载荷
        .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //两小时token过期
        .sign(Algorithm.HMAC256(sign)); //以password作为token的密钥
    }
}
