package com.example.projet.models;

import com.google.gson.annotations.SerializedName;

public class Statistics {
    @SerializedName("team")
    private Team team;
    
    @SerializedName("league")
    private League league;
    
    @SerializedName("games")
    private Games games;
    
    @SerializedName("substitutes")
    private Substitutes substitutes;
    
    @SerializedName("shots")
    private Shots shots;
    
    @SerializedName("goals")
    private Goals goals;
    
    @SerializedName("passes")
    private Passes passes;
    
    @SerializedName("tackles")
    private Tackles tackles;
    
    @SerializedName("duels")
    private Duels duels;
    
    @SerializedName("dribbles")
    private Dribbles dribbles;
    
    @SerializedName("fouls")
    private Fouls fouls;
    
    @SerializedName("cards")
    private Cards cards;
    
    @SerializedName("penalty")
    private Penalty penalty;

    public Statistics() {}

    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }

    public League getLeague() { return league; }
    public void setLeague(League league) { this.league = league; }

    public Games getGames() { return games; }
    public void setGames(Games games) { this.games = games; }

    public Substitutes getSubstitutes() { return substitutes; }
    public void setSubstitutes(Substitutes substitutes) { this.substitutes = substitutes; }

    public Shots getShots() { return shots; }
    public void setShots(Shots shots) { this.shots = shots; }

    public Goals getGoals() { return goals; }
    public void setGoals(Goals goals) { this.goals = goals; }

    public Passes getPasses() { return passes; }
    public void setPasses(Passes passes) { this.passes = passes; }

    public Tackles getTackles() { return tackles; }
    public void setTackles(Tackles tackles) { this.tackles = tackles; }
    
    public Duels getDuels() { return duels; }
    public void setDuels(Duels duels) { this.duels = duels; }
    
    public Dribbles getDribbles() { return dribbles; }
    public void setDribbles(Dribbles dribbles) { this.dribbles = dribbles; }
    
    public Fouls getFouls() { return fouls; }
    public void setFouls(Fouls fouls) { this.fouls = fouls; }
    
    public Cards getCards() { return cards; }
    public void setCards(Cards cards) { this.cards = cards; }
    
    public Penalty getPenalty() { return penalty; }
    public void setPenalty(Penalty penalty) { this.penalty = penalty; }
}
