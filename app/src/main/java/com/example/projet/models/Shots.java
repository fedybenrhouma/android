package com.example.projet.models;

import com.google.gson.annotations.SerializedName;

public class Shots {
    @SerializedName("total")
    private int total;
    
    @SerializedName("on")
    private int on;

    public Shots() {}

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public int getOn() { return on; }
    public void setOn(int on) { this.on = on; }
}
