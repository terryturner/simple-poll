package com.simple.poll.config.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@EnableWebSecurity
@Configuration
public class JwtSecurityConfig {

    @Autowired
    private JwtAuthenticationProvider authenticationProvider;
    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonHttpMessageConverter;
    @Autowired
    private JwtSuccessHandler jwtSuccessHandler;

    @Value("${spring.application.name}")
    private String appName;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

    private JwtAuthenticationTokenFilter authenticationTokenFilter(String filterPath) {
        JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter(filterPath);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(jwtSuccessHandler);
        filter.setHttpMessageConverter(jacksonHttpMessageConverter);
        return filter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String[] authPaths = new String[] {
                "/user/**",
                "/election/**",
                "/candidate/**",
                "/ballotBox/**",
        };
        // step: add custom filter for need to authentication API path
        for(String authPath : authPaths) {
            http
                .addFilterBefore(authenticationTokenFilter(authPath), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(authPath)
                .authenticated();
        }

        // permit other API for guest
        http.csrf().disable()
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().cacheControl();
        return http.build();
    }
}