package com.example.projet.models;

import com.google.gson.annotations.SerializedName;

public class Dribbles {
    @SerializedName("attempts")
    private Integer attempts;
    
    @SerializedName("success")
    private Integer success;

    public Dribbles() {}

    public Integer getAttempts() { return attempts; }
    public void setAttempts(Integer attempts) { this.attempts = attempts; }

    public Integer getSuccess() { return success; }
    public void setSuccess(Integer success) { this.success = success; }
}
