package com.simple.poll.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simple.poll.database.entity.BallotBox;


@Repository
public interface BallotBoxRepository extends JpaRepository<BallotBox, Long> {

    Page<BallotBox> findAll(Pageable pageable);
    
    Page<BallotBox> findAllByCandidateId(Long candidateId, Pageable pageable);
    
    long countByCandidateId(Long candidateId);
    
    long countByElectionNameIgnoreCase(String electionName);
    
    boolean existsByVoterIdAndElectionNameIgnoreCase(Long voterId, String electionName);
    
    boolean existsByVoterIdAndElectionId(Long voterId, Long electionId);
}
