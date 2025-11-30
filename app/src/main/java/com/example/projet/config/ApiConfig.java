package com.example.projet.config;

/**
 * API Configuration - Store API keys securely
 * Add API_KEY and API_HOST to local.properties file
 */
public class ApiConfig {
    // These should be loaded from BuildConfig or environment variables
    // For now, they need to be set in local.properties
    
    public static final String RAPIDAPI_KEY = "e5fb53591552d093868ec934371f5a08"; // Replace with actual key
    public static final String RAPIDAPI_HOST = "api-football-v1.p.rapidapi.com";
    public static final int DEFAULT_SEASON = 2024;
}
