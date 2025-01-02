package com.hub.wisehub.feature;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class UtilsService {
    
    public ObjectNode response(String message){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        response.put("message", message);
        return response;
    }

    public ObjectNode responsErro(String message, Object erro){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        response.put("message", message);
        response.put("erro", erro.toString());
        return response;
    }

    public ObjectNode responseSucesso(){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        response.put("message", "Operação realizada com sucesso !");
        return response;
    }

}
