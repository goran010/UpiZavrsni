package com.example.smartrecipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {
    private RecipeDatabaseHelper dbHelper; //Management of the database creation and version management

    public RecipeRepository(Context context) {
        dbHelper = new RecipeDatabaseHelper(context);
    }

    public void addRecipe(Recipe recipe) { //Method to add a new recipe to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //Gets a writable database instance
        ContentValues values = new ContentValues();
        values.put("name", recipe.getName());
        values.put("country", recipe.getCountry());
        values.put("ingredients", recipe.getIngredients());
        values.put("instructions", recipe.getInstructions());
        db.insert("recipes", null, values); //Inserts the values
    }

    public List<Recipe> getAllRecipes() { //Method to retrieve all recipes from the database
        SQLiteDatabase db = dbHelper.getReadableDatabase(); //Insert the values
        List<Recipe> recipes = new ArrayList<>(); //New list to hold the recipes
        Cursor cursor = db.query("recipes", null, null, null, null, null, null);

        if (cursor != null) { //if true, iterate over the rows
            while (cursor.moveToNext()) {
                int nameIndex = cursor.getColumnIndex("name");
                int countryIndex = cursor.getColumnIndex("country");
                int ingredientsIndex = cursor.getColumnIndex("ingredients");
                int instructionsIndex = cursor.getColumnIndex("instructions");
                int idIndex = cursor.getColumnIndex("id");

                //Ensure that all necessary columns are present in the cursor
                if (nameIndex != -1 && countryIndex != -1 && ingredientsIndex != -1 && instructionsIndex != -1 && idIndex != -1) {

                    //Create new Recipe object using the values retrieved from the cursor
                    Recipe recipe = new Recipe(
                            cursor.getString(nameIndex),
                            cursor.getString(countryIndex),
                            cursor.getString(ingredientsIndex),
                            cursor.getString(instructionsIndex)
                    );
                    //Retrieves the value of the id column and sets it as the ID of the Recipe object
                    recipe.setId(cursor.getInt(idIndex));
                    recipes.add(recipe); //Add to recipes list
                }
            }
            cursor.close();
        }
        return recipes;
    }

    public List<String> getAllRecipeNames() {
        List<Recipe> recipes = getAllRecipes();
        List<String> recipeNames = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipeNames.add(recipe.getName());
        }
        return recipeNames;
    }

    public Recipe getRecipeByName(String name) { //Method to retrieve a recipe by its name from the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] selectionArgs = {name}; //Create array with the name as the selection argument
        Cursor cursor = db.query("recipes", null, "name = ?", selectionArgs, null, null, null);

        Recipe recipe = null;

        if (cursor != null) { //If true, get the column indexes for all columns
            if (cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex("name");
                int countryIndex = cursor.getColumnIndex("country");
                int ingredientsIndex = cursor.getColumnIndex("ingredients");
                int instructionsIndex = cursor.getColumnIndex("instructions");
                int idIndex = cursor.getColumnIndex("id");

                if (nameIndex != -1 && countryIndex != -1 && ingredientsIndex != -1 && instructionsIndex != -1 && idIndex != -1) {
                    recipe = new Recipe(
                            cursor.getString(nameIndex),
                            cursor.getString(countryIndex),
                            cursor.getString(ingredientsIndex),
                            cursor.getString(instructionsIndex)
                    );
                    recipe.setId(cursor.getInt(idIndex));
                }
            }
            cursor.close();
        }

        return recipe;
    }

    //Delete a recipe from the db
    public void deleteRecipe(Recipe recipe) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] selectionArgs = {String.valueOf(recipe.getId())}; //Create an array with the recipe ID as the selection argument
        //Delete row from the table where the ID matches the specified ID
        db.delete("recipes", "id = ?", selectionArgs);
    }
}