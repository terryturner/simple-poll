package com.simple.poll.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simple.poll.database.entity.Candidate;
import com.simple.poll.database.entity.Election;
import com.simple.poll.database.entity.INumberOfVotes;
import com.simple.poll.database.repository.CandidateRepository;
import com.simple.poll.model.output.ElectionSimpleStatics;

@Service
public class CandidateCRUDService {

    @Autowired
    private CandidateRepository candidateRepository;
    
    @Autowired
    private ElectionModelService electionModelService;
    
    @Transactional
    public Candidate save(Candidate entity) {
        return candidateRepository.save(entity);
    }
    
    public Optional<Candidate> findById(Long id) {
        return candidateRepository.findById(id);
    }
    
    public long countByElectionId(Long electionId) {
        return candidateRepository.countByElectionId(electionId);
    }
    
    @Cacheable(value = "electionCache", key = "#eleciton.id")
    public ElectionSimpleStatics numberOfVotesByElectionId(Election eleciton) {
        List<INumberOfVotes> list = candidateRepository.getNumberOfVotesByElectionId(eleciton.getId());
        ElectionSimpleStatics statics = electionModelService.buildResponseElectionSimpleInfo(eleciton, list);
        return statics;
    }
}
