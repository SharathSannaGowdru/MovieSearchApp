package com.sharath.moviesearch.Utils;

import com.google.gson.JsonElement;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface APIService {

    @GET("/")
    Call<JsonElement> movielist(@QueryMap Map<String, String> s, @QueryMap Map<String, String> apikey);

    @GET("/")
    Call<JsonElement> movie(@QueryMap Map<String, String> i, @QueryMap Map<String, String> apikey);

}

