package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet.adapter.FeaturedPlayerAdapter;
import com.example.projet.api.FootballAPI;
import com.example.projet.api.RetrofitClient;
import com.example.projet.config.ApiConfig;
import com.example.projet.models.Player;
import com.example.projet.models.PlayerSearchResponse;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuthHelper authHelper;
    private FirebaseUser currentUser;
    private Button logoutButton;
    private RecyclerView featuredPlayersRecyclerView;
    private FeaturedPlayerAdapter playerAdapter;
    private ProgressBar progressBar;
    private TextView emptyStateText;

    // List of famous player names to fetch randomly
    private static final List<String> FAMOUS_PLAYERS = Arrays.asList(
            "Messi", "Ronaldo", "Neymar", "Mbappe", "Haaland",
            "Salah", "Benzema", "Lewandowski", "Kane", "De Bruyne"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authHelper = new FirebaseAuthHelper();
        currentUser = authHelper.getCurrentUser();

        // Check if user is logged in
        if (currentUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Initialize views
        logoutButton = findViewById(R.id.logoutButton);
        featuredPlayersRecyclerView = findViewById(R.id.featuredPlayersRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        emptyStateText = findViewById(R.id.emptyStateText);

        // Setup RecyclerView
        featuredPlayersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        playerAdapter = new FeaturedPlayerAdapter(new ArrayList<>(), this::onPlayerClick);
        featuredPlayersRecyclerView.setAdapter(playerAdapter);

        // Setup logout button
        logoutButton.setOnClickListener(v -> logout());

        // Load random featured players
        loadFeaturedPlayers();
    }

    private void loadFeaturedPlayers() {
        progressBar.setVisibility(View.VISIBLE);
        emptyStateText.setVisibility(View.GONE);
        featuredPlayersRecyclerView.setVisibility(View.GONE);

        // Select 5 random players
        List<String> shuffled = new ArrayList<>(FAMOUS_PLAYERS);
        Collections.shuffle(shuffled);
        List<String> selectedPlayers = shuffled.subList(0, Math.min(5, shuffled.size()));

        List<Player> allPlayers = new ArrayList<>();
        final int[] completedRequests = {0};
        final int totalRequests = selectedPlayers.size();

        for (String playerName : selectedPlayers) {
            searchPlayerInLeagues(playerName, new PlayerSearchCallback() {
                @Override
                public void onPlayerFound(Player player) {
                    if (player != null) {
                        allPlayers.add(player);
                    }
                    completedRequests[0]++;
                    
                    if (completedRequests[0] == totalRequests) {
                        displayFeaturedPlayers(allPlayers);
                    }
                }

                @Override
                public void onSearchComplete() {
                    // Not used here
                }
            });
        }
    }

    private void searchPlayerInLeagues(String playerName, PlayerSearchCallback callback) {
        // Search in major leagues sequentially
        int[] leagues = {39, 140, 61, 135, 78}; // Premier League, La Liga, Ligue 1, Serie A, Bundesliga
        searchInNextLeague(playerName, leagues, 0, callback);
    }

    private void searchInNextLeague(String playerName, int[] leagues, int index, PlayerSearchCallback callback) {
        if (index >= leagues.length) {
            callback.onPlayerFound(null);
            return;
        }

        FootballAPI api = RetrofitClient.getFootballAPI();
        Call<PlayerSearchResponse> call = api.searchPlayer(playerName, leagues[index], ApiConfig.DEFAULT_SEASON);

        call.enqueue(new Callback<PlayerSearchResponse>() {
            @Override
            public void onResponse(Call<PlayerSearchResponse> call, Response<PlayerSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PlayerSearchResponse body = response.body();
                    
                    if (body.getResponse() != null && !body.getResponse().isEmpty()) {
                        // Found player in this league
                        callback.onPlayerFound(body.getResponse().get(0));
                        return;
                    }
                }
                
                // Try next league
                searchInNextLeague(playerName, leagues, index + 1, callback);
            }

            @Override
            public void onFailure(Call<PlayerSearchResponse> call, Throwable t) {
                Log.e(TAG, "Search failed for " + playerName, t);
                searchInNextLeague(playerName, leagues, index + 1, callback);
            }
        });
    }

    private void displayFeaturedPlayers(List<Player> players) {
        progressBar.setVisibility(View.GONE);
        
        if (players.isEmpty()) {
            emptyStateText.setVisibility(View.VISIBLE);
            emptyStateText.setText("No featured players available at the moment");
            featuredPlayersRecyclerView.setVisibility(View.GONE);
        } else {
            featuredPlayersRecyclerView.setVisibility(View.VISIBLE);
            emptyStateText.setVisibility(View.GONE);
            playerAdapter.updatePlayers(players);
        }
    }

    private void onPlayerClick(Player player) {
        Intent intent = new Intent(this, PlayerDetailsActivity.class);
        intent.putExtra("player_id", player.getPlayerInfo().getId());
        startActivity(intent);
    }

    private void logout() {
        authHelper.logoutUser();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void searchPlayers(View view) {
        startActivity(new Intent(this, SearchPlayerActivity.class));
    }

    public void viewFavorites(View view) {
        startActivity(new Intent(this, FavoritesActivity.class));
    }

    interface PlayerSearchCallback {
        void onPlayerFound(Player player);
        void onSearchComplete();
    }
}