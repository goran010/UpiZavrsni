package com.example.smartrecipes;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiCallModel {
    @SerializedName("meals")
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}

