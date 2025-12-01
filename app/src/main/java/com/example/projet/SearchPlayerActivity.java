package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet.adapter.PlayerAdapter;
import com.example.projet.api.FootballAPI;
import com.example.projet.api.RetrofitClient;
import com.example.projet.config.ApiConfig;
import com.example.projet.models.Player;
import com.example.projet.models.PlayerSearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPlayerActivity extends AppCompatActivity {

    private EditText searchInput;
    private RecyclerView playerRecyclerView;
    private ProgressBar progressBar;
    private PlayerAdapter playerAdapter;
    private List<Player> playerList;
    private static final String TAG = "SearchPlayerActivity";
    private static final int[] POPULAR_LEAGUES = {39, 140, 61, 135, 78, 253}; // Premier League, La Liga, Ligue 1, Serie A, Bundesliga, MLS
    private int currentLeagueIndex = 0;
    private String currentSearchName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_player);

        searchInput = findViewById(R.id.searchInput);
        playerRecyclerView = findViewById(R.id.playerRecyclerView);
        progressBar = findViewById(R.id.progressBar);

        playerList = new ArrayList<>();
        playerAdapter = new PlayerAdapter(playerList, this);
        playerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        playerRecyclerView.setAdapter(playerAdapter);

        // Search on button click
        findViewById(R.id.searchButton).setOnClickListener(v -> searchPlayer());
    }

    private void searchPlayer() {
        try {
            String playerName = searchInput.getText().toString().trim();

            if (playerName.isEmpty()) {
                Toast.makeText(this, "Please enter a player name", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            playerList.clear();
            playerAdapter.notifyDataSetChanged();
            currentLeagueIndex = 0;
            currentSearchName = playerName;

            searchInNextLeague();
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error", e);
            Toast.makeText(this, "Unexpected error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void searchInNextLeague() {
        if (currentLeagueIndex >= POPULAR_LEAGUES.length) {
            progressBar.setVisibility(View.GONE);
            if (playerList.isEmpty()) {
                Toast.makeText(this, "No players found in any league", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        int leagueId = POPULAR_LEAGUES[currentLeagueIndex];
        FootballAPI api = RetrofitClient.getFootballAPI();
        Call<PlayerSearchResponse> call = api.searchPlayer(currentSearchName, leagueId, ApiConfig.DEFAULT_SEASON);

        call.enqueue(new Callback<PlayerSearchResponse>() {
            @Override
            public void onResponse(Call<PlayerSearchResponse> call, Response<PlayerSearchResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        PlayerSearchResponse body = response.body();
                        
                        // Check for API errors
                        if (body.getErrors() != null && !body.getErrors().isEmpty()) {
                            StringBuilder errorMsg = new StringBuilder();
                            for (String key : body.getErrors().keySet()) {
                                errorMsg.append(key).append(": ").append(body.getErrors().get(key)).append("\n");
                            }
                            Log.e(TAG, "API Errors: " + errorMsg);
                            // Try next league on error
                            currentLeagueIndex++;
                            searchInNextLeague();
                            return;
                        }
                        
                        List<Player> players = body.getResponse();

                        if (players != null && !players.isEmpty()) {
                            playerList.addAll(players);
                            playerAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SearchPlayerActivity.this, "Found " + players.size() + " players", Toast.LENGTH_SHORT).show();
                        } else {
                            // No players found in this league, try next
                            currentLeagueIndex++;
                            searchInNextLeague();
                        }
                    } else {
                        String errorMsg = "Error: " + response.code();
                        Log.e(TAG, errorMsg);
                        // Try next league on error
                        currentLeagueIndex++;
                        searchInNextLeague();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error in onResponse", e);
                    // Try next league on exception
                    currentLeagueIndex++;
                    searchInNextLeague();
                }
            }

            @Override
            public void onFailure(Call<PlayerSearchResponse> call, Throwable t) {
                Log.e(TAG, "API call failed", t);
                // Try next league on failure
                currentLeagueIndex++;
                searchInNextLeague();
            }
        });
    }
}

