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

            FootballAPI api = RetrofitClient.getFootballAPI();
            Call<PlayerSearchResponse> call = api.searchPlayer(playerName, ApiConfig.DEFAULT_SEASON);

            call.enqueue(new Callback<PlayerSearchResponse>() {
                @Override
                public void onResponse(Call<PlayerSearchResponse> call, Response<PlayerSearchResponse> response) {
                    try {
                        progressBar.setVisibility(View.GONE);

                        if (response.isSuccessful() && response.body() != null) {
                            List<Player> players = response.body().getResponse();

                            if (players != null && !players.isEmpty()) {
                                playerList.addAll(players);
                                playerAdapter.notifyDataSetChanged();
                                Toast.makeText(SearchPlayerActivity.this, "Found " + players.size() + " players", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SearchPlayerActivity.this, "No players found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            String errorMsg = "Error: " + response.code();
                            Log.e(TAG, errorMsg);
                            Toast.makeText(SearchPlayerActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error in onResponse", e);
                        Toast.makeText(SearchPlayerActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PlayerSearchResponse> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Log.e(TAG, "API call failed", t);
                    Toast.makeText(SearchPlayerActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error", e);
            Toast.makeText(this, "Unexpected error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

