package com.example.projet.api;

import android.util.Log;

import com.example.projet.config.ApiConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://v3.football.api-sports.io/";
    private static final String TAG = "RetrofitClient";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> 
                Log.d(TAG, message)
            );
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        okhttp3.Request original = chain.request();
                        
                        // Add API-Football dashboard headers to all requests
                        okhttp3.Request request = original.newBuilder()
                                .header("x-apisports-key", ApiConfig.API_KEY)
                                .header("User-Agent", "Android-Football-App/1.0")
                                .build();
                        
                        Log.d(TAG, "Request: " + request.url());
                        Log.d(TAG, "Headers - API Key: " + (ApiConfig.API_KEY != null ? "***" + ApiConfig.API_KEY.substring(Math.max(0, ApiConfig.API_KEY.length() - 4)) : "null"));
                        
                        okhttp3.Response response = chain.proceed(request);
                        Log.d(TAG, "Response Code: " + response.code());
                        return response;
                    })
                    .addInterceptor(loggingInterceptor)
                    .build();

            // Create Gson with lenient parsing and null handling
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .serializeNulls()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static FootballAPI getFootballAPI() {
        return getRetrofit().create(FootballAPI.class);
    }
}
