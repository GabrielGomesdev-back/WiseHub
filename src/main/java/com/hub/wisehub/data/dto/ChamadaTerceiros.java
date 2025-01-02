package com.hub.wisehub.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChamadaTerceiros {
    
    private String playerName;
    private String season;
    private String team;

    public ChamadaTerceiros(String playerName, String season, String team) {
        this.playerName = playerName;
        this.season = season;
        this.team = team;
    }
}
