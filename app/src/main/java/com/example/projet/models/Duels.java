package com.example.projet.models;

import com.google.gson.annotations.SerializedName;

public class Duels {
    @SerializedName("total")
    private Integer total;
    
    @SerializedName("won")
    private Integer won;

    public Duels() {}

    public Integer getTotal() { return total; }
    public void setTotal(Integer total) { this.total = total; }

    public Integer getWon() { return won; }
    public void setWon(Integer won) { this.won = won; }
}
