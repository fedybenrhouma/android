package com.example.projet.models;

import com.google.gson.annotations.SerializedName;

public class Substitutes {
    @SerializedName("in")
    private int in;
    
    @SerializedName("out")
    private int out;
    
    @SerializedName("bench")
    private int bench;

    public Substitutes() {}

    public int getIn() { return in; }
    public void setIn(int in) { this.in = in; }

    public int getOut() { return out; }
    public void setOut(int out) { this.out = out; }

    public int getBench() { return bench; }
    public void setBench(int bench) { this.bench = bench; }
}
