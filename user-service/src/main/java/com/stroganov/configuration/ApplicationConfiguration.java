package com.stroganov.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

    @Bean
    UserDetailsService getUserDetailsService(@Autowired DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }
}