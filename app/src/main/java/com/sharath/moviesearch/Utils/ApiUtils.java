package com.sharath.moviesearch.Utils;


public class ApiUtils {

    public static final String BASE_URL = "http://www.omdbapi.com/"; // want to change to client server

    public static APIService apiService(){

        return RetrofitClient.retrofit_client(BASE_URL).create(APIService.class);
    }
}