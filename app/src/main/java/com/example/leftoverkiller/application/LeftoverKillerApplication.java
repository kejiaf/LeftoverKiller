package com.example.leftoverkiller.application;

import android.app.Application;

import com.example.leftoverkiller.network.LeftoverKillerInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LeftoverKillerApplication extends Application {

    public Gson gson;
    public Retrofit retrofit;
    public LeftoverKillerInterface apiService;
    public static String BASE_URL = "http://18.222.31.30/";
    public String username;
    @Override
    public void onCreate() {
        super.onCreate();
        gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        // add your other interceptors
        // add logging as last interceptor
        httpClient.addInterceptor(logging);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        apiService =
                retrofit.create(LeftoverKillerInterface.class);


    }
}
