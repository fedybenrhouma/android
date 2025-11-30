package com.example.projet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projet.R;
import com.example.projet.PlayerDetailsActivity;
import com.example.projet.models.Player;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    private List<Player> playerList;
    private Context context;

    public PlayerAdapter(List<Player> playerList, Context context) {
        this.playerList = playerList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_player, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = playerList.get(position);
        holder.bind(player);
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView playerName;
        private TextView playerTeam;
        private TextView playerPosition;
        private ImageView playerPhoto;
        private ImageView teamLogo;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.playerName);
            playerTeam = itemView.findViewById(R.id.playerTeam);
            playerPosition = itemView.findViewById(R.id.playerPosition);
            playerPhoto = itemView.findViewById(R.id.playerPhoto);
            teamLogo = itemView.findViewById(R.id.teamLogo);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Player player = playerList.get(position);
                    Intent intent = new Intent(context, PlayerDetailsActivity.class);
                    intent.putExtra("player_id", player.getPlayerInfo().getId());
                    intent.putExtra("player_name", player.getPlayerInfo().getName());
                    context.startActivity(intent);
                }
            });
        }

        public void bind(Player player) {
            if (player.getPlayerInfo() != null) {
                playerName.setText(player.getPlayerInfo().getName());

                if (player.getStatistics() != null && !player.getStatistics().isEmpty()) {
                    String teamName = player.getStatistics().get(0).getTeam().getName();
                    String position = player.getStatistics().get(0).getGames().getPosition();

                    playerTeam.setText("Team: " + teamName);
                    playerPosition.setText("Position: " + (position != null ? position : "N/A"));

                    // Load team logo
                    String teamLogoUrl = player.getStatistics().get(0).getTeam().getLogo();
                    if (teamLogoUrl != null && !teamLogoUrl.isEmpty()) {
                        Glide.with(context)
                                .load(teamLogoUrl)
                                .placeholder(R.drawable.ic_placeholder)
                                .into(teamLogo);
                    }
                }

                // Load player photo
                if (player.getPlayerInfo().getPhoto() != null && !player.getPlayerInfo().getPhoto().isEmpty()) {
                    Glide.with(context)
                            .load(player.getPlayerInfo().getPhoto())
                            .placeholder(R.drawable.ic_placeholder)
                            .into(playerPhoto);
                }
            }
        }
    }
}
