package com.simple.poll.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.simple.poll.controller.ExceptionBusiness;
import com.simple.poll.database.entity.User;
import com.simple.poll.model.input.GuestSigninModel;
import com.simple.poll.model.input.GuestSignupModel;

@Service
public class UserCheckService {

    @Autowired
    private UserCRUDService userCRUDService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void checkGuestSignupRule(GuestSignupModel input) throws ExceptionBusiness {
        List<User> users = userCRUDService.findAllSignupUserDuplication(input.getUserName(), input.getEmail(), input.getHkIdentify());
        if (users.size() > 0) {
            throw new ExceptionBusiness("The user is already signup");
        }
    }

    public void checkGuestSigninRule(GuestSigninModel input) throws ExceptionBusiness {
        Optional<User> optionalUser = userCRUDService.findByUserName(input.getUserName());
        if (optionalUser.isEmpty()) {
            throw new ExceptionBusiness("The user is not exists");
        }
        
        if (!passwordEncoder.matches(input.getPassword(), optionalUser.get().getPassword())) {
            throw new ExceptionBusiness("The password is incorrect");
        }
    }
}
