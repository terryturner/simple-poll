package com.simple.poll.config.security;

import java.util.Optional;

import org.springframework.security.core.Authentication;

import com.simple.poll.database.entity.UserRole;
import com.simple.poll.model.JwtUserDetails;


public interface IAuthenticationFacade {
	 Authentication getAuthentication();
     
     Optional<JwtUserDetails> getOptionalJwtUserDetail();
     
     Optional<UserRole> getOptionalJwtUserRoles(JwtUserDetails jwtUserDetails) throws Exception;
}
