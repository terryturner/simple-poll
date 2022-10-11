package com.simple.poll.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.poll.controller.ExceptionBusiness;
import com.simple.poll.controller.ExceptionResourceMissing;
import com.simple.poll.database.entity.Election;
import com.simple.poll.database.entity.ElectionState;
import com.simple.poll.model.input.ElectionCreateModel;

@Service
public class ElectionCheckService extends BaseCheckService {

    @Autowired
    private ElectionCRUDService electionCRUDService;
    
    @Autowired
    private CandidateCRUDService candidateCRUDService;
    
    public void checkElectionCreateRule(ElectionCreateModel input) throws ExceptionBusiness {
        List<String> errors = new ArrayList<>();
        Optional<Election> optionalElection = electionCRUDService.findByName(input.getElectionName());
        if (optionalElection.isPresent()) {
            errors.add("The eleciton is already created");
        }
        buildBusinessException(errors);
    }
    
    public void checkElectionStartRule(Long electionId) throws ExceptionBusiness, ExceptionResourceMissing {
        List<String> errors = new ArrayList<>();
        Optional<Election> optionalElection = electionCRUDService.findById(electionId);
        if (optionalElection.isEmpty()) {
            errors.add("eleciton");
        }
        buildResourceMissingException(errors);
        
        Election election = optionalElection.get();
        if (ElectionState.VALID.equals(election.getState()) == false) {
            errors.add("The eleciton state is not in valid");
        }
        long candidateSize = candidateCRUDService.countByElectionId(electionId);
        if (candidateSize < 2) {
            errors.add("The eleciton has insufficient candidates");
        }
        buildBusinessException(errors);
    }
    
    public void checkElectionCloseRule(Long electionId) throws ExceptionBusiness, ExceptionResourceMissing {
        List<String> errors = new ArrayList<>();
        Optional<Election> optionalElection = electionCRUDService.findById(electionId);
        if (optionalElection.isEmpty()) {
            errors.add("eleciton");
        }
        buildResourceMissingException(errors);
        
        Election election = optionalElection.get();
        if (ElectionState.POLLING.equals(election.getState()) == false) {
            errors.add("The eleciton state is not in polling");
        }
        buildBusinessException(errors);
    }
    
    
    public void checkGetSimpleStatics(Long electionId) throws ExceptionBusiness, ExceptionResourceMissing {
        List<String> errors = new ArrayList<>();
        Optional<Election> optionalElection = electionCRUDService.findById(electionId);
        if (optionalElection.isEmpty()) {
            errors.add("eleciton");
        }
        buildResourceMissingException(errors);
    }
}
