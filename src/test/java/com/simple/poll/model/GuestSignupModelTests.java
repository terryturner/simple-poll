package com.simple.poll.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;

import com.simple.poll.model.input.GuestSignupModel;

@RunWith(MockitoJUnitRunner.class)
public class GuestSignupModelTests {
    
    private static Validator validator;
    
    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    void testUserName() throws Exception {
        GuestSignupModel input = new GuestSignupModel();
        input.setEmail("junit5@email.com");
        input.setPassword("123456");
        input.setHkIdentify("A123456");
        
        // case: user name is NULL
        input.setUserName(null);
        assertFalse(validator.validate(input).isEmpty());
        
        // case: user name length is over limit
        input.setUserName(StringUtils.repeat("*", 51));
        assertFalse(validator.validate(input).isEmpty());
        
        // case: user name is legal
        input.setUserName(StringUtils.repeat("*", 1));
        assertTrue(validator.validate(input).isEmpty());
    }

    @Test
    void testPassword() throws Exception {
        GuestSignupModel input = new GuestSignupModel();
        input.setUserName("junit5");
        input.setEmail("junit5@email.com");
        input.setHkIdentify("A123456");
        
        // case: password is NULL
        input.setPassword(null);
        assertFalse(validator.validate(input).isEmpty());
        
        // case: password length is over limit
        input.setPassword(StringUtils.repeat("*", 51));
        assertFalse(validator.validate(input).isEmpty());
        
        // case: password is legal
        input.setPassword(StringUtils.repeat("*", 1));
        assertTrue(validator.validate(input).isEmpty());
    }
    
    @Test
    void testHkIdentify() throws Exception {
        GuestSignupModel input = new GuestSignupModel();
        input.setUserName("junit5");
        input.setEmail("junit5@email.com");
        input.setPassword("123456");
        
        // case: identify is NULL
        input.setHkIdentify(null);
        assertFalse(validator.validate(input).isEmpty());
        
        // case: identify length is over limit
        input.setHkIdentify("A1234567");
        assertFalse(validator.validate(input).isEmpty());
        
        // case: identify length is shorter than limit
        input.setHkIdentify("A12345");
        assertFalse(validator.validate(input).isEmpty());
        
        // case: identify start with digit
        input.setHkIdentify("0123456");
        assertFalse(validator.validate(input).isEmpty());
        
        // case: identify start with sign
        input.setHkIdentify("$123456");
        assertFalse(validator.validate(input).isEmpty());

        // case: identify digit part contains alpha without capital
        input.setHkIdentify("A12345b");
        assertFalse(validator.validate(input).isEmpty());

        // case: identify digit part contains alpha with capital
        input.setHkIdentify("A1234S6");
        assertFalse(validator.validate(input).isEmpty());
        
        // case: identify is legal
        input.setHkIdentify("A123456");
        assertTrue(validator.validate(input).isEmpty());
    }
    
    @Test
    void testEmail() throws Exception {
        GuestSignupModel input = new GuestSignupModel();
        input.setUserName("junit5");
        input.setPassword("123456");
        input.setHkIdentify("A123456");
        
        // case: email is NULL
        input.setEmail(null);
        assertFalse(validator.validate(input).isEmpty());
        
        // case: email is illegal for email without @
        input.setEmail("junit5mail.com");
        assertFalse(validator.validate(input).isEmpty());
        
        // case: email is illegal for domain only has one segment
        input.setEmail("junit5@com");
        assertFalse(validator.validate(input).isEmpty());
        
        // case: email is legal
        input.setEmail("junit5@mail.com");
        assertTrue(validator.validate(input).isEmpty());
        
        // case: email is legal
        input.setEmail("junit.5@mail.com");
        assertTrue(validator.validate(input).isEmpty());
        
        // case: email is legal
        input.setEmail("junit-5@mail.com");
        assertTrue(validator.validate(input).isEmpty());
        
        // case: email is legal
        input.setEmail("junit5@mail.com.hk");
        assertTrue(validator.validate(input).isEmpty());
        
        // case: email is legal
        input.setEmail("junit_5@mail.com");
        assertTrue(validator.validate(input).isEmpty());
        
        // case: email is illegal for name end with dot
        input.setEmail("junit5.@mail.com");
        assertFalse(validator.validate(input).isEmpty());
        
        // case: email is illegal for name start with dot
        input.setEmail(".junit5@mail.com");
        assertFalse(validator.validate(input).isEmpty());
        
        // case: email is illegal for name has consecutive dots
        input.setEmail("junit..5@mail.com");
        assertFalse(validator.validate(input).isEmpty());

        // case: email is illegal for domain end with dot
        input.setEmail("junit5@mail.com.");
        assertFalse(validator.validate(input).isEmpty());
        
        // case: email is illegal for domain start with dot
        input.setEmail("junit5@.mail.com");
        assertFalse(validator.validate(input).isEmpty());
        
    }
}
