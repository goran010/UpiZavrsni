package com.example.smartrecipes;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddRecipeActivity extends AppCompatActivity {

    private RecipeRepository recipeRepository; //Declaration of RecipeRepository to handle database operations

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Method called when the activity is first created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe); //Setting layout for the activity (activity_add_recipe.xml)

        //Setup the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Check if action bar exists, if true enable button for going back to main activity
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recipeRepository = new RecipeRepository(this); //Initialization of RecipeRepository

        //Initialize EditText fields for user input by finding them by their IDs in the layout
        final EditText editTextName = findViewById(R.id.edit_text_name);
        final EditText editTextCountry = findViewById(R.id.edit_text_country);
        final EditText editTextIngredients = findViewById(R.id.edit_text_ingredients);
        final EditText editTextInstructions = findViewById(R.id.edit_text_instructions);

        ImageButton buttonSave = findViewById(R.id.button_save); //ImageButton to save recipes using their ID
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get text from EditText, trim() method used for empty spaces at the beginning or at the end of the text
                String name = editTextName.getText().toString().trim();
                String country = editTextCountry.getText().toString().trim();
                String ingredients = editTextIngredients.getText().toString().trim();
                String instructions = editTextInstructions.getText().toString().trim();

                //Check if all fields are filled in, if false -> show message
                if (name.isEmpty() || country.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
                    Toast.makeText(AddRecipeActivity.this, "All fields must be filled in", Toast.LENGTH_SHORT).show();
                }
                //If all fields are filled in, create new Recipe object and add it to database
                else {
                    Recipe recipe = new Recipe(name, country, ingredients, instructions);
                    recipeRepository.addRecipe(recipe);
                    finish(); //End current activity, return user to previous activity
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed(); // Navigate back
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
