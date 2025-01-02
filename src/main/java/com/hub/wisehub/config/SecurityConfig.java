package com.hub.wisehub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  // Desabilita a proteção CSRF
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .anyRequest().permitAll()  // Permite todas as requisições sem autenticação
            )
            .formLogin().disable()  // Desabilita o formulário de login
            .httpBasic().disable();  // Desabilita a autenticação básica

        return http.build();
    }
}
