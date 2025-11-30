package com.example.projet.models;

import com.google.gson.annotations.SerializedName;

public class League {
    @SerializedName("id")
    private int id;
    
    @SerializedName("name")
    private String name;
    
    @SerializedName("country")
    private String country;
    
    @SerializedName("logo")
    private String logo;
    
    @SerializedName("flag")
    private String flag;
    
    @SerializedName("season")
    private int season;

    public League() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }

    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }

    public int getSeason() { return season; }
    public void setSeason(int season) { this.season = season; }
}
