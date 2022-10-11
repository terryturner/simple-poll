package com.simple.poll.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simple.poll.model.input.VoteCreateModel;
import com.simple.poll.model.output.ApiResponseBody;
import com.simple.poll.model.output.ElectionSimpleStatics;
import com.simple.poll.service.flow.BallotBoxFlowService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "投票箱API")
@RestController
@RequestMapping("/ballotBox")
public class BallotBoxController {
    
    @Autowired
    private BallotBoxFlowService ballotBoxFlowService;

    @PostMapping(value = "/vote")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "使用者對某位參選人投票")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<ApiResponseBody<ElectionSimpleStatics>> vote(@Valid @RequestBody VoteCreateModel input)
            throws Exception {
        return ballotBoxFlowService.runCreateVote(input);
    }
}
