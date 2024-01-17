package com.stroganov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class InternalJWTManager {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${service.username}")
    private String serviceUsername;
    @Value("${service.password}")
    private String servicePassword;

    @Autowired
    public InternalJWTManager(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String getInterServiceToken() {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(serviceUsername, servicePassword));
        if (authentication.isAuthenticated()) {
            //  Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            return jwtService.generateToken(serviceUsername, authentication.getAuthorities());
        } else {
            throw new UsernameNotFoundException("invalid service credentials !");
        }
    }
}
