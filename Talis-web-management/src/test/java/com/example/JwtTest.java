package com.example;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {


    @Test
    public void genJwt(){
        Map<String,Object> map = new HashMap<>();
        map.put("username","sunpeihong");
        map.put("password","123456");

        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, "c3VucGVpaG9uZw==")
                .addClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .compact();
        System.out.println(jwt);
    }


    @Test
    public void decoderJwt(){
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJuYW1lIjoic3VucGVpaG9uZyIsImV4cCI6MTc2NTMzNjU0Mn0.tl8T_PsUw4I9ChfUXqV3P_edi83M8eQUA40yjzToxNY";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey("c3VucGVpaG9uZw==")
                .parseClaimsJws(jwt);
        System.out.println(claimsJws);
    }
}
