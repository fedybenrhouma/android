package com.example.projet.models;

import com.google.gson.annotations.SerializedName;

public class Passes {
    @SerializedName("total")
    private int total;
    
    @SerializedName("key")
    private int key;
    
    @SerializedName("accuracy")
    private String accuracy;

    public Passes() {}

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public int getKey() { return key; }
    public void setKey(int key) { this.key = key; }

    public String getAccuracy() { return accuracy; }
    public void setAccuracy(String accuracy) { this.accuracy = accuracy; }
}
