package com.stroganov.warehouseservice.filter;

import com.stroganov.warehouseservice.exception.jwtTokenException;
import com.stroganov.warehouseservice.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class CustomAuthenticationProvider {

    @Autowired
    private  JwtService jwtService;

    public CustomAuthenticationProvider(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(String jwtToken) throws jwtTokenException {
        if (Boolean.FALSE.equals(jwtService.isTokenExpired(jwtToken))) {
            String username = jwtService.extractUsername(jwtToken);
            List<String> authoritiesList = jwtService.extractAuthorities(jwtToken);
            Collection<? extends GrantedAuthority> authorities = authoritiesList
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            return new UsernamePasswordAuthenticationToken(username, jwtToken, authorities);
        } else {
          throw new jwtTokenException("Token is Expired");
        }
    }
}
