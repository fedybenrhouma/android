package com.example.projet.models;

import com.google.gson.annotations.SerializedName;

public class TeamInfo {
    @SerializedName("id")
    private int id;
    
    @SerializedName("name")
    private String name;
    
    @SerializedName("code")
    private String code;
    
    @SerializedName("country")
    private String country;
    
    @SerializedName("founded")
    private Integer founded;
    
    @SerializedName("national")
    private boolean national;
    
    @SerializedName("logo")
    private String logo;

    public TeamInfo() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Integer getFounded() { return founded; }
    public void setFounded(Integer founded) { this.founded = founded; }

    public boolean isNational() { return national; }
    public void setNational(boolean national) { this.national = national; }

    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }
}
