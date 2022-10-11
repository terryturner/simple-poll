package com.simple.poll.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simple.poll.model.input.ElectionCreateModel;
import com.simple.poll.model.output.ApiResponseBody;
import com.simple.poll.model.output.ElectionOutput;
import com.simple.poll.model.output.ElectionSimpleStatics;
import com.simple.poll.service.flow.ElectionFlowService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "選舉相關API")
@RestController
@RequestMapping("/election")
public class ElectionController {
    
    @Autowired
    private ElectionFlowService electionFlowService;

    @PostMapping(value = "/new")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "建立一場選舉資訊")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponseBody<ElectionOutput>> createElection(@Valid @RequestBody ElectionCreateModel input)
            throws Exception {
        return electionFlowService.runCreateElection(input);
    }

    @PutMapping(value = "/{id}/start")
    @ApiOperation(value = "設置投票開始")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "選舉ID", paramType = "path", dataType = "Integer", dataTypeClass = Long.class)
    })
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponseBody<ElectionOutput>> startElection(@NotNull @PathVariable("id") Long electionId)
            throws Exception {
        return electionFlowService.runStartTheElection(electionId);
    }
    
    @PutMapping(value = "/{id}/close")
    @ApiOperation(value = "設置投票結束")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "選舉ID", paramType = "path", dataType = "Integer", dataTypeClass = Long.class)
    })
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponseBody<ElectionOutput>> closeElection(@NotNull @PathVariable("id") Long electionId)
            throws Exception {
        return electionFlowService.runCloseTheElection(electionId);
    }
    
    @GetMapping(value = "/{id}/statics/simple")
    @ApiOperation(value = "查詢目前選舉每個參選人投票數")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "選舉ID", paramType = "path", dataType = "Integer", dataTypeClass = Long.class)
    })
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponseBody<ElectionSimpleStatics>> getSimpleStatics(@NotNull @PathVariable("id") Long electionId)
            throws Exception {
        return electionFlowService.runGetSimpleStatics(electionId);
    }
}
