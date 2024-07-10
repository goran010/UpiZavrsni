package com.example.smartrecipes;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface ApiService {
    @GET("www.themealdb.com/api/json/v1/1/search.php?s=egg")
    Call<List<MyData>> getData();
}

