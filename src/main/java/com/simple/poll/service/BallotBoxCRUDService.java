package com.simple.poll.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simple.poll.database.entity.BallotBox;
import com.simple.poll.database.entity.Election;
import com.simple.poll.database.entity.User;
import com.simple.poll.database.repository.BallotBoxRepository;

@Service
public class BallotBoxCRUDService {

    @Autowired
    private BallotBoxRepository ballotBoxRepository;
    
    @CacheEvict(value = "electionCache", key = "#entity.election.id")
    @Transactional
    public BallotBox save(BallotBox entity) {
        return ballotBoxRepository.save(entity);
    }
    
    public Optional<BallotBox> findById(Long id) {
        return ballotBoxRepository.findById(id);
    }
    
    public long countByCandidateId(Long candidateId) {
        return ballotBoxRepository.countByCandidateId(candidateId);
    }
    
    public Page<BallotBox> findAllByCandidateId(Long candidateId, Pageable pageable) {
        return ballotBoxRepository.findAllByCandidateId(candidateId, pageable);
    }
    
    public boolean isVoted(User voter, Election elction) {
        return ballotBoxRepository.existsByVoterIdAndElectionId(voter.getId(), elction.getId());
    }
}
