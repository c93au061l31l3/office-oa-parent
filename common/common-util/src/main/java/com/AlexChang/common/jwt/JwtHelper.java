package com.AlexChang.common.jwt;

import org.springframework.util.StringUtils;
import java.util.Date;
import io.jsonwebtoken.*;

/**
 * ClassName:JwtHelper
 * Description:
 *
 * @author Alex
 * @create 2023-10-12 上午 10:47
 * @Version:1.0
 */

//jwt工具類
public class JwtHelper {

    //token的有效時長
    private static long tokenExpiration = 365 * 24 * 60 * 60 * 1000;
    //簽名加密的密鑰
    private static String tokenSignKey = "123456";

    //根据用户id和用户名称生成token字符串
    public static String createToken(Long userId, String username) {
        String token = Jwts.builder()
                .setSubject("AUTH-USER") //分类
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)) //设置token有效时长
                .claim("userId", userId) //设置主体部分
                .claim("username", username) //设置主体部分
                //签名部分
                .signWith(SignatureAlgorithm.HS512, tokenSignKey) //根據key與算法進行加密
                .compressWith(CompressionCodecs.GZIP) //壓縮token
                .compact();
        return token;
    }

    //从生成token字符串获取用户id
    public static Long getUserId(String token) {
        try {
            if (StringUtils.isEmpty(token)) return null;

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            Integer userId = (Integer) claims.get("userId");
            return userId.longValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //从生成token字符串获取用户名称
    public static String getUsername(String token) {
        try {
            if (StringUtils.isEmpty(token)) return "";

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return (String) claims.get("username");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String token = JwtHelper.createToken(4L, "li4");
        System.out.println(token);
        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUsername(token));
    }
}
