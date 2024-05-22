package com.example.smartrecipes;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewRecipeActivity extends AppCompatActivity {

    private RecipeRepository recipeRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.smartrecipes.R.layout.activity_view_recipe);

        recipeRepository = new RecipeRepository(this);

        int recipeId = getIntent().getIntExtra("recipeId", -1);
        Recipe recipe = null;
        for (Recipe r : recipeRepository.getAllRecipes()) {
            if (r.getId() == recipeId) {
                recipe = r;
                break;
            }
        }

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
    }
}