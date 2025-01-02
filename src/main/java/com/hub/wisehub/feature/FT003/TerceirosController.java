package com.hub.wisehub.feature.FT003;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hub.wisehub.data.entity.Usuario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/FT003/nba")
public class TerceirosController {

    @Autowired TerceirosService terceirosService;

    @Operation(summary = "Consutar a escalação e status dos jogadores de times da NBA na temporada atual")
    @ApiResponses(value = { 
    @ApiResponse(responseCode = "200", description = "Encontrou informações sobre o time !", 
        content = { @Content(mediaType = "application/json", 
        schema = @Schema(implementation = ObjectNode.class)) }),
    @ApiResponse(responseCode = "400", description = "Não encontrou informações sobre o time.", 
        content = @Content)
    })

    @GetMapping("/{time}")
    public ResponseEntity<ObjectNode> pesquisarTimePelaSigla(@Parameter(description = "Sigla de um time da NBA") @PathVariable String time) {
        return terceirosService.pesquisarTimePelaSigla(time);
    }
    
}
