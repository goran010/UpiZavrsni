package com.example.smartrecipes;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

public class AddRecipeActivity extends AppCompatActivity {

    private RecipeRepository recipeRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        recipeRepository = new RecipeRepository(this);

        final EditText editTextName = findViewById(R.id.edit_text_name);
        final EditText editTextCountry = findViewById(R.id.edit_text_country);
        final EditText editTextIngredients = findViewById(R.id.edit_text_ingredients);
        final EditText editTextInstructions = findViewById(R.id.edit_text_instructions);

        ImageButton buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String country = editTextCountry.getText().toString().trim();
                String ingredients = editTextIngredients.getText().toString().trim();
                String instructions = editTextInstructions.getText().toString().trim();

                if (name.isEmpty() || country.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
                    Toast.makeText(AddRecipeActivity.this, "All fields must be filled in", Toast.LENGTH_SHORT).show();
                } else {
                    Recipe recipe = new Recipe(name, country, ingredients, instructions);
                    recipeRepository.addRecipe(recipe);
                    finish();
                }
            }
        });
    }
}
