package com.simple.poll.service.flow;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.simple.poll.database.entity.Election;
import com.simple.poll.model.input.ElectionCreateModel;
import com.simple.poll.model.output.ApiResponseBody;
import com.simple.poll.model.output.ElectionOutput;
import com.simple.poll.model.output.ElectionSimpleStatics;
import com.simple.poll.service.CandidateCRUDService;
import com.simple.poll.service.ElectionCRUDService;
import com.simple.poll.service.ElectionCheckService;
import com.simple.poll.service.ElectionModelService;
import com.simple.poll.service.IMailService;
import com.simple.poll.service.ResponseModelService;

@Service
public class ElectionFlowService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ResponseModelService responseService;
    
    @Autowired
    private ElectionCheckService electionCheckService;
    
    @Autowired
    private ElectionModelService electionModelService;
    
    @Autowired
    private ElectionCRUDService electionCRUDService;
    
    @Autowired
    private CandidateCRUDService candidateCRUDService;
    
    @Autowired
    private IMailService mailService;
    
    public ResponseEntity<ApiResponseBody<ElectionOutput>> runCreateElection(ElectionCreateModel input) throws Exception {
        // step: business check
        electionCheckService.checkElectionCreateRule(input);
        
        // step: build entity
        Election entity = electionModelService.createElection(input);
        
        // step: persist
        entity = electionCRUDService.save(entity);
        
        // step: build the output
        ElectionOutput output = electionModelService.buildResponseElection(entity);
        
        // step: response with HTTP 201
        return responseService.getCreateSuccessResult(output);
    }
    
    public ResponseEntity<ApiResponseBody<ElectionOutput>> runStartTheElection(Long electionId) throws Exception {
        // step: business check
        electionCheckService.checkElectionStartRule(electionId);
        
        // step: find the entity
        Optional<Election> optionalEleciton = electionCRUDService.findById(electionId);
        Election entity = optionalEleciton.get();
        
        // step: update the entity information
        electionModelService.updateElectionByEventStart(entity);
        
        // step: persist
        entity = electionCRUDService.save(entity);
        
        // step: build the output
        ElectionOutput output = electionModelService.buildResponseElection(entity);
        
        // step: response
        return responseService.getSuccessResult(output);
    }
    
    public ResponseEntity<ApiResponseBody<ElectionOutput>> runCloseTheElection(Long electionId) throws Exception {
        // step: business check
        electionCheckService.checkElectionCloseRule(electionId);
        
        // step: find the entity
        Optional<Election> optionalEleciton = electionCRUDService.findById(electionId);
        Election entity = optionalEleciton.get();
        
        // step: update the entity information
        electionModelService.updateElectionByEventClose(entity);
        
        // step: persist
        entity = electionCRUDService.save(entity);
        
        // step: build the output
        ElectionOutput output = electionModelService.buildResponseElection(entity);
        
        // step: find the simple statics and build output
        ElectionSimpleStatics statics = candidateCRUDService.numberOfVotesByElectionId(entity);
        
        // step: send the statics of election to all user
        mailService.sendReportToAllUser(statics);
        
        // step: response
        return responseService.getSuccessResult(output);
    }
    
    public ResponseEntity<ApiResponseBody<ElectionSimpleStatics>> runGetSimpleStatics(Long electionId) throws Exception {
        // step: business check
        electionCheckService.checkGetSimpleStatics(electionId);
        
        // step: find the entity
        Optional<Election> optionalEleciton = electionCRUDService.findById(electionId);
        Election entity = optionalEleciton.get();
        
        // step: find the simple statics and build output
        ElectionSimpleStatics output = candidateCRUDService.numberOfVotesByElectionId(entity);
        
        // step: response
        return responseService.getSuccessResult(output);
    }
}
