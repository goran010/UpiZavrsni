package com.example.smartrecipes;

import retrofit2.Retrofit; //Used for creating and executing HTTP requests
import retrofit2.converter.gson.GsonConverterFactory; //Used for converting JSON data using Gson

public class ApiClient {

    //API URL, used as the base for all API calls
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private static Retrofit retrofit;

    //Return an instance of the Retrofit client
    public static Retrofit getClient() {
        if (retrofit == null) { //If true, Retrofit instance hasn't been declared yet
            //Creating Retrofit instance
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
