package com.example.prac.security;

import java.util.*;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    String str="abcojeknddndksksnldnknksnknnsnmnxmnmxmx";
    SecretKey mySecretKey=Keys.hmacShaKeyFor(str.getBytes());

    public String generateToken(String email){
        return Jwts.builder()
            .subject(email)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis()+1000L*60*60*24))
            .signWith(mySecretKey)
            .compact();
            
    }
    public String extractEmail(String token) {

    return Jwts.parser()
            .verifyWith(mySecretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
}
public Date extractExpiration(String token){
    return Jwts.parser()
            .verifyWith(mySecretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getExpiration();
}


public boolean isTokenValid(String token) {
    return System.currentTimeMillis() < extractExpiration(token).getTime();
}


}
