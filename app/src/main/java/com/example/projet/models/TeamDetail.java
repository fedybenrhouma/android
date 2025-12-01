package com.example.projet.models;

import com.google.gson.annotations.SerializedName;

public class TeamDetail {
    @SerializedName("team")
    private TeamInfo team;
    
    @SerializedName("venue")
    private Venue venue;

    public TeamDetail() {}

    public TeamInfo getTeam() { return team; }
    public void setTeam(TeamInfo team) { this.team = team; }

    public Venue getVenue() { return venue; }
    public void setVenue(Venue venue) { this.venue = venue; }
}
