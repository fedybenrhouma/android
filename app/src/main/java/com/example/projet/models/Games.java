package com.example.projet.models;

import com.google.gson.annotations.SerializedName;

public class Games {
    @SerializedName("appearences")
    private int appearances;
    
    @SerializedName("lineups")
    private int lineups;
    
    @SerializedName("minutes")
    private int minutes;
    
    @SerializedName("position")
    private String position;
    
    @SerializedName("rating")
    private String rating;
    
    @SerializedName("captain")
    private boolean captain;

    public Games() {}

    public int getAppearances() { return appearances; }
    public void setAppearances(int appearances) { this.appearances = appearances; }

    public int getLineups() { return lineups; }
    public void setLineups(int lineups) { this.lineups = lineups; }

    public int getMinutes() { return minutes; }
    public void setMinutes(int minutes) { this.minutes = minutes; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public boolean isCaptain() { return captain; }
    public void setCaptain(boolean captain) { this.captain = captain; }
}
