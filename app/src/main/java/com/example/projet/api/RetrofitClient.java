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
    private static final String BASE_URL = "https://api-football-v1.p.rapidapi.com/v3/";
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
                        
                        // Add RapidAPI headers to all requests
                        okhttp3.Request request = original.newBuilder()
                                .header("x-rapidapi-key", ApiConfig.RAPIDAPI_KEY)
                                .header("x-rapidapi-host", ApiConfig.RAPIDAPI_HOST)
                                .header("User-Agent", "Android-Football-App/1.0")
                                .build();
                        
                        Log.d(TAG, "Request: " + request.url());
                        Log.d(TAG, "Headers - Key: " + request.header("x-rapidapi-key"));
                        Log.d(TAG, "Headers - Host: " + request.header("x-rapidapi-host"));
                        
                        okhttp3.Response response = chain.proceed(request);
                        Log.d(TAG, "Response Code: " + response.code());
                        return response;
                    })
                    .addInterceptor(loggingInterceptor)
                    .build();

            // Create Gson with lenient parsing
            Gson gson = new GsonBuilder()
                    .setLenient()
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
