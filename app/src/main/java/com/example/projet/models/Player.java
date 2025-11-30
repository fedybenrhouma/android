package com.example.projet.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Player {
    @SerializedName("player")
    private PlayerInfo playerInfo;
    
    @SerializedName("statistics")
    private List<Statistics> statistics;

    public Player() {}

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public List<Statistics> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<Statistics> statistics) {
        this.statistics = statistics;
    }
}
