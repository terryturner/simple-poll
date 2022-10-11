package com.simple.poll.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.simple.poll.AbstractTestContainerTests;
import com.simple.poll.database.entity.User;

@SpringBootTest
public class UserCRUDServiceTests extends AbstractTestContainerTests {

    @Autowired
    private UserCRUDService userCRUDService;
    
    private static User user;
    
    @BeforeAll
    public static void setUp(@Autowired UserModelService userModelService) {
        user = userModelService.createDefaultEntity();
        user.setName("user3");
        user.setPassword("$2a$12$uj5PrAaXwf6nzXGqupZzc.HBdmT9XlSgEt5CCo5w1GqUd7UtcRlp.");
        user.setEmail("user3@gmail.com");
        user.setHkIdentify("A123458");
    }
    
    @Transactional
    @Test
    void testSave() throws Exception {
        user = userCRUDService.save(user);
        assertNotNull(user);
        assertNotNull(user.getId());
    }
    
    @Transactional
    @Test
    void testFindByUserName() throws Exception {
        Optional<User> optionalUser = userCRUDService.findByUserName("user1");
        assertTrue(optionalUser.isPresent());
        assertNotNull(optionalUser.get().getId());
    }
    
    @Transactional
    @Test
    void testFindAllSignupUserDuplication() throws Exception {
        List<User> duplicateUsers = userCRUDService.findAllSignupUserDuplication("user1", "user2@gmail.com", "A654321");
        assertEquals(duplicateUsers.size(), 3);
    }
}
