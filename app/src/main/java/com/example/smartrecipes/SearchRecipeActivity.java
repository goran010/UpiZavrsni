package com.example.smartrecipes;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class SearchRecipeActivity extends AppCompatActivity {

    private RecipeRepository recipeRepository;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);

        recipeRepository = new RecipeRepository(this);
        searchBar = findViewById(R.id.search_bar);
        listView = findViewById(R.id.list_view_search_results);

        List<String> recipeNames = recipeRepository.getAllRecipeNames();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeNames);
        listView.setAdapter(adapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SearchRecipeActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
