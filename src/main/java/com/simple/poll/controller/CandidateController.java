package com.simple.poll.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simple.poll.model.input.CandidateCreateModel;
import com.simple.poll.model.output.ApiResponseBody;
import com.simple.poll.model.output.CandidateCreateOutput;
import com.simple.poll.model.output.CandidateGetNumberOfVotes;
import com.simple.poll.service.flow.CandidateFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "參選人相關API")
@RestController
@RequestMapping("/candidate")
public class CandidateController {
    
    @Autowired
    private CandidateFlowService candidateFlowService;

    @PostMapping(value = "/new")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "將User加入一個選舉的候選人")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponseBody<CandidateCreateOutput>> addUserToElection(@Valid @RequestBody CandidateCreateModel input)
            throws Exception {
        return candidateFlowService.runAddCandidate(input);
    }
    
    @GetMapping(value = "/{id}/votes/number", params = { "page", "size" })
    @ApiOperation(value = "查詢目前參選人的得票數")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "候選人ID", paramType = "path", dataType = "Integer", dataTypeClass = Long.class),
        @ApiImplicitParam(name = "page", value = "分頁頁數", paramType = "query", dataType = "integer", dataTypeClass = Integer.class),
        @ApiImplicitParam(name = "size", value = "分頁大小", paramType = "query", dataType = "integer", dataTypeClass = Integer.class)
    })
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponseBody<CandidateGetNumberOfVotes>> getNumberOfVotes(@NotNull @PathVariable("id") Long candidateId,
            @RequestParam("page") Integer page, 
            @RequestParam("size") Integer size)
            throws Exception {
        PageRequest pageRequest = PageRequest.of(page, size);
        return candidateFlowService.runGetNumberOfVotes(candidateId, pageRequest);
    }
}
