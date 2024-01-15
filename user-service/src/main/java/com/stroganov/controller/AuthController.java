package com.stroganov.controller;

import com.stroganov.domain.dto.user.UserAuthorizationRequest;
import com.stroganov.service.JwtService;
import com.stroganov.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Tag(name = "Authentication Controller", description = "Authentication APIs")
@RestController
@RequestMapping("/api")
@RefreshScope //
@Validated
public class AuthController {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private UserService userService;

    @Autowired
    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody UserAuthorizationRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
          //  Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
           return jwtService.generateToken(authRequest.getUsername(),authentication.getAuthorities());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
