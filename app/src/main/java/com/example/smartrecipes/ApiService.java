package com.example.smartrecipes;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search.php") //GET request to the search.php

    // Return Call object with ApiCallModel as the response type, which represents the HTTP request and its response
    Call<ApiCallModel> getRecipes(@Query("s") String keyword);
}


