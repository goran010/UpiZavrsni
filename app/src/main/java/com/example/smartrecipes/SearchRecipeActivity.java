package com.example.smartrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher; //Used to listen for changes to the text in an EditText
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.List;

public class SearchRecipeActivity extends AppCompatActivity {

    private RecipeRepository recipeRepository;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe); //Layout (activity_search_recipe.xml)

        //Setup the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Allows the user to navigate back to the previous screen
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recipeRepository = new RecipeRepository(this);
        searchBar = findViewById(R.id.search_bar);
        listView = findViewById(R.id.list_view_search_results);

        //Retrieve the list of all recipe names from the db
        List<String> recipeNames = recipeRepository.getAllRecipeNames();
        //Create new ArrayAdapter with a simple list item layout and the list of recipe names
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeNames);
        listView.setAdapter(adapter); //Set the adapter for the ListView

        //Add a TextWatcher to listen for changes to the text in the search bar
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            //Empty implementation for before text changed
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            //Filters the adapter's data based on the search query
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SearchRecipeActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            //Empty implementation for after text changed
            public void afterTextChanged(Editable s) {}
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedRecipeName = adapter.getItem(position); //Retrieve the name of the clicked recipe
                Recipe selectedRecipe = recipeRepository.getRecipeByName(selectedRecipeName);

                if (selectedRecipe != null) { //If recipe exists
                    //Creates an Intent to move the user to ViewRecipeActivity
                    Intent intent = new Intent(SearchRecipeActivity.this, ViewRecipeActivity.class);
                    intent.putExtra("recipeId", selectedRecipe.getId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Respond to the action bar's Up/Home button
                onBackPressed(); //Navigate back
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
