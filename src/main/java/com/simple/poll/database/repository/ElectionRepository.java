package com.simple.poll.database.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simple.poll.database.entity.Election;


@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {

    Page<Election> findAll(Pageable pageable);
    
    Optional<Election> findByNameIgnoreCase(String electionName);
}
