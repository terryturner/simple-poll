package com.simple.poll.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.poll.config.security.IAuthenticationFacade;
import com.simple.poll.controller.ExceptionBusiness;
import com.simple.poll.controller.ExceptionResourceMissing;
import com.simple.poll.database.entity.Candidate;
import com.simple.poll.database.entity.Election;
import com.simple.poll.database.entity.ElectionState;
import com.simple.poll.database.entity.User;
import com.simple.poll.model.JwtUserDetails;
import com.simple.poll.model.input.VoteCreateModel;

@Service
public class BallotBoxCheckService extends BaseCheckService {

    @Autowired
    private IAuthenticationFacade authenticationFacade;
    
    @Autowired
    private CandidateCRUDService candidateCRUDService;
    
    @Autowired
    private UserCRUDService userCRUDService;
    
    @Autowired
    private BallotBoxCRUDService ballotBoxCRUDService;
    
    public void checkCreateVoteRule(VoteCreateModel input) throws ExceptionBusiness, ExceptionResourceMissing {
        List<String> errors = new ArrayList<>();
        
        Optional<JwtUserDetails> optionalJwtUser = authenticationFacade.getOptionalJwtUserDetail();
        if (optionalJwtUser.isEmpty()) {
            errors.add("JwtToken");
        }
        Optional<User> optionalUser = userCRUDService.findById(optionalJwtUser.get().getId());
        if (optionalUser.isEmpty()) {
            errors.add("user");
        }
        Optional<Candidate> optionalCandidate = candidateCRUDService.findById(input.getCandidateId());
        if (optionalCandidate.isEmpty()) {
            errors.add("candidate");
        }
        buildResourceMissingException(errors);
        
        User voter = optionalUser.get();
        if (voter.getEmail() == null) {
            errors.add("User has an invalid email");
        }
        if (voter.getHkIdentify() == null) {
            errors.add("User has an invalid HK identify");
        }
        Candidate candidate = optionalCandidate.get();
        Election election = candidate.getElection();
        if (election.getId() != input.getElectionId()) {
            errors.add("The election does not have the candidate");
        }
        if (!ElectionState.POLLING.equals(election.getState())) {
            errors.add("The election is not in polling state");
        }
        if (ballotBoxCRUDService.isVoted(voter, election)) {
            errors.add("User already voted this election");
        }
        buildBusinessException(errors);
        
    }
    
    public void checkGetCandidateNumberOfVotesRule(Long candidateId) throws ExceptionBusiness, ExceptionResourceMissing {
        List<String> errors = new ArrayList<>();
        Optional<Candidate> optionalCandidate = candidateCRUDService.findById(candidateId);
        if (optionalCandidate.isEmpty()) {
            errors.add("candidate");
        }
        buildResourceMissingException(errors);
    }
    
    public void checkVoteRule(Long candidateId, Long userId) throws ExceptionBusiness, ExceptionResourceMissing {
        List<String> errors = new ArrayList<>();
        Optional<User> optionalUser = userCRUDService.findById(userId);
        if (optionalUser.isEmpty()) {
            errors.add("voter");
        }
        Optional<Candidate> optionalCandidate = candidateCRUDService.findById(candidateId);
        if (optionalCandidate.isEmpty()) {
            errors.add("candidate");
        }
        buildResourceMissingException(errors);
    }
}
