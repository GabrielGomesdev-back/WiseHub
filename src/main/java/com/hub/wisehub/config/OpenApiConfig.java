package com.hub.wisehub.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "WiseHubApi", version = "v1", description = "WiseHub é uma api demonstrativa que contém alguns desenvolvimentos mais avançados em springboot que foram condensados em um cadastro de usuários para receber informações sobre o mundo do basquete"))
@SecurityScheme(
    name = "bearerAuth",
    scheme = "bearer", 
    bearerFormat = "JWT", 
    type = SecuritySchemeType.HTTP
)
public class OpenApiConfig {
    
}
