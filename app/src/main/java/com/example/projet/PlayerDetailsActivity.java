package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.projet.api.FootballAPI;
import com.example.projet.api.RetrofitClient;
import com.example.projet.config.ApiConfig;
import com.example.projet.models.Player;
import com.example.projet.models.PlayerInfo;
import com.example.projet.models.PlayerSearchResponse;
import com.example.projet.models.Statistics;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerDetailsActivity extends AppCompatActivity {

    private ImageView playerPhoto;
    private TextView playerName;
    private TextView playerAge;
    private TextView playerNationality;
    private TextView playerHeight;
    private TextView playerWeight;
    private TextView teamInfo;
    private TextView statsInfo;
    private ProgressBar progressBar;
    private Button addToFavoritesButton;
    private FavoritesManager favoritesManager;
    private Player currentPlayer;
    private int currentPlayerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        playerPhoto = findViewById(R.id.playerPhoto);
        playerName = findViewById(R.id.playerName);
        playerAge = findViewById(R.id.playerAge);
        playerNationality = findViewById(R.id.playerNationality);
        playerHeight = findViewById(R.id.playerHeight);
        playerWeight = findViewById(R.id.playerWeight);
        teamInfo = findViewById(R.id.teamInfo);
        statsInfo = findViewById(R.id.statsInfo);
        progressBar = findViewById(R.id.progressBar);
        addToFavoritesButton = findViewById(R.id.addToFavoritesButton);

        favoritesManager = new FavoritesManager();

        int playerId = getIntent().getIntExtra("player_id", -1);
        String playerNameIntent = getIntent().getStringExtra("player_name");

        if (playerId != -1) {
            currentPlayerId = playerId;
            updateFavoriteButton();
            loadPlayerDetails(playerId);
        } else {
            Toast.makeText(this, "Invalid player", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadPlayerDetails(int playerId) {
        progressBar.setVisibility(View.VISIBLE);

        FootballAPI api = RetrofitClient.getFootballAPI();
        Call<PlayerSearchResponse> call = api.getPlayerById(playerId, ApiConfig.DEFAULT_SEASON);

        call.enqueue(new Callback<PlayerSearchResponse>() {
            @Override
            public void onResponse(Call<PlayerSearchResponse> call, Response<PlayerSearchResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    java.util.List<Player> players = response.body().getResponse();
                    if (players != null && !players.isEmpty()) {
                        displayPlayerDetails(players.get(0));
                    }
                } else {
                    Toast.makeText(PlayerDetailsActivity.this, "Error loading details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlayerSearchResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PlayerDetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayPlayerDetails(Player player) {
        currentPlayer = player;
        updateFavoriteButton();

        if (player.getPlayerInfo() != null) {
            PlayerInfo info = player.getPlayerInfo();

            playerName.setText(info.getName());
            playerAge.setText("Age: " + info.getAge());
            playerNationality.setText("Nationality: " + info.getNationality());
            playerHeight.setText("Height: " + info.getHeight());
            playerWeight.setText("Weight: " + info.getWeight());

            // Load photo
            if (info.getPhoto() != null && !info.getPhoto().isEmpty()) {
                Glide.with(this)
                        .load(info.getPhoto())
                        .placeholder(R.drawable.ic_placeholder)
                        .into(playerPhoto);
            }

            // Display statistics
            if (player.getStatistics() != null && !player.getStatistics().isEmpty()) {
                Statistics stats = player.getStatistics().get(0);

                String team = stats.getTeam().getName();
                String league = stats.getLeague().getName();
                teamInfo.setText("Team: " + team + "\nLeague: " + league + " (" + stats.getLeague().getSeason() + ")");

                // Make team info clickable
                final int teamId = stats.getTeam().getId();
                final String teamName = stats.getTeam().getName();
                final String teamLogo = stats.getTeam().getLogo();
                
                teamInfo.setClickable(true);
                teamInfo.setFocusable(true);
                teamInfo.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
                teamInfo.setOnClickListener(v -> {
                    Intent intent = new Intent(PlayerDetailsActivity.this, TeamDetailsActivity.class);
                    intent.putExtra("team_id", teamId);
                    intent.putExtra("team_name", teamName);
                    intent.putExtra("team_logo", teamLogo);
                    startActivity(intent);
                });

                String statsText = buildStatsText(stats);
                statsInfo.setText(statsText);
            }
        }
    }

    private String buildStatsText(Statistics stats) {
        StringBuilder sb = new StringBuilder();

        if (stats.getGames() != null) {
            sb.append("Appearances: ").append(stats.getGames().getAppearances()).append("\n");
            sb.append("Minutes Played: ").append(stats.getGames().getMinutes()).append("\n");
            sb.append("Position: ").append(stats.getGames().getPosition()).append("\n");
            if (stats.getGames().getRating() != null) {
                sb.append("Rating: ").append(stats.getGames().getRating()).append("\n");
            }
        }

        if (stats.getGoals() != null) {
            sb.append("\n⚽ Goals: ").append(stats.getGoals().getTotal()).append("\n");
            sb.append("Assists: ").append(stats.getGoals().getAssists()).append("\n");
        }

        if (stats.getShots() != null) {
            sb.append("Shots on Target: ").append(stats.getShots().getOn()).append("/").append(stats.getShots().getTotal()).append("\n");
        }

        if (stats.getPasses() != null) {
            sb.append("Pass Accuracy: ").append(stats.getPasses().getAccuracy()).append("%\n");
        }

        if (stats.getTackles() != null) {
            sb.append("Tackles: ").append(stats.getTackles().getTotal()).append("\n");
        }
        
        if (stats.getDribbles() != null) {
            sb.append("Dribbles: ").append(stats.getDribbles().getSuccess()).append("/").append(stats.getDribbles().getAttempts()).append("\n");
        }
        
        if (stats.getFouls() != null) {
            sb.append("Fouls Drawn: ").append(stats.getFouls().getDrawn()).append(" | Committed: ").append(stats.getFouls().getCommitted()).append("\n");
        }
        
        if (stats.getCards() != null) {
            sb.append("Cards - Yellow: ").append(stats.getCards().getYellow());
            if (stats.getCards().getRed() != null && stats.getCards().getRed() > 0) {
                sb.append(", Red: ").append(stats.getCards().getRed());
            }
            sb.append("\n");
        }
        
        if (stats.getPenalty() != null) {
            sb.append("Penalties Scored: ").append(stats.getPenalty().getScored()).append(" | Missed: ").append(stats.getPenalty().getMissed()).append("\n");
        }

        return sb.toString();
    }

    private void updateFavoriteButton() {
        if (currentPlayerId == -1) {
            return;
        }

        addToFavoritesButton.setEnabled(false);
        
        favoritesManager.isFavorite(currentPlayerId, isFavorite -> {
            runOnUiThread(() -> {
                addToFavoritesButton.setEnabled(true);
                
                if (isFavorite) {
                    addToFavoritesButton.setText("Remove from Favorites");
                    addToFavoritesButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                } else {
                    addToFavoritesButton.setText("Add to Favorites");
                    addToFavoritesButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                }
            });
        });

        addToFavoritesButton.setOnClickListener(v -> toggleFavorite());
    }

    private void toggleFavorite() {
        if (currentPlayer == null || currentPlayerId == -1) {
            Toast.makeText(this, "Player data not loaded", Toast.LENGTH_SHORT).show();
            return;
        }

        addToFavoritesButton.setEnabled(false);

        favoritesManager.isFavorite(currentPlayerId, isFavorite -> {
            if (isFavorite) {
                // Remove from favorites
                favoritesManager.removeFavorite(currentPlayerId, new FavoritesManager.OperationCallback() {
                    @Override
                    public void onSuccess() {
                        runOnUiThread(() -> {
                            Toast.makeText(PlayerDetailsActivity.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                            updateFavoriteButton();
                        });
                    }

                    @Override
                    public void onError(String error) {
                        runOnUiThread(() -> {
                            Toast.makeText(PlayerDetailsActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                            addToFavoritesButton.setEnabled(true);
                        });
                    }
                });
            } else {
                // Add to favorites
                favoritesManager.addFavorite(currentPlayer, new FavoritesManager.OperationCallback() {
                    @Override
                    public void onSuccess() {
                        runOnUiThread(() -> {
                            Toast.makeText(PlayerDetailsActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                            updateFavoriteButton();
                        });
                    }

                    @Override
                    public void onError(String error) {
                        runOnUiThread(() -> {
                            Toast.makeText(PlayerDetailsActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                            addToFavoritesButton.setEnabled(true);
                        });
                    }
                });
            }
        });
    }
}
