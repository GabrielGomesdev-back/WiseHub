package com.hub.wisehub.data.dto;

import java.util.ArrayList;
import java.util.List;

import com.hub.wisehub.data.entity.Usuario;

public class UsuarioService {
    List<Usuario> usersList = new ArrayList<>();

    public List<Usuario> getUsersList() {
        return usersList;
    }
}
