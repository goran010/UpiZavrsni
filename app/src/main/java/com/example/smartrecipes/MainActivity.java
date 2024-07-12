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
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecipeRepository recipeRepository; //For database operations
    private ArrayAdapter<String> adapter; //For managing data in ListView
    private ListView listView; //For displaying list of the recipes
    private boolean isDataFetched = false; //If data was fetched from API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //layout (activity_main.xml)

        recipeRepository = new RecipeRepository(this); //Interact with database
        List<String> recipeNames = recipeRepository.getAllRecipeNames(); //Retrieve and store all recipe names from db


        //Initialize the ListView by finding it by its ID in the layout
        listView = findViewById(R.id.list_view_recipes);
        //Create a new ArrayAdapter to manage the list of recipe names
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, recipeNames);
        // Set the adapter for the ListView to display the recipes
        listView.setAdapter(adapter);
        //Enable multiple choice mode in the ListView
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //Call method to fetch recipes from API if not fetched already
        if (!isDataFetched) {
            fetchRecipesFromApi();
        }

        //If "+" button is clicked, create new Intent and move user to AddRecipeActivity
        ImageButton buttonAddRecipe = findViewById(R.id.button_add_recipe);
        buttonAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
                startActivity(intent);
            }
        });

        //If "delete" button is clicked, delete selected (checked) items from ListView
        ImageButton buttonDeleteRecipe = findViewById(R.id.button_delete_recipe);
        buttonDeleteRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedRecipes();
            }
        });

        //If "search" button is clicked, create new Intent and move user to SearchRecipeActivity
        ImageButton buttonSearchRecipe = findViewById(R.id.button_search_recipe);
        buttonSearchRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchRecipeActivity.class);
                startActivity(intent);
            }
        });

        //If item from ListView is clicked, retrieve the name from clicked recipe (item), check if it exists,
        //if true create new Intent and move user to ViewRecipeActivity
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
    protected void onResume() { //Called when activity is resumed
        super.onResume();
        updateRecipeList(); //Updates the ListView with the current list of recipes
    }

    //Update the ListView with the latest list of recipes
    private void updateRecipeList() {
        adapter.clear();
        adapter.addAll(recipeRepository.getAllRecipeNames());
        adapter.notifyDataSetChanged();
    }

    //Method to delete selected recipes from the ListView
    private void deleteSelectedRecipes() {
        //Retrieves the positions of checked items in the ListView
        SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
        //Gets the total number of items in the adapter
        int itemCount = adapter.getCount();

        //Iteration (reverse order)
        for (int i = itemCount - 1; i >= 0; i--) {
            if (checkedItemPositions.get(i)) { //Checks if the item at position i is checked
                String selectedRecipeName = adapter.getItem(i); //Get name of the selected recipe
                Recipe selectedRecipe = recipeRepository.getRecipeByName(selectedRecipeName); //Get Recipe object with it's name
                if (selectedRecipe != null) { //If recipe exists, delete it from db
                    recipeRepository.deleteRecipe(selectedRecipe);
                }
            }
        }

        updateRecipeList(); //update ListView
    }

    private void fetchRecipesFromApi() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class); //Create instance of ApiService using ApiClient
        Call<ApiCallModel> call = apiService.getRecipes("egg"); //Replace "egg" with your search query

        call.enqueue(new Callback<ApiCallModel>() {
            @Override
            public void onResponse(Call<ApiCallModel> call, Response<ApiCallModel> response) { //Handle API response
                if (response.isSuccessful() && response.body() != null) { //Check if response is successful and the body is not null
                    //Log the entire response object
                    Log.i("myTag", response.toString());

                    List<Meal> meals = response.body().getMeals(); //Retrieve list of meals from response
                    List<String> mealNames = new ArrayList<>(); //Create list to store meal names
                    for (Meal meal : meals) { //Iteration through list of meals
                        Log.i("myTag", "Meal: " + meal.getStrMeal()); //Logs the name of each meal

                        // Check if the meal is already in the database
                        if (recipeRepository.getRecipeByName(meal.getStrMeal()) == null) {
                            //New recipe object
                            Recipe recipe = new Recipe(meal.getStrMeal(), meal.getStrArea(), "meal.getStrIngredients()", meal.getStrInstructions());
                            //Add recipe to db
                            recipeRepository.addRecipe(recipe);
                            //Add the meal name to the list of meal names
                            mealNames.add(meal.getStrMeal());
                        }
                    }
                    // Update the ListView with the fetched recipes
                    adapter.addAll(mealNames);
                    adapter.notifyDataSetChanged();
                    isDataFetched = true;
                } else {
                    Log.e("myTag", "Failed to get response: " + response.errorBody().toString()); //Error message
                }
            }

            @Override
            public void onFailure(Call<ApiCallModel> call, Throwable t) {
                Log.e("myTag", "Error fetching data", t);
                Toast.makeText(MainActivity.this, "Error fetching data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}







