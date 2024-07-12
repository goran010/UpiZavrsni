package com.example.smartrecipes;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ViewRecipeActivity extends AppCompatActivity {

    private RecipeRepository recipeRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe); //Layout (activity_view_recipe)

        //Initialize the RecipeRepository
        recipeRepository = new RecipeRepository(this);

        //Retrieve recipeId from intent; default value is -1 if recipeID is not found in Intent
        int recipeId = getIntent().getIntExtra("recipeId", -1);

        //Find the recipe with the matching recipeId
        Recipe recipe = findRecipeById(recipeId);

        //If recipe found, populate the views
        if (recipe != null) {
            TextView textViewName = findViewById(R.id.text_view_name);
            TextView textViewCountry = findViewById(R.id.text_view_country);
            TextView textViewIngredients = findViewById(R.id.text_view_ingredients);
            TextView textViewInstructions = findViewById(R.id.text_view_instructions);

            textViewName.setText(recipe.getName());
            textViewCountry.setText(recipe.getCountry());
            textViewIngredients.setText(recipe.getIngredients());
            textViewInstructions.setText(recipe.getInstructions());
        }

        //Setup the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Navigate back to previous screen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Find recipe by its ID in the repository
    private Recipe findRecipeById(int recipeId) {
        for (Recipe r : recipeRepository.getAllRecipes()) { //Iterate over all recipes in the repository
            if (r.getId() == recipeId) {
                return r;
            }
        }
        return null; //Null if recipe not found
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Respond to the action bar's Up/Home button
                onBackPressed(); //Return to previous activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
