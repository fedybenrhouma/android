package com.example.projet.models;

import com.google.gson.annotations.SerializedName;

public class Cards {
    @SerializedName("yellow")
    private Integer yellow;
    
    @SerializedName("yellowred")
    private Integer yellowRed;
    
    @SerializedName("red")
    private Integer red;

    public Cards() {}

    public Integer getYellow() { return yellow; }
    public void setYellow(Integer yellow) { this.yellow = yellow; }

    public Integer getYellowRed() { return yellowRed; }
    public void setYellowRed(Integer yellowRed) { this.yellowRed = yellowRed; }

    public Integer getRed() { return red; }
    public void setRed(Integer red) { this.red = red; }
}
