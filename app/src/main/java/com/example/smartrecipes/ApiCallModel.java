package com.example.smartrecipes;
import com.google.gson.annotations.SerializedName; //SerializedName annotation from Gson library used for
                                                  // working with JSON data in Java applications.

import java.util.List;

public class ApiCallModel {
    @SerializedName("meals") //Enables automatic insertion of values inside meals field
    private List<Meal> meals; //Declaration of meals field

    public List<Meal> getMeals() {
        return meals;
    } //Return value of meals

    public void setMeals(List<Meal> meals) {
        this.meals = meals; //Set value of meals
    }
}

