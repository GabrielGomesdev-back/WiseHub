package com.hub.wisehub.data.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDTO {
    
    @NotEmpty String destinatario;
    @NotEmpty String assunto;
    @NotEmpty String corpo;
}
