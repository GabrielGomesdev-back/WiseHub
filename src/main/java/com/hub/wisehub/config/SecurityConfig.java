package com.hub.wisehub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfiguration{
    
    private final FiltroJwt filtroJwt;

    @Autowired
    public SecurityConfig(FiltroJwt filtroJwt) {
        this.filtroJwt = filtroJwt;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // Desabilita CSRF (não necessário para JWT)
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers()
                    .frameOptions().sameOrigin()  // Configura como sem estado (stateless) para JWT
            .and()
            .authorizeRequests()
            .requestMatchers(
                "/auth/login",
                "/h2-console/**", 
                "/auth/register", 
                "/swagger-ui/**",  // Permite o acesso ao Swagger UI
                "/v3/api-docs/**", // Permite o acesso à documentação do Swagger
                "/swagger-resources/**", // Permite o acesso aos recursos do Swagger
                "/webjars/**", 
                "/api/v1/FT001/usuarios/criar").permitAll()  // Permite o acesso sem autenticação para login e registro
            .anyRequest().authenticated()  // Requer autenticação para outras rotas
            .and()
            .addFilterBefore(filtroJwt, UsernamePasswordAuthenticationFilter.class);  // Adiciona o filtro JWT antes do filtro de autenticação padrão

        return http.build();  // Retorna o SecurityFilterChain configurado
    }
}
