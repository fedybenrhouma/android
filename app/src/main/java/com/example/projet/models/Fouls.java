package com.example.projet.models;

import com.google.gson.annotations.SerializedName;

public class Fouls {
    @SerializedName("drawn")
    private Integer drawn;
    
    @SerializedName("committed")
    private Integer committed;

    public Fouls() {}

    public Integer getDrawn() { return drawn; }
    public void setDrawn(Integer drawn) { this.drawn = drawn; }

    public Integer getCommitted() { return committed; }
    public void setCommitted(Integer committed) { this.committed = committed; }
}
