package com.simple.poll.config.security;

import java.security.PublicKey;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.simple.poll.model.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Component
public class JwtValidator {
    private Logger logger = LoggerFactory.getLogger(JwtValidator.class);
    
    public Claims validate(String token, PublicKey publicKey) throws Exception {
        
        Claims body = null;
        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
            
            body = jws.getBody();
            
            if (!body.containsKey(JwtTokenKeys.KEY_JWTUSER_ID)) {
                throw new Exception("JWT missing ID");
            }
            if (!body.containsKey(JwtTokenKeys.KEY_JWTUSER_USER_NAME)) {
                throw new Exception("JWT missing user name");
            }
            if (!body.containsKey(JwtTokenKeys.KEY_JWTUSER_ROLE)) {
                throw new Exception("JWT missing role");
            }
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
        
        return body;
    }
    
    public JwtUser getBody(@NotNull Claims claims) {
        JwtUser jwtUser = new JwtUser();

        jwtUser.setId(((Integer) claims.get(JwtTokenKeys.KEY_JWTUSER_ID)).longValue());
        jwtUser.setUserName((String) claims.get(JwtTokenKeys.KEY_JWTUSER_USER_NAME));
        jwtUser.setRoles((String) claims.get(JwtTokenKeys.KEY_JWTUSER_ROLE));
        
        return jwtUser;
    }
    
}