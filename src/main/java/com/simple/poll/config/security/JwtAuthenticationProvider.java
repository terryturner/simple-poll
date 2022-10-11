package com.simple.poll.config.security;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.simple.poll.model.JwtAuthenticationToken;
import com.simple.poll.model.JwtUser;
import com.simple.poll.model.JwtUserDetails;
import com.simple.poll.model.UserNotValidateException;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    
    @Autowired
    private JwtValidator validator;
    
    @Autowired
    private PublicKey jwtPublicKey;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username,
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws UserNotValidateException {

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;
        String token = jwtAuthenticationToken.getToken();
        
        Optional<JwtUser> optionalJwtUser = Optional.empty();
        try {
            optionalJwtUser = Optional.ofNullable(validator.getBody(validator.validate(token, jwtPublicKey)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (optionalJwtUser.isEmpty()) throw new UserNotValidateException("User not validate");

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        JwtUser jwtUser = optionalJwtUser.get();
        if (jwtUser.getRoles() != null) {
            grantedAuthorities.addAll(AuthorityUtils.createAuthorityList(jwtUser.getRoles()));
        }
        
        UserDetails userDetails = new JwtUserDetails(jwtUser.getUserName(), jwtUser.getId(), token, grantedAuthorities);
        return userDetails;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (JwtAuthenticationToken.class.isAssignableFrom(aClass));
    }
}