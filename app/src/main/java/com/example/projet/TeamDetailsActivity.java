package com.example.projet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.projet.api.FootballAPI;
import com.example.projet.api.RetrofitClient;
import com.example.projet.models.TeamDetail;
import com.example.projet.models.TeamInfo;
import com.example.projet.models.TeamSearchResponse;
import com.example.projet.models.Venue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamDetailsActivity extends AppCompatActivity {

    private static final String TAG = "TeamDetailsActivity";
    private ImageView teamLogo;
    private TextView teamName;
    private TextView teamCountry;
    private TextView teamFounded;
    private TextView teamVenue;
    private TextView teamDescription;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        // Initialize views
        teamLogo = findViewById(R.id.teamLogo);
        teamName = findViewById(R.id.teamName);
        teamCountry = findViewById(R.id.teamCountry);
        teamFounded = findViewById(R.id.teamFounded);
        teamVenue = findViewById(R.id.teamVenue);
        teamDescription = findViewById(R.id.teamDescription);
        progressBar = findViewById(R.id.progressBar);

        // Get team data from intent
        int teamId = getIntent().getIntExtra("team_id", -1);
        String teamNameStr = getIntent().getStringExtra("team_name");
        String teamLogoUrl = getIntent().getStringExtra("team_logo");

        if (teamId != -1) {
            // Display basic info first
            teamName.setText(teamNameStr != null ? teamNameStr : "Team Details");
            
            if (teamLogoUrl != null && !teamLogoUrl.isEmpty()) {
                Glide.with(this)
                        .load(teamLogoUrl)
                        .placeholder(R.drawable.ic_placeholder)
                        .into(teamLogo);
            }
            
            // Fetch full team details from API
            loadTeamDetails(teamId);
        } else {
            Toast.makeText(this, "Invalid team", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadTeamDetails(int teamId) {
        progressBar.setVisibility(View.VISIBLE);
        teamDescription.setText("Loading team information...");

        FootballAPI api = RetrofitClient.getFootballAPI();
        Call<TeamSearchResponse> call = api.getTeamById(teamId);

        call.enqueue(new Callback<TeamSearchResponse>() {
            @Override
            public void onResponse(Call<TeamSearchResponse> call, Response<TeamSearchResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    TeamSearchResponse body = response.body();
                    
                    // Check for API errors
                    if (body.getErrors() != null && !body.getErrors().isEmpty()) {
                        StringBuilder errorMsg = new StringBuilder();
                        for (String key : body.getErrors().keySet()) {
                            errorMsg.append(key).append(": ").append(body.getErrors().get(key)).append("\n");
                        }
                        Log.e(TAG, "API Errors: " + errorMsg);
                        displayFallbackInfo(teamId);
                        return;
                    }
                    
                    if (body.getResponse() != null && !body.getResponse().isEmpty()) {
                        TeamDetail teamDetail = body.getResponse().get(0);
                        displayTeamDetails(teamDetail);
                    } else {
                        displayFallbackInfo(teamId);
                    }
                } else {
                    Log.e(TAG, "Error: " + response.code());
                    displayFallbackInfo(teamId);
                }
            }

            @Override
            public void onFailure(Call<TeamSearchResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "API call failed", t);
                displayFallbackInfo(teamId);
            }
        });
    }

    private void displayTeamDetails(TeamDetail teamDetail) {
        TeamInfo team = teamDetail.getTeam();
        Venue venue = teamDetail.getVenue();

        if (team != null) {
            // Update team name
            teamName.setText(team.getName());

            // Load team logo
            if (team.getLogo() != null && !team.getLogo().isEmpty()) {
                Glide.with(this)
                        .load(team.getLogo())
                        .placeholder(R.drawable.ic_placeholder)
                        .into(teamLogo);
            }

            // Display team information
            teamCountry.setText("Country: " + (team.getCountry() != null ? team.getCountry() : "N/A"));
            teamFounded.setText("Founded: " + (team.getFounded() != null ? team.getFounded() : "N/A"));

            // Display venue information
            if (venue != null) {
                StringBuilder venueInfo = new StringBuilder();
                if (venue.getName() != null) {
                    venueInfo.append(venue.getName());
                }
                if (venue.getCity() != null) {
                    venueInfo.append(" (").append(venue.getCity()).append(")");
                }
                if (venue.getCapacity() != null) {
                    venueInfo.append("\nCapacity: ").append(String.format("%,d", venue.getCapacity()));
                }
                if (venue.getSurface() != null) {
                    venueInfo.append("\nSurface: ").append(venue.getSurface());
                }
                teamVenue.setText("Stadium: " + (venueInfo.length() > 0 ? venueInfo.toString() : "N/A"));
            } else {
                teamVenue.setText("Stadium: N/A");
            }

            // Build description
            StringBuilder description = new StringBuilder();
            description.append(team.getName());
            
            if (team.getCountry() != null) {
                description.append(" is a ").append(team.isNational() ? "national" : "club").append(" team from ").append(team.getCountry());
            }
            
            if (team.getFounded() != null) {
                description.append(", founded in ").append(team.getFounded());
            }
            
            description.append(".\n\n");
            
            if (venue != null && venue.getName() != null) {
                description.append("They play their home matches at ").append(venue.getName());
                if (venue.getCapacity() != null) {
                    description.append(", which has a capacity of ").append(String.format("%,d", venue.getCapacity())).append(" spectators");
                }
                description.append(".");
            }

            teamDescription.setText(description.toString());
        }
    }

    private void displayFallbackInfo(int teamId) {
        String name = teamName.getText().toString();
        teamDescription.setText("Team information for " + name + " (ID: " + teamId + ")\n\n" +
                "Unable to load detailed information from the API.\n\n" +
                "This could be due to:\n" +
                "• API rate limits\n" +
                "• Season restrictions on free tier\n" +
                "• Network connectivity issues\n\n" +
                "The team details endpoint requires additional API access.");
    }
}
