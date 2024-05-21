package com.example.smartrecipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {
    private RecipeDatabaseHelper dbHelper;

    public RecipeRepository(Context context) {
        dbHelper = new RecipeDatabaseHelper(context);
    }

    public void addRecipe(Recipe recipe) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", recipe.getName());
        values.put("country", recipe.getCountry());
        values.put("ingredients", recipe.getIngredients());
        values.put("instructions", recipe.getInstructions());
        db.insert("recipes", null, values);
    }

    public List<Recipe> getAllRecipes() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Recipe> recipes = new ArrayList<>();
        Cursor cursor = db.query("recipes", null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int nameIndex = cursor.getColumnIndex("name");
                int countryIndex = cursor.getColumnIndex("country");
                int ingredientsIndex = cursor.getColumnIndex("ingredients");
                int instructionsIndex = cursor.getColumnIndex("instructions");
                int idIndex = cursor.getColumnIndex("id");

                // Provjera je li stupac pronađen
                if (nameIndex != -1 && countryIndex != -1 && ingredientsIndex != -1 && instructionsIndex != -1 && idIndex != -1) {
                    Recipe recipe = new Recipe(
                            cursor.getString(nameIndex),
                            cursor.getString(countryIndex),
                            cursor.getString(ingredientsIndex),
                            cursor.getString(instructionsIndex)
                    );
                    recipe.setId(cursor.getInt(idIndex));
                    recipes.add(recipe);
                }
            }
            cursor.close();
        }
        return recipes;
    }
}