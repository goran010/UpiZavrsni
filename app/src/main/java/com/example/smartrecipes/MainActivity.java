package com.example.smartrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecipeRepository recipeRepository;
    private ArrayAdapter<Recipe> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeRepository = new RecipeRepository(this);
        List<Recipe> recipes = recipeRepository.getAllRecipes();

        ListView listView = findViewById(R.id.list_view_recipes);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipes);
        listView.setAdapter(adapter);

        Button buttonAddRecipe = findViewById(R.id.button_add_recipe);
        buttonAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe selectedRecipe = adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, ViewRecipeActivity.class);
                intent.putExtra("recipeId", selectedRecipe.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update the list of recipes when the activity resumes
        updateRecipeList();
    }

    private void updateRecipeList() {
        // Clear and update the adapter with the latest list of recipes
        adapter.clear();
        adapter.addAll(recipeRepository.getAllRecipes());
        adapter.notifyDataSetChanged();
    }
}