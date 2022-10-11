package com.simple.poll.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simple.poll.model.input.GuestSigninModel;
import com.simple.poll.model.input.GuestSignupModel;
import com.simple.poll.model.output.ApiResponseBody;
import com.simple.poll.model.output.GuestSigninOutput;
import com.simple.poll.service.ResponseModelService;
import com.simple.poll.service.flow.UserFlowService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "訪客相關API")
@RestController
@RequestMapping("/guest")
public class GuestController {
    
    @Autowired
    private ResponseModelService responseService;
    
    @Autowired
    private UserFlowService userFlowService;
    
    @GetMapping(value = "/hello")
    @ApiOperation(value = "訪客API測試")
    public ResponseEntity<ApiResponseBody<Object>> helloPrint()
            throws Exception {
        return responseService.getDefaultSuccessResult();
    }
    
    @PostMapping(value = "/signup")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "訪客註冊帳號")
    public ResponseEntity<ApiResponseBody<Object>> signup(
            @Valid @RequestBody GuestSignupModel input)
            throws Exception {
        return userFlowService.runSignUpFlow(input);
    }

    @PostMapping(value = "/signin")
    @ApiOperation(value = "訪客登錄帳號")
    public ResponseEntity<ApiResponseBody<GuestSigninOutput>> signin(
            @Valid @RequestBody GuestSigninModel input)
            throws Exception {
        return userFlowService.runSignInFlow(input);
    }
}
