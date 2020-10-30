package com.example.leftoverkiller;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.leftoverkiller.application.IngredientsAdapter;
import com.example.leftoverkiller.application.MatchingRecipeAdapter;
import com.example.leftoverkiller.application.RecipesAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class IngredientSearchActivity extends AppCompatActivity
{
    // Recycler view stuff
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> recipeDataset = new ArrayList<>(Arrays.asList("ingredient 1", "ingredient 2", "ingredient 3", "ingredient 4", "ingredient 5")); // TODO: placeholder!
    //private ArrayList<String> ingredientDataset = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_recipe);

        // Get recycler view
        recyclerView = (RecyclerView) findViewById(R.id.matching_recipes_recycler_view);

        // Setting below to false along with using NestedScrollView forces the page to only scroll
        // with one scroll bar
        recyclerView.setNestedScrollingEnabled(false);

        // Set recycler layout manager
        layoutManager = new LinearLayoutManager( this );
        recyclerView.setLayoutManager(layoutManager);

        // If dataset is not null, add adapter
        buildRecyclerView(recipeDataset);

    }

    private void buildRecyclerView(ArrayList<String> recipeDataset) {
        // If dataset is not null, add adapter
        if (recipeDataset != null) {
            Log.i("ingredientlist", "ingredient data not null with size " + recipeDataset.size());
            // Adapter for recycler view
            mAdapter = new MatchingRecipeAdapter(recipeDataset);
            recyclerView.setAdapter(mAdapter);
        }

        // Hide recycler view if empty and replace with an empty dataset message
        if (recipeDataset == null || recipeDataset.size() == 0) {
            TextView tvEmptyWarning = findViewById(R.id.tv_empty_view_warning_matching_recipes);
            recyclerView.setVisibility(View.GONE);
            tvEmptyWarning.setVisibility(View.VISIBLE);
        } else {
            TextView tvEmptyWarning = findViewById(R.id.tv_empty_view_warning_matching_recipes);
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyWarning.setVisibility(View.GONE);
        }
    }
}