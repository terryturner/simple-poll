package com.simple.poll.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simple.poll.database.entity.Election;
import com.simple.poll.database.repository.ElectionRepository;

@Service
public class ElectionCRUDService {

    @Autowired
    private ElectionRepository electionRepository;
    
    @Transactional
    public Election save(Election entity) {
        return electionRepository.save(entity);
    }
    
    public Optional<Election> findById(Long id) {
        return electionRepository.findById(id);
    }
    
    public Optional<Election> findByName(String name) {
        return electionRepository.findByNameIgnoreCase(name);
    }
}
