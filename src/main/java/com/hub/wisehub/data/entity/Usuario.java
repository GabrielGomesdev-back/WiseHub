package com.hub.wisehub.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
