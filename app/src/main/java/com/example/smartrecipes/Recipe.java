package com.example.smartrecipes;

//Class that is used for creating Recipe objects with attributes and provides
//methods to access and modify these attributes

public class Recipe {
    private int id;
    private String name;
    private String country;
    private String ingredients;
    private String instructions;

    public Recipe(String name, String country, String ingredients, String instructions) {
        this.name = name;
        this.country = country;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
}