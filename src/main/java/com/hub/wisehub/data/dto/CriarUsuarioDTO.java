package com.hub.wisehub.data.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarUsuarioDTO {
    
    @NotEmpty(message = "Login é obrigatório")
    private String login;

    @Email(message = "Email inválido")
    @NotEmpty(message = "Email é obrigatório")
    private String email;

    @NotEmpty(message = "O time favorito é obrigatório")
    private String time;

    @NotEmpty(message = "A senha é obrigatória")
    private String senha;

    private String jogador;

}
