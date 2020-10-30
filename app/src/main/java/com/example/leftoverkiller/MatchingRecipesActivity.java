package com.example.leftoverkiller;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leftoverkiller.application.IngredientsAdapter;
import com.example.leftoverkiller.application.LeftoverKillerApplication;
import com.example.leftoverkiller.application.MatchingRecipeAdapter;
import com.example.leftoverkiller.application.RecipesAdapter;
import com.example.leftoverkiller.model.Ingredient;
import com.example.leftoverkiller.model.IngredientListRequest;
import com.example.leftoverkiller.model.IngredientListResponse;
import com.example.leftoverkiller.model.Recipe;
import com.example.leftoverkiller.model.RecipeListResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchingRecipesActivity extends AppCompatActivity {
    // Recycler view stuff
    private RecyclerView recyclerView;
    private TextView label;
    private MatchingRecipeAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Recipe> recipeDataset;
    ArrayList<String> ingredientList;
    //private ArrayList<String> ingredientDataset = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_recipe);

        ingredientList = (ArrayList<String>) getIntent().getSerializableExtra("ingredient_list");
        setupRecyclerView();
        callAPI();
    }

    private void setupRecyclerView() {

        // Get recycler view
        recyclerView = (RecyclerView) findViewById(R.id.matching_recipes_recycler_view);
        label = findViewById(R.id.matching_recipes_label);
        // Setting below to false along with using NestedScrollView forces the page to only scroll
        // with one scroll bar
        recyclerView.setNestedScrollingEnabled(false);

        // Set recycler layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void buildRecyclerView(List<Recipe> recipeDataset) {

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

    private void callAPI() {
        IngredientListRequest request = new IngredientListRequest(ingredientList);
        Call<RecipeListResponse> call = ((LeftoverKillerApplication) getApplication()).apiService.getMatchingRecipes(request);
        call.enqueue(new Callback<RecipeListResponse>() {
            @Override
            public void onResponse(Call<RecipeListResponse> call, Response<RecipeListResponse> response) {
                if (response.body().getRecipes() != null) {

                    recipeDataset = response.body().getRecipes();
                    // If dataset is not null, add adapter
                    Log.i("ingredientlist", "ingredient data not null with size " + recipeDataset.size());
                    // Adapter for recycler view
                    mAdapter = new MatchingRecipeAdapter(response.body().getRecipes());
                    recyclerView.setAdapter(mAdapter);
                    // If dataset is not null, add adapter
                    buildRecyclerView(recipeDataset);
                    label.setText("Matching Recipes Found: " + recipeDataset.size());

                }
            }

            @Override
            public void onFailure(Call<RecipeListResponse> call, Throwable t) {
                Toast.makeText(MatchingRecipesActivity.this, "Please check your network connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}