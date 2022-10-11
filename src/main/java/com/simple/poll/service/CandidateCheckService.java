package com.simple.poll.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.poll.controller.ExceptionBusiness;
import com.simple.poll.controller.ExceptionResourceMissing;
import com.simple.poll.database.entity.Candidate;
import com.simple.poll.database.entity.Election;
import com.simple.poll.database.entity.User;
import com.simple.poll.model.input.CandidateCreateModel;

@Service
public class CandidateCheckService extends BaseCheckService {

    @Autowired
    private ElectionCRUDService electionCRUDService;
    
    @Autowired
    private UserCRUDService userCRUDService;
    
    @Autowired
    private CandidateCRUDService candidateCRUDService;
    
    public void checkAddCandidateRule(CandidateCreateModel input) throws ExceptionBusiness, ExceptionResourceMissing {
        List<String> errors = new ArrayList<>();
        Optional<Election> optionalElection = electionCRUDService.findById(input.getElectionId());
        if (optionalElection.isEmpty()) {
            errors.add("eleciton");
        }
        Optional<User> optionalUser = userCRUDService.findById(input.getUserId());
        if (optionalUser.isEmpty()) {
            errors.add("user");
        }
        buildResourceMissingException(errors);
    }
    
    public void checkAddCandidateRule(Long candidateId) throws ExceptionBusiness, ExceptionResourceMissing {
        List<String> errors = new ArrayList<>();
        Optional<Candidate> optionalCandidate = candidateCRUDService.findById(candidateId);
        if (optionalCandidate.isEmpty()) {
            errors.add("candidate");
        }
        buildResourceMissingException(errors);
    }
}
