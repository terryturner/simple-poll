package com.simple.poll.database.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.simple.poll.database.entity.Candidate;
import com.simple.poll.database.entity.INumberOfVotes;


@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Page<Candidate> findAll(Pageable pageable);
    
    List<Candidate> findAllByElectionId(Long electionId);
    
    List<Candidate> findAllByElectionNameIgnoreCase(String electionName);
    
    Optional<Candidate> findByElectionNameIgnoreCaseAndUserName(String electionName, String userName);
    
    long countByElectionId(Long electionId);
    
    @Query(nativeQuery = true,
            value ="SELECT "
            + "c.election_id AS electionId, u.id as candidateId, u.user_name as candidateName, bb.count as numberOfVotes "
            + "FROM candidates c "
            + "LEFT JOIN users u "
            + "ON c.user_id = u.id "
            + "LEFT JOIN ("
            + "  SELECT election_id, candidate_id, count(candidate_id) "
            + "  FROM ballot_box "
            + "  WHERE election_id = :electionId "
            + "  GROUP BY election_id, candidate_id "
            + ") bb "
            + "ON c.id = bb.candidate_id and c.election_id = bb.election_id "
            + "WHERE c.election_id = :electionId "
            + "ORDER BY c.election_id, bb.count "
            + " ")
    List<INumberOfVotes> getNumberOfVotesByElectionId(@Param("electionId") Long electionId);
}
