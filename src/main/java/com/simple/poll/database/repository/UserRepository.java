package com.simple.poll.database.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.simple.poll.database.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Pageable pageable);
    
    List<User> findAllByNameOrEmailOrHkIdentifyIgnoreCase(String userName, String email, String identify);
    
    Optional<User> findByName(String userName);
    
    boolean existsByNameAndPassword(String userName, String encodecPassword);
    
}
