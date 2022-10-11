package com.simple.poll.service.flow;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.simple.poll.database.entity.BallotBox;
import com.simple.poll.database.entity.Candidate;
import com.simple.poll.database.entity.Election;
import com.simple.poll.database.entity.User;
import com.simple.poll.model.input.CandidateCreateModel;
import com.simple.poll.model.output.ApiResponseBody;
import com.simple.poll.model.output.CandidateCreateOutput;
import com.simple.poll.model.output.CandidateGetNumberOfVotes;
import com.simple.poll.service.BallotBoxCRUDService;
import com.simple.poll.service.CandidateCRUDService;
import com.simple.poll.service.CandidateCheckService;
import com.simple.poll.service.CandidateModelService;
import com.simple.poll.service.ElectionCRUDService;
import com.simple.poll.service.ResponseModelService;
import com.simple.poll.service.UserCRUDService;

@Service
public class CandidateFlowService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ResponseModelService responseService;
    
    @Autowired
    private CandidateCheckService candidateCheckService;
    
    @Autowired
    private CandidateModelService candidateModelService;
    
    @Autowired
    private CandidateCRUDService candidateCRUDService;
    
    @Autowired
    private ElectionCRUDService electionCRUDService;
    
    @Autowired
    private UserCRUDService userCRUDService;
    
    @Autowired
    private BallotBoxCRUDService ballotBoxCRUDService;
    
    
    public ResponseEntity<ApiResponseBody<CandidateCreateOutput>> runAddCandidate(CandidateCreateModel input) throws Exception {
        // step: business check
        candidateCheckService.checkAddCandidateRule(input);
        
        // step: find the entity
        Optional<Election> optionalElection = electionCRUDService.findById(input.getElectionId());
        Election election = optionalElection.get();
        Optional<User> optionalUser = userCRUDService.findById(input.getUserId());
        User user = optionalUser.get();
        
        // step: build entity
        Candidate entity = candidateModelService.createCandidate(election, user);
        
        // step: persist
        election = electionCRUDService.save(entity.getElection());
        
        // step: update the entity after persist
        Optional<Candidate> optionalCandidate = election.getCandidates().stream()
                .filter(candidate -> candidate.getUser().getId().equals(input.getUserId())).findAny();
        entity = optionalCandidate.get();
        
        // step: build the output
        CandidateCreateOutput output = candidateModelService.buildResponseCandidate(entity);
        
        // step: response with HTTP 201
        return responseService.getCreateSuccessResult(output);
    }
    
    public ResponseEntity<ApiResponseBody<CandidateGetNumberOfVotes>> runGetNumberOfVotes(Long candidateId,
            Pageable pageable) throws Exception {
        // step: business check
        candidateCheckService.checkAddCandidateRule(candidateId);
        
        // step: find the entity
        Optional<Candidate> optionalCandidate = candidateCRUDService.findById(candidateId);
        Candidate entity = optionalCandidate.get();
        Page<BallotBox> votes = ballotBoxCRUDService.findAllByCandidateId(candidateId, pageable);
        
        // step: build the output
        CandidateGetNumberOfVotes output = candidateModelService.buildResponseCandidateNumberOfVotes(entity, votes);
        
        // step: response
        return responseService.getSuccessResult(output);
    }
}
