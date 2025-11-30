package com.example.projet.models;

import com.google.gson.annotations.SerializedName;

public class Tackles {
    @SerializedName("total")
    private Integer total;
    
    @SerializedName("blocks")
    private Integer blocks;
    
    @SerializedName("interceptions")
    private Integer interceptions;

    public Tackles() {}

    public Integer getTotal() { return total; }
    public void setTotal(Integer total) { this.total = total; }

    public Integer getBlocks() { return blocks; }
    public void setBlocks(Integer blocks) { this.blocks = blocks; }

    public Integer getInterceptions() { return interceptions; }
    public void setInterceptions(Integer interceptions) { this.interceptions = interceptions; }
}
