package com.hub.wisehub.feature.FT003;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hub.wisehub.feature.FT003.Client.NbaClient;

import feign.Feign;

import com.hub.wisehub.data.dto.ChamadaTerceiros;
import com.hub.wisehub.feature.UtilsService;

@Service
public class TerceirosService {
    
    @Autowired UtilsService utilsService;
    @Value("${wiseHub.url.terceiro}") private String url;

    public ResponseEntity<ObjectNode> pesquisarTimePelaSigla(String time) {
        ObjectMapper mapper = new ObjectMapper();
        LocalDate dataAtual = LocalDate.now();
        int anoAtual = dataAtual.getYear();
        try {
            NbaClient nbaClient = Feign.builder().target(NbaClient.class, url);
            String responseApi = nbaClient.chamarNbaApi(new ChamadaTerceiros("", ""+anoAtual, time));    
            ObjectNode responseApiFormatada = this.formatArrayNode("Time", (ArrayNode) mapper.readTree(responseApi));
            return ResponseEntity.status(HttpStatus.OK).body(responseApiFormatada);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(utilsService.response("Time não encontrado por favor revise a sigla e consulte novamente.")); 
        }
    }   

    public ObjectNode formatArrayNode(String atributo, ArrayNode node){
        ObjectMapper mapper = new ObjectMapper();
        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ArrayNode response = nodeFactory.arrayNode();
        ObjectNode formatado = mapper.createObjectNode();
        for(JsonNode registro: node){
            ObjectNode registroTratado = mapper.createObjectNode();
            registroTratado.put("nomeJogador", registro.get("playerName"));
            registroTratado.put("posicao", registro.get("position"));
            registroTratado.put("minutosJogados", registro.get("minutesPlayed"));
            registroTratado.put("pocentagemAsstencia", registro.get("assistPercent"));
            registroTratado.put("porcentagemRoubos", registro.get("stealPercent"));
            registroTratado.put("porcentagemBloqueios", registro.get("blockPercent"));
            registroTratado.put("porcentagemPerdas", registro.get("turnoverPercent"));
            registroTratado.put("porcentagemPontos", registro.get("per"));
            response.add(registroTratado);
        }
        formatado.put(atributo, response);
        return formatado;
    }

    public ObjectNode pesquisarTimeEJogador(String jogador, String time) {
        ObjectMapper mapper = new ObjectMapper();
        LocalDate dataAtual = LocalDate.now();
        int anoAtual = dataAtual.getYear();
        try {
            ObjectNode responseCompleta = mapper.createObjectNode();

            NbaClient nbaClient = Feign.builder().target(NbaClient.class, url);
            String responseTime = nbaClient.chamarNbaApi(new ChamadaTerceiros("", ""+anoAtual, time));    
            responseCompleta = this.formatArrayNode(responseCompleta, "time", (ArrayNode) mapper.readTree(responseTime));

            if(jogador != null && !jogador.isEmpty()){
                String responseJogador = nbaClient.chamarNbaApi(new ChamadaTerceiros(jogador, ""+anoAtual, ""));    
                responseCompleta = this.formatArrayNode(responseCompleta, "jogador", (ArrayNode) mapper.readTree(responseJogador));
            } else {
                responseCompleta.put("jogador", "");
            }
            return responseCompleta;

        } catch (Exception e){
            ObjectNode responseErro = mapper.createObjectNode();
            responseErro.put("jogador", "nenhuma informação encontrada");
            responseErro.put("time", "nenhuma informação encontrada");
            return responseErro;
        }
    }

    public ObjectNode formatArrayNode(ObjectNode objeto, String atributo, ArrayNode node){
        ObjectMapper mapper = new ObjectMapper();
        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ArrayNode response = nodeFactory.arrayNode();
        for(JsonNode registro: node){
            ObjectNode registroTratado = mapper.createObjectNode();
            registroTratado.put("nomeJogador", registro.get("playerName"));
            registroTratado.put("posicao", registro.get("position"));
            registroTratado.put("minutosJogados", registro.get("minutesPlayed"));
            registroTratado.put("pocentagemAsstencia", registro.get("assistPercent"));
            registroTratado.put("porcentagemRoubos", registro.get("stealPercent"));
            registroTratado.put("porcentagemBloqueios", registro.get("blockPercent"));
            registroTratado.put("porcentagemPerdas", registro.get("turnoverPercent"));
            registroTratado.put("porcentagemPontos", registro.get("per"));
            response.add(registroTratado);
        }
        objeto.put(atributo, response);
        return objeto;
    }
}
