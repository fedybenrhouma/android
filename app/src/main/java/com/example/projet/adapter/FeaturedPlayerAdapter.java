package com.example.projet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projet.R;
import com.example.projet.models.Player;
import com.example.projet.models.Statistics;

import java.util.List;

public class FeaturedPlayerAdapter extends RecyclerView.Adapter<FeaturedPlayerAdapter.ViewHolder> {
    
    private List<Player> players;
    private OnPlayerClickListener listener;

    public interface OnPlayerClickListener {
        void onPlayerClick(Player player);
    }

    public FeaturedPlayerAdapter(List<Player> players, OnPlayerClickListener listener) {
        this.players = players;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_player, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Player player = players.get(position);
        holder.bind(player, listener);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void updatePlayers(List<Player> newPlayers) {
        this.players = newPlayers;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView playerName;
        private TextView playerTeam;
        private TextView playerPosition;
        private ImageView playerPhoto;
        private ImageView teamLogo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.playerName);
            playerTeam = itemView.findViewById(R.id.playerTeam);
            playerPosition = itemView.findViewById(R.id.playerPosition);
            playerPhoto = itemView.findViewById(R.id.playerPhoto);
            teamLogo = itemView.findViewById(R.id.teamLogo);
        }

        public void bind(Player player, OnPlayerClickListener listener) {
            if (player == null || player.getPlayerInfo() == null) return;

            // Set player name
            playerName.setText(player.getPlayerInfo().getName());

            // Load player photo
            if (player.getPlayerInfo().getPhoto() != null && !player.getPlayerInfo().getPhoto().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(player.getPlayerInfo().getPhoto())
                        .placeholder(R.drawable.ic_placeholder)
                        .into(playerPhoto);
            }

            // Get statistics if available
            if (player.getStatistics() != null && !player.getStatistics().isEmpty()) {
                Statistics stats = player.getStatistics().get(0);
                
                if (stats.getTeam() != null) {
                    playerTeam.setText("Team: " + stats.getTeam().getName());
                    
                    // Load team logo
                    if (stats.getTeam().getLogo() != null && !stats.getTeam().getLogo().isEmpty()) {
                        Glide.with(itemView.getContext())
                                .load(stats.getTeam().getLogo())
                                .placeholder(R.drawable.ic_placeholder)
                                .into(teamLogo);
                    }
                }

                if (stats.getGames() != null && stats.getGames().getPosition() != null) {
                    playerPosition.setText("Position: " + stats.getGames().getPosition());
                } else {
                    playerPosition.setText("Position: N/A");
                }
            } else {
                playerTeam.setText("Team: N/A");
                playerPosition.setText("Position: N/A");
            }

            // Set click listener
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPlayerClick(player);
                }
            });
        }
    }
}
