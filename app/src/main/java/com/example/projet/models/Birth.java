package com.example.projet.models;

import com.google.gson.annotations.SerializedName;

public class Birth {
    @SerializedName("date")
    private String date;
    
    @SerializedName("place")
    private String place;
    
    @SerializedName("country")
    private String country;

    public Birth() {}

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}
