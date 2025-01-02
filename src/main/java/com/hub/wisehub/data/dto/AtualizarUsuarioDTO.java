package com.hub.wisehub.data.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AtualizarUsuarioDTO {
    
    @NotNull(message = "Id não pode ser nula")
    private Long id;

    @NotEmpty(message = "Nome é obrigatório")
    private String nome;

    @Email(message = "Email inválido")
    @NotEmpty(message = "Email é obrigatório")
    private String email;

    @NotEmpty(message = "O time favorito é obrigatório")
    private String time;

    private String jogador;
}
