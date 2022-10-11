package com.simple.poll.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.poll.database.entity.BallotBox;
import com.simple.poll.database.entity.Candidate;
import com.simple.poll.database.entity.User;
import com.simple.poll.helper.BaseEntityHelper;

@Service
public class BallotBoxModelService {
    @Autowired
    private BaseEntityHelper prebuildHelper;
    
    public BallotBox createDefaultEntity() {
        BallotBox entity = new BallotBox();
        prebuildHelper.preBuildSetting(entity, "system");
        return entity;
    }
    
    public BallotBox createCandidate(Candidate candidate, User voter) {
        BallotBox entity = createDefaultEntity();
        entity.setCandidate(candidate);
        entity.setElection(candidate.getElection());
        entity.setVoter(voter);
        return entity;
    }
    
    public Map<String, Object> buildResponseCandidateNumberOfVotes(Long candidateId, long numbers) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("candidateId", candidateId);
        response.put("numberOfVotes", numbers);
        return response;
    }
}
