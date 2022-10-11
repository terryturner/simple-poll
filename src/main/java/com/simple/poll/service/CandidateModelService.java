package com.simple.poll.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.simple.poll.database.entity.BallotBox;
import com.simple.poll.database.entity.Candidate;
import com.simple.poll.database.entity.Election;
import com.simple.poll.database.entity.User;
import com.simple.poll.helper.BaseEntityHelper;
import com.simple.poll.model.output.CandidateCreateOutput;
import com.simple.poll.model.output.CandidateGetNumberOfVotes;

@Service
public class CandidateModelService {
    @Autowired
    private BaseEntityHelper prebuildHelper;
    
    public Candidate createDefaultEntity() {
        Candidate entity = new Candidate();
        prebuildHelper.preBuildSetting(entity, "system");
        return entity;
    }
    
    public Candidate createCandidate(Election election, User user) {
        Candidate entity = createDefaultEntity();
        entity.setElection(election);
        entity.setUser(user);
        
        election.getCandidates().add(entity);
        return entity;
    }
    
    public CandidateCreateOutput buildResponseCandidate(Candidate entity) {
        CandidateCreateOutput response = new CandidateCreateOutput();
        response.setId(entity.getId());
        response.setElectionName(entity.getElection().getName());
        response.setElectionState(entity.getElection().getState());
        response.setCandidateName(entity.getUser().getName());
        return response;
    }
    
    public CandidateGetNumberOfVotes buildResponseCandidateNumberOfVotes(Candidate entity, Page<BallotBox> pageInfos) {
        CandidateGetNumberOfVotes response = new CandidateGetNumberOfVotes();
        response.setId(entity.getId());
        response.setCandidateName(entity.getUser().getName());
        response.setElectionName(entity.getElection().getName());
        response.setElectionState(entity.getElection().getState());
        response.setTotalNumberOfVotes(pageInfos.getTotalElements());
        response.setPageIndex(pageInfos.getPageable().getPageNumber());
        response.setPageSize(pageInfos.getPageable().getPageSize());
        response.setVoters(pageInfos.map(vote -> vote.getVoter().getName()).get().collect(Collectors.toList()));

        return response;
    }
}
