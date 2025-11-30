package com.example.projet.models;

import com.google.gson.annotations.SerializedName;

public class Penalty {
    @SerializedName("won")
    private Integer won;
    
    @SerializedName("scored")
    private Integer scored;
    
    @SerializedName("missed")
    private Integer missed;

    public Penalty() {}

    public Integer getWon() { return won; }
    public void setWon(Integer won) { this.won = won; }

    public Integer getScored() { return scored; }
    public void setScored(Integer scored) { this.scored = scored; }

    public Integer getMissed() { return missed; }
    public void setMissed(Integer missed) { this.missed = missed; }
}
