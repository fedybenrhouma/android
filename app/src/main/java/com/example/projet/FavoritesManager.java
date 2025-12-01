package com.example.projet;

import  android.util.Log;

import com.example.projet.models.Player;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoritesManager {
    private static final String TAG = "FavoritesManager";
    private static final String COLLECTION_FAVORITES = "favorites";
    
    private final FirebaseFirestore db;
    private final FirebaseAuth auth;

    public FavoritesManager() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public interface FavoritesCallback {
        void onSuccess(List<Player> favorites);
        void onError(String error);
    }

    public interface OperationCallback {
        void onSuccess();
        void onError(String error);
    }

    private String getUserId() {
        FirebaseUser user = auth.getCurrentUser();
        return user != null ? user.getUid() : null;
    }

    public void addFavorite(Player player, OperationCallback callback) {
        String userId = getUserId();
        if (userId == null) {
            callback.onError("User not logged in");
            return;
        }

        if (player.getPlayerInfo() == null) {
            callback.onError("Invalid player data");
            return;
        }

        int playerId = player.getPlayerInfo().getId();
        
        // Create document with player ID as document ID
        String docId = userId + "_" + playerId;
        
        Map<String, Object> favoriteData = new HashMap<>();
        favoriteData.put("userId", userId);
        favoriteData.put("playerId", playerId);
        favoriteData.put("playerName", player.getPlayerInfo().getName());
        favoriteData.put("playerPhoto", player.getPlayerInfo().getPhoto());
        favoriteData.put("timestamp", System.currentTimeMillis());

        db.collection(COLLECTION_FAVORITES)
                .document(docId)
                .set(favoriteData)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Favorite added successfully");
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding favorite", e);
                    callback.onError(e.getMessage());
                });
    }

    public void removeFavorite(int playerId, OperationCallback callback) {
        String userId = getUserId();
        if (userId == null) {
            callback.onError("User not logged in");
            return;
        }

        String docId = userId + "_" + playerId;

        db.collection(COLLECTION_FAVORITES)
                .document(docId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Favorite removed successfully");
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error removing favorite", e);
                    callback.onError(e.getMessage());
                });
    }

    public void isFavorite(int playerId, IsFavoriteCallback callback) {
        String userId = getUserId();
        if (userId == null) {
            callback.onResult(false);
            return;
        }

        String docId = userId + "_" + playerId;

        db.collection(COLLECTION_FAVORITES)
                .document(docId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    callback.onResult(documentSnapshot.exists());
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error checking favorite", e);
                    callback.onResult(false);
                });
    }

    public void getFavorites(FavoritesCallback callback) {
        String userId = getUserId();
        if (userId == null) {
            callback.onError("User not logged in");
            return;
        }

        db.collection(COLLECTION_FAVORITES)
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Integer> playerIds = new ArrayList<>();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Long playerId = doc.getLong("playerId");
                        if (playerId != null) {
                            playerIds.add(playerId.intValue());
                        }
                    }
                    
                    if (playerIds.isEmpty()) {
                        callback.onSuccess(new ArrayList<>());
                    } else {
                        // For now, return empty list and user needs to click to see details
                        // In a real app, you'd fetch full player data from API for each ID
                        callback.onSuccess(new ArrayList<>());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting favorites", e);
                    callback.onError(e.getMessage());
                });
    }

    public void getFavoriteIds(FavoriteIdsCallback callback) {
        String userId = getUserId();
        if (userId == null) {
            callback.onSuccess(new ArrayList<>());
            return;
        }

        db.collection(COLLECTION_FAVORITES)
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Map<String, Object>> favorites = new ArrayList<>();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Map<String, Object> favorite = new HashMap<>();
                        favorite.put("playerId", doc.getLong("playerId"));
                        favorite.put("playerName", doc.getString("playerName"));
                        favorite.put("playerPhoto", doc.getString("playerPhoto"));
                        favorites.add(favorite);
                    }
                    callback.onSuccess(favorites);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting favorite IDs", e);
                    callback.onSuccess(new ArrayList<>());
                });
    }

    public interface IsFavoriteCallback {
        void onResult(boolean isFavorite);
    }

    public interface FavoriteIdsCallback {
        void onSuccess(List<Map<String, Object>> favorites);
    }
}
