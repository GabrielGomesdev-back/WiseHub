package com.hub.wisehub.feature.FT004;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hub.wisehub.data.dto.UserLoginRequestDTO;
import com.hub.wisehub.data.entity.Usuario;
import com.hub.wisehub.data.repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired private UsuarioRepository usuarioRepository;
    
    public boolean verificarUsuario(UserLoginRequestDTO loginRequest){
        Usuario usuario = usuarioRepository.findUsuarioByLogin(loginRequest.getLogin());
        return usuario.getLogin() == loginRequest.getLogin() && Base64.getEncoder().encodeToString(loginRequest.getLogin().getBytes()) == usuario.getSenha() ? false : true;
    }
}
