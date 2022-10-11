package com.simple.poll.config.security;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JwtKeyPair {

    @Value("${security.jwt.private}")
    private String privateKey;
    
    @Value("${security.jwt.public}")
    private String publicKey;
    
    @Bean
    public PrivateKey jwtPrivateKey() throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException {
        PrivateKey key = JwtUtils.convertPrivateKey(privateKey);
        return key;
    }
    
    @Bean
    public PublicKey jwtPublicKey() throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException {
        PublicKey key = JwtUtils.convertPublicKey(publicKey);
        return key;
    }
}
