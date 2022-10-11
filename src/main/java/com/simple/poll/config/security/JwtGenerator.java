package com.simple.poll.config.security;

import java.security.PrivateKey;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.simple.poll.model.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public Claims generateClaims(String issuer, JwtUser jwtUser) {
        String subject = (jwtUser.getId() == null) ? jwtUser.getUserName() : jwtUser.getId().toString();
        Claims claims = Jwts.claims().setSubject(subject);
        
        Date now = new Date();
        Date expiration = Date.from(now.toInstant().atZone(ZoneId.systemDefault()).plusYears(1).toInstant());
        
        claims.setIssuedAt(now);
        claims.setIssuer(issuer);
        claims.setExpiration(expiration);
        claims.put(JwtTokenKeys.KEY_JWTUSER_ID, jwtUser.getId());
        claims.put(JwtTokenKeys.KEY_JWTUSER_ROLE, jwtUser.getRoles());
        claims.put(JwtTokenKeys.KEY_JWTUSER_USER_NAME, jwtUser.getUserName());
        
        return claims;
    }
    
    public String generateTokenKey(PrivateKey secretKey, Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.RS512, secretKey)
                .compact();
    }
}