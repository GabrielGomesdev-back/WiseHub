package com.hub.wisehub.feature.FT003.Client;

import org.springframework.web.bind.annotation.RequestParam;

import com.hub.wisehub.data.dto.ChamadaTerceiros;

import feign.QueryMap;
import feign.RequestLine;

public interface NbaClient {
    
    @RequestLine("GET /api/PlayerDataAdvanced/query")
    public String chamarNbaApi( @QueryMap ChamadaTerceiros json );  

}
