package com.lianxiao.demo.simpleserver.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class TokenUtils {
    private static final String SECRET="abcdefghijklmnopqrstuvwxyz";
    private static final int EXPIRE_MINUTE=15;

    //  phone生成token 过期时间15分钟
    public static String generateJwtToken(String phone){
        JWTCreator.Builder builder= JWT.create();
        builder.withClaim("phone",phone);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 60*EXPIRE_MINUTE);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(SECRET));
    }

    //  token解析成Claims 过期返回空
    public static Map<String,Claim> verify(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (Exception e){
            return null;
        }
    }
}
