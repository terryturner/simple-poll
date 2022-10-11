package com.simple.poll.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simple.poll.database.entity.User;
import com.simple.poll.database.repository.UserRepository;

@Service
public class UserCRUDService {

    @Autowired
    private UserRepository userRepository;

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
    
    public List<User> findAllSignupUserDuplication(String userName, String email, String identify) {
        return userRepository.findAllByNameOrEmailOrHkIdentifyIgnoreCase(userName, email, identify);
    }
    
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByName(userName);
    }

}
