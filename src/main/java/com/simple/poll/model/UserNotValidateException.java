package com.simple.poll.model;

import org.springframework.security.core.AuthenticationException;

public class UserNotValidateException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a <code>BadCredentialsException</code> with the specified message.
     * @param msg the detail message
     */
    public UserNotValidateException(String msg) {
        super(msg);
    }

    /**
     * Constructs a <code>BadCredentialsException</code> with the specified message and
     * root cause.
     * @param msg the detail message
     * @param cause root cause
     */
    public UserNotValidateException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

