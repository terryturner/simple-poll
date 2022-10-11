package com.simple.poll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple.poll.model.output.ApiResponseBody;
import com.simple.poll.service.ResponseModelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "帳號使用者相關API")
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private ResponseModelService responseService;
    
    @GetMapping(value = "/hello")
    @ApiOperation(value = "一般使用者帳號API測試")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<ApiResponseBody<Object>> helloPrint()
            throws Exception {
        return responseService.getDefaultSuccessResult();
    }
    
    @GetMapping(value = "/admin")
    @ApiOperation(value = "管理帳號API測試")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponseBody<Object>> adminPrint()
            throws Exception {
        return responseService.getDefaultSuccessResult();
    }

}
