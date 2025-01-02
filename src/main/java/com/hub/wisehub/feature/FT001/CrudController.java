package com.hub.wisehub.feature.FT001;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hub.wisehub.data.dto.AtualizarUsuarioDTO;
import com.hub.wisehub.data.dto.CriarUsuarioDTO;
import com.hub.wisehub.data.dto.UsuarioService;
import com.hub.wisehub.data.entity.Usuario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/FT001/usuarios")
public class CrudController {

    @Autowired private CrudService crudService;
   
    @Operation(
        summary = "Pesquisar *todos* usuários cadastrados",         
        security = @SecurityRequirement(name = "bearerAuth")    
    )
    @ApiResponses(value = { 
    @ApiResponse(responseCode = "200", description = "Retorna os usuários que foram encontrados", 
        content = { @Content(mediaType = "application/json", 
        schema = @Schema(implementation = UsuarioService.class)) })
    })
    @GetMapping
    public List<Usuario> pesquisarUsuarios() {
        return crudService.pesquisarUsuarios();
    }

    @Operation(
        summary = "Pesquisar um usuário pelo Id",         
        security = @SecurityRequirement(name = "bearerAuth")    
    )
    @ApiResponses(value = { 
    @ApiResponse(responseCode = "200", description = "Encontrou o usuário", 
        content = { @Content(mediaType = "application/json", 
        schema = @Schema(implementation = Usuario.class)) }),
    @ApiResponse(responseCode = "400", description = "Usuário não foi encontrado", 
        content = @Content)
    })

    @GetMapping("/{id}")
    public ResponseEntity<ObjectNode> pesquisarPorId(@Parameter(description = "Id de um usuário previamente cadastrado") @PathVariable Long id) {
        return crudService.pesquisarPorId(id);
    }

    @Operation(summary = "Cadastrar um novo usuário")
    @ApiResponses(value = { 
    @ApiResponse(responseCode = "200", description = "Usuário gravado com sucesso !", 
        content = { @Content(mediaType = "application/json", 
        schema = @Schema(implementation = ObjectNode.class)) }),
    @ApiResponse(responseCode = "400", description = "Não foi possível cadastrar um usuário", 
        content = @Content)
    })

    @PostMapping("/criar")
    public ResponseEntity<ObjectNode> criarUsuario(@Parameter(description = "JSON com informações do usuário para ser cadastrado")  @Valid @RequestBody CriarUsuarioDTO json) {
        return  crudService.criarUsuario(json);
    }

    @Operation(
        summary = "Atualizar um usuário",         
        security = @SecurityRequirement(name = "bearerAuth")    
    )
    @ApiResponses(value = { 
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso !", 
        content = { @Content(mediaType = "application/json", 
        schema = @Schema(implementation = ObjectNode.class)) }),
    @ApiResponse(responseCode = "400", description = "Não foi possível atualizar um usuário", 
        content = @Content)
    })

    @PutMapping("/atualizar")
    public ResponseEntity<ObjectNode> atualizarUsuario(@Parameter(description = "JSON com informações do usuário para ser Atualizado")  @Valid @RequestBody AtualizarUsuarioDTO json) {
        return  crudService.atualizarUsuario(json);
    }

    @Operation(
        summary = "Deletar um usuário existente",         
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = { 
    @ApiResponse(responseCode = "200", description = "Usuário Deletado com sucesso !", 
        content = { @Content(mediaType = "application/json", 
        schema = @Schema(implementation = ObjectNode.class)) }),
    @ApiResponse(responseCode = "400", description = "Não foi possível deletar o usuário", 
        content = @Content)
    })

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<ObjectNode> deleteUser(@Parameter(description = "Id do usuário à ser deletado (o id deve pertencer a um usuário previamente cadastrado)") @PathVariable Long id) {
        return crudService.deleteUser(id);
    }
}
