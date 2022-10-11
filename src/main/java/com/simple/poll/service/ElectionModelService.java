package com.simple.poll.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.poll.database.entity.Election;
import com.simple.poll.database.entity.ElectionState;
import com.simple.poll.database.entity.INumberOfVotes;
import com.simple.poll.helper.BaseEntityHelper;
import com.simple.poll.model.input.ElectionCreateModel;
import com.simple.poll.model.output.ElectionSimpleStatics;
import com.simple.poll.model.output.CandidateSimpleStatics;
import com.simple.poll.model.output.ElectionOutput;

@Service
public class ElectionModelService {
    @Autowired
    private BaseEntityHelper prebuildHelper;
    
    public Election createDefaultEntity() {
        Election entity = new Election();
        prebuildHelper.preBuildSetting(entity, "system");
        entity.setState(ElectionState.INVALID);
        entity.setCandidates(new ArrayList<>());
        return entity;
    }
    
    public Election createElection(ElectionCreateModel input) {
        Election entity = createDefaultEntity();
        entity.setName(input.getElectionName());
        return entity;
    }
    
    public Election updateElectionByEventStart(Election entity) {
        prebuildHelper.preBuildSetting(entity, "system");
        entity.setState(ElectionState.POLLING);
        return entity;
    }
    
    public Election updateElectionByEventClose(Election entity) {
        prebuildHelper.preBuildSetting(entity, "system");
        entity.setState(ElectionState.CLOSED);
        return entity;
    }
    
    public ElectionOutput buildResponseElection(Election entity) {
        ElectionOutput response = new ElectionOutput();
        response.setElectionId(entity.getId());
        response.setElectionName(entity.getName());
        response.setElectionState(entity.getState());
        return response;
    }
    
    public ElectionSimpleStatics buildResponseElectionSimpleInfo(Election entity, List<INumberOfVotes> list) {
        ElectionSimpleStatics response = new ElectionSimpleStatics();
        response.setElectionId(entity.getId());
        response.setElectionName(entity.getName());
        response.setElectionState(entity.getState());
        
        Collections.sort(list, (info1, info2) -> {
            Long votes1 = 0L;
            Long votes2 = 0L;
            if (info1.getNumberOfVotes() != null)
                votes1 = info1.getNumberOfVotes();
            if (info2.getNumberOfVotes() != null)
                votes2 = info2.getNumberOfVotes();
            return votes2.compareTo(votes1);
        });
        List<CandidateSimpleStatics> statics = new ArrayList<>();
        for (INumberOfVotes info : list) {
            CandidateSimpleStatics data = new CandidateSimpleStatics();
            data.setCandidateId(info.getCandidateId());
            data.setCandidateName(info.getCandidateName());
            data.setNumberOfVotes(info.getNumberOfVotes());
            statics.add(data);
        }
        response.setStatics(statics);
        return response;
    }
}
