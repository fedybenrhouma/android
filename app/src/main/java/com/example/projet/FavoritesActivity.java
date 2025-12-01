package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet.adapter.FavoritesAdapter;
import com.example.projet.adapter.PlayerAdapter;
import com.example.projet.models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView favoritesRecyclerView;
    private FavoritesManager favoritesManager;
    private TextView emptyView;
    private ProgressBar progressBar;
    private List<Map<String, Object>> favoritesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView);
        emptyView = findViewById(R.id.emptyView);
        progressBar = findViewById(R.id.progressBar);

        favoritesManager = new FavoritesManager();

        loadFavorites();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void loadFavorites() {
        progressBar.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        favoritesRecyclerView.setVisibility(View.GONE);

        favoritesManager.getFavoriteIds(favorites -> {
            progressBar.setVisibility(View.GONE);
            
            if (favorites.isEmpty()) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                favoritesList = favorites;
                displayFavorites();
            }
        });
    }

    private void displayFavorites() {
        favoritesRecyclerView.setVisibility(View.VISIBLE);
        
        FavoritesAdapter adapter = new FavoritesAdapter(favoritesList, favorite -> {
            // Navigate to player details
            Long playerId = (Long) favorite.get("playerId");
            String playerName = (String) favorite.get("playerName");
            
            if (playerId != null) {
                Intent intent = new Intent(FavoritesActivity.this, PlayerDetailsActivity.class);
                intent.putExtra("player_id", playerId.intValue());
                intent.putExtra("player_name", playerName);
                startActivity(intent);
            }
        });
        
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        favoritesRecyclerView.setAdapter(adapter);
    }
}
