package com.example.projet.models;

import com.google.gson.annotations.SerializedName;

public class Goals {
    @SerializedName("total")
    private int total;
    
    @SerializedName("conceded")
    private int conceded;
    
    @SerializedName("assists")
    private int assists;

    public Goals() {}

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public int getConceded() { return conceded; }
    public void setConceded(int conceded) { this.conceded = conceded; }

    public int getAssists() { return assists; }
    public void setAssists(int assists) { this.assists = assists; }
}
