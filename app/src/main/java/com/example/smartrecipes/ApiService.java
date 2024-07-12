package com.example.smartrecipes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search.php")
    Call<ApiCallModel> getRecipes(@Query("s") String keyword);
}


