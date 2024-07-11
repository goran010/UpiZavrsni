package com.example.smartrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecipeRepository recipeRepository;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeRepository = new RecipeRepository(this);
        List<String> recipeNames = recipeRepository.getAllRecipeNames();

        listView = findViewById(R.id.list_view_recipes);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, recipeNames);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        ImageButton buttonAddRecipe = findViewById(R.id.button_add_recipe);
        buttonAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonDeleteRecipe = findViewById(R.id.button_delete_recipe);
        buttonDeleteRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedRecipes();
            }
        });

        ImageButton buttonSearchRecipe = findViewById(R.id.button_search_recipe);
        buttonSearchRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchRecipeActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedRecipeName = adapter.getItem(position);
                Recipe selectedRecipe = recipeRepository.getRecipeByName(selectedRecipeName);
                if (selectedRecipe != null) {
                    Intent intent = new Intent(MainActivity.this, ViewRecipeActivity.class);
                    intent.putExtra("recipeId", selectedRecipe.getId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRecipeList();
    }

    private void updateRecipeList() {
        adapter.clear();
        adapter.addAll(recipeRepository.getAllRecipeNames());
        adapter.notifyDataSetChanged();
    }

    private void deleteSelectedRecipes() {
        SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
        int itemCount = adapter.getCount();

        for (int i = itemCount - 1; i >= 0; i--) {
            if (checkedItemPositions.get(i)) {
                String selectedRecipeName = adapter.getItem(i);
                Recipe selectedRecipe = recipeRepository.getRecipeByName(selectedRecipeName);
                if (selectedRecipe != null) {
                    recipeRepository.deleteRecipe(selectedRecipe);
                }
            }
        }

        updateRecipeList();
    }
}
