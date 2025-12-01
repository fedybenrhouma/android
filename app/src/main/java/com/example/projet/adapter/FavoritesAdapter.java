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

import java.util.List;
import java.util.Map;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private final List<Map<String, Object>> favorites;
    private final OnFavoriteClickListener listener;

    public interface OnFavoriteClickListener {
        void onFavoriteClick(Map<String, Object> favorite);
    }

    public FavoritesAdapter(List<Map<String, Object>> favorites, OnFavoriteClickListener listener) {
        this.favorites = favorites;
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
        Map<String, Object> favorite = favorites.get(position);
        
        String playerName = (String) favorite.get("playerName");
        String playerPhoto = (String) favorite.get("playerPhoto");

        holder.playerName.setText(playerName != null ? playerName : "Unknown Player");
        holder.playerTeam.setText("Tap to view details");

        if (playerPhoto != null && !playerPhoto.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(playerPhoto)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(holder.playerPhoto);
        } else {
            holder.playerPhoto.setImageResource(R.drawable.ic_placeholder);
        }

        holder.itemView.setOnClickListener(v -> listener.onFavoriteClick(favorite));
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView playerPhoto;
        TextView playerName;
        TextView playerTeam;

        ViewHolder(View itemView) {
            super(itemView);
            playerPhoto = itemView.findViewById(R.id.playerPhoto);
            playerName = itemView.findViewById(R.id.playerName);
            playerTeam = itemView.findViewById(R.id.playerTeam);
        }
    }
}
