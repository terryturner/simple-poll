package com.simple.poll.service.flow;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.simple.poll.database.entity.User;
import com.simple.poll.model.input.GuestSigninModel;
import com.simple.poll.model.input.GuestSignupModel;
import com.simple.poll.model.output.ApiResponseBody;
import com.simple.poll.model.output.GuestSigninOutput;
import com.simple.poll.service.ResponseModelService;
import com.simple.poll.service.UserCRUDService;
import com.simple.poll.service.UserCheckService;
import com.simple.poll.service.UserModelService;

@Service
public class UserFlowService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ResponseModelService responseService;
    
    @Autowired
    private UserCheckService userCheckService;
    
    @Autowired
    private UserModelService userModelService;
    
    @Autowired
    private UserCRUDService userCRUDService;
    
    public ResponseEntity<ApiResponseBody<Object>> runSignUpFlow(GuestSignupModel input) throws Exception {
        // step: business check
        userCheckService.checkGuestSignupRule(input);
        
        // step: build entity
        User entity = userModelService.createUserBySignup(input);
        
        // step: persist
        entity = userCRUDService.save(entity);
        
        // step: response with HTTP 201
        return responseService.getDefaultCreateSuccessResult();
    }
    
    public ResponseEntity<ApiResponseBody<GuestSigninOutput>> runSignInFlow(GuestSigninModel input) throws Exception {
        // step: business check
        userCheckService.checkGuestSigninRule(input);
        
        // step: find the entity
        Optional<User> optionalUser = userCRUDService.findByUserName(input.getUserName());
        User entity = optionalUser.get();
        
        System.out.println("dbg : id " + entity.getId() + " "  + entity.getName());
        // step: update the entity information
        userModelService.updateUserBySignin(entity);
        
        // step: persist
        entity = userCRUDService.save(entity);

        // step: build the output
        GuestSigninOutput output = userModelService.buildResponseToken(entity);

        // step: response
        return responseService.getSuccessResult(output);
    }
}
