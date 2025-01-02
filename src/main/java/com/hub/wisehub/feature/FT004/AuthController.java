package com.hub.wisehub.feature.FT004;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hub.wisehub.config.JwtUtil;
import com.hub.wisehub.data.dto.UserLoginRequestDTO;
import com.hub.wisehub.feature.UtilsService;

@RestController
public class AuthController {
    
    private final JwtUtil jwtUtil;
    @Autowired private AuthService authService;
    @Autowired private UtilsService utilsService;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ObjectNode> login(@RequestBody UserLoginRequestDTO loginRequest) {
        boolean flgLoginValido = authService.verificarUsuario(loginRequest);
        return flgLoginValido == true ? ResponseEntity.status(HttpStatus.CREATED).body(this.generateJWT(loginRequest)) : 
         ResponseEntity.status(HttpStatus.BAD_REQUEST).body(utilsService.response("O usuário não existe por favor verifique as informações enviadas"));
    }

    protected ObjectNode generateJWT(UserLoginRequestDTO loginRequest){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        response.put("token", jwtUtil.generateToken(loginRequest.getLogin()));
        return response;
    }
}
