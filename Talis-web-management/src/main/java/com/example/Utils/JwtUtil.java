package com.example.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 */
public class JwtUtil {
    
    /**
     * 默认密钥
     */
    private static final String DEFAULT_SECRET = "c3VucGVpaG9uZw==";
    
    /**
     * 默认过期时间(12小时)，单位毫秒
     */
    private static final long DEFAULT_EXPIRATION = 12 * 60 * 60 * 1000L;
    
    /**
     * 生成JWT令牌
     * @param claims 要包含在令牌中的声明
     * @return JWT令牌字符串
     */
    public static String generateJwt(Map<String, Object> claims) {
        return generateJwt(claims, DEFAULT_SECRET, DEFAULT_EXPIRATION);
    }
    
    /**
     * 生成JWT令牌
     * @param claims 要包含在令牌中的声明
     * @param secret 签名密钥
     * @param expiration 过期时间(毫秒)
     * @return JWT令牌字符串
     */
    public static String generateJwt(Map<String, Object> claims, String secret, long expiration) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, secret)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }
    
    /**
     * 解析JWT令牌
     * @param jwt JWT令牌字符串
     * @return 解析后的声明对象
     */
    public static Jws<Claims> parseJwt(String jwt) {
        return parseJwt(jwt, DEFAULT_SECRET);
    }
    
    /**
     * 解析JWT令牌
     * @param jwt JWT令牌字符串
     * @param secret 签名密钥
     * @return 解析后的声明对象
     */
    public static Jws<Claims> parseJwt(String jwt, String secret) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt);
    }
}
