package com.simple.poll.service.flow;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.simple.poll.config.security.IAuthenticationFacade;
import com.simple.poll.database.entity.BallotBox;
import com.simple.poll.database.entity.Candidate;
import com.simple.poll.database.entity.Election;
import com.simple.poll.database.entity.User;
import com.simple.poll.model.input.VoteCreateModel;
import com.simple.poll.model.output.ApiResponseBody;
import com.simple.poll.model.output.ElectionSimpleStatics;
import com.simple.poll.service.BallotBoxCRUDService;
import com.simple.poll.service.BallotBoxCheckService;
import com.simple.poll.service.BallotBoxModelService;
import com.simple.poll.service.CandidateCRUDService;
import com.simple.poll.service.ElectionModelService;
import com.simple.poll.service.ResponseModelService;
import com.simple.poll.service.UserCRUDService;

@Service
public class BallotBoxFlowService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ResponseModelService responseService;
    
    @Autowired
    private BallotBoxCRUDService ballotBoxCRUDService;
    
    @Autowired
    private BallotBoxModelService ballotBoxModelService;
    
    @Autowired
    private BallotBoxCheckService ballotBoxCheckService;
    
    @Autowired
    private CandidateCRUDService candidateCRUDService;
    
    @Autowired
    private UserCRUDService userCRUDService;
    
    @Autowired
    private ElectionModelService electionModelService;
    
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    
    public ResponseEntity<ApiResponseBody<ElectionSimpleStatics>> runCreateVote(VoteCreateModel input) throws Exception {
        // step: business check
        ballotBoxCheckService.checkCreateVoteRule(input);
        
        // step: find the entity
        Optional<User> optionalVoter = userCRUDService.findById(authenticationFacade.getOptionalJwtUserDetail().get().getId());
        User voter = optionalVoter.get();
        Optional<Candidate> optionalCandidate = candidateCRUDService.findById(input.getCandidateId());
        Candidate candidate = optionalCandidate.get();
        Election election = candidate.getElection();
        
        // step: build entity
        BallotBox entity = ballotBoxModelService.createCandidate(candidate, voter);
        
        // step: persist
        entity = ballotBoxCRUDService.save(entity);
        
        // step: find the simple statics and build output
        ElectionSimpleStatics output = candidateCRUDService.numberOfVotesByElectionId(election);
        
        // step: response
        return responseService.getCreateSuccessResult(output);
    }
}
