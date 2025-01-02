package com.hub.wisehub.feature.FT002;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hub.wisehub.data.dto.EmailDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/FT002/mail")
public class EmailController {

    @Autowired private EmailService emailService;
 
    @Operation(
        summary = "Endpoint responsável por enviar um email",         
        security = @SecurityRequirement(name = "bearerAuth")    
    )
    @ApiResponses(value = { 
    @ApiResponse(responseCode = "200", description = "Email enviado com sucesso !", 
        content = @Content),
    @ApiResponse(responseCode = "400", description = "Não foi possível executar o envio do email.", 
        content = @Content)
    })

    @PostMapping(value = "/enviar-email")    
    public ResponseEntity<ObjectNode> enviarEmail(@Valid @RequestBody EmailDTO json) {
        return emailService.enviarEmail(json.getDestinatario(), json.getAssunto(), json.getCorpo());
    }
}
