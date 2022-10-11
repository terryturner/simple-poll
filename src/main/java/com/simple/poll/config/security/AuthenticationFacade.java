package com.simple.poll.config.security;

import java.security.PublicKey;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.simple.poll.database.entity.UserRole;
import com.simple.poll.model.JwtUser;
import com.simple.poll.model.JwtUserDetails;

import io.jsonwebtoken.Claims;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Autowired
    private PublicKey jwtPublicKey;
    @Autowired
    private JwtValidator jwtValidator;
    
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public Optional<JwtUserDetails> getOptionalJwtUserDetail() {
        if (SecurityContextHolder.getContext() != null) {
            // SecurityContext context = SecurityContextHolder.getContext();
            Authentication auth = getAuthentication();
            if (auth != null) {
                if (auth.getPrincipal() != null && auth.getPrincipal() instanceof JwtUserDetails) {
                    return Optional.of((JwtUserDetails) auth.getPrincipal());
                }
            }
        }
        return Optional.empty();
    }
    
    @Override
    public Optional<UserRole> getOptionalJwtUserRoles(JwtUserDetails jwtUserDetails) throws Exception
    {
        //取出 user role
        Claims claims = jwtValidator.validate(jwtUserDetails.getToken(), jwtPublicKey);
        JwtUser jwtUser = jwtValidator.getBody(claims);
        String roles = jwtUser.getRoles();
        return Optional.ofNullable(UserRole.valueOf(roles));
    }

}
