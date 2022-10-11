package com.simple.poll.service;

import java.security.PrivateKey;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.simple.poll.config.security.JwtGenerator;
import com.simple.poll.database.entity.UserRole;
import com.simple.poll.database.entity.User;
import com.simple.poll.helper.BaseEntityHelper;
import com.simple.poll.model.JwtUser;
import com.simple.poll.model.input.GuestSignupModel;
import com.simple.poll.model.output.GuestSigninOutput;

import io.jsonwebtoken.Claims;

@Service
public class UserModelService {
    
    @Autowired
    private BaseEntityHelper prebuildHelper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtGenerator jwtGenerator;
    
    @Autowired
    private PrivateKey jwtPrivateKey;
    
    @Value("${spring.application.name}")
    private String applicationName;

    public User createDefaultEntity() {
        User entity = new User();
        prebuildHelper.preBuildSetting(entity, "system");
        entity.setRole(UserRole.USER);
        entity.setUserStatus(false);
        return entity;
    }

    public User createUserBySignup(GuestSignupModel input) {
        User user = createDefaultEntity();
        user.setName(input.getUserName());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setEmail(input.getEmail().toLowerCase());
        user.setHkIdentify(input.getHkIdentify().toUpperCase());
        user.setUserStatus(true);
        return user;
    }
    
    public String generateToken(User entity) {
        JwtUser jwtUser = new JwtUser();
        jwtUser.setId(entity.getId());
        jwtUser.setUserName(entity.getName());
        jwtUser.setRoles(entity.getRole().name());
        //step: generate user token
        Claims claims = jwtGenerator.generateClaims(applicationName, jwtUser);
        return jwtGenerator.generateTokenKey(jwtPrivateKey, claims);        
    }
    
    public void updateUserBySignin(User entity) {
        prebuildHelper.preBuildSetting(entity, entity.getId().toString());
        entity.setLastLogin(LocalDateTime.now());
    }
    
    public GuestSigninOutput buildResponseToken(User entity) {
        GuestSigninOutput response = new GuestSigninOutput();
        response.setTokenId(generateToken(entity));
        return response;
    }
}
