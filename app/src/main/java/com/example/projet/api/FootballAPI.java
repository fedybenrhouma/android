package com.example.projet.api;

import com.example.projet.models.PlayerSearchResponse;
import com.example.projet.models.TeamSearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FootballAPI {
    /**
     * Search for players by name
     * @param search Player name to search for
     * @param league League ID (optional, helps narrow results)
     * @param season Season (e.g., 2023, 2024)
     * @return PlayerSearchResponse containing list of players
     */
    @GET("players")
    Call<PlayerSearchResponse> searchPlayer(
            @Query("search") String search,
            @Query("league") Integer league,
            @Query("season") int season
    );

    /**
     * Get player by ID with their statistics
     */
    @GET("players")
    Call<PlayerSearchResponse> getPlayerById(
            @Query("id") int id,
            @Query("season") int season
    );

    /**
     * Get team by ID with their information
     */
    @GET("teams")
    Call<TeamSearchResponse> getTeamById(
            @Query("id") int id
    );
}

