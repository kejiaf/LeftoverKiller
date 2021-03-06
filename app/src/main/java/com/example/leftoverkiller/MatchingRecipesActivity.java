package com.example.leftoverkiller;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
import com.example.leftoverkiller.ui.home.SearchRecipesFragment;

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

    public abstract class MyItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

        private GestureDetectorCompat mGestureDetectorCompat;
        private RecyclerView mRecyclerView;

        public MyItemClickListener(RecyclerView recyclerView) {
            this.mRecyclerView = recyclerView;
            mGestureDetectorCompat =
                    new GestureDetectorCompat(mRecyclerView.getContext(), new MatchingRecipesActivity.MyItemClickListener.MyGestureListener());
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetectorCompat.onTouchEvent(e);
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetectorCompat.onTouchEvent(e);
            return false;
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }

        public abstract void onItemClick(RecyclerView.ViewHolder vh);

        private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null) {
                    RecyclerView.ViewHolder viewHolder =
                            mRecyclerView.getChildViewHolder(childView);
                    onItemClick(viewHolder);
                }
                return true;
            }
        }
    }

    private void callAPI() {
        final IngredientListRequest request = new IngredientListRequest(ingredientList);
        Call<RecipeListResponse> call = ((LeftoverKillerApplication) getApplication()).apiService.getMatchingRecipes(request);
        call.enqueue(new Callback<RecipeListResponse>() {
            @Override
            public void onResponse(Call<RecipeListResponse> call, Response<RecipeListResponse> response) {
                if (response.body().getRecipes() != null && response.body().getRecipes().size() != 0) {
                    recipeDataset = response.body().getRecipes();
                    // If dataset is not null, add adapter
                    Log.i("ingredientlist", "ingredient data not null with size " + recipeDataset.size());
                    // Adapter for recycler view
                    mAdapter = new MatchingRecipeAdapter(response.body().getRecipes());
                    recyclerView.setAdapter(mAdapter);

                    //click on recipe to see details
                    recyclerView.addOnItemTouchListener(new MatchingRecipesActivity.MyItemClickListener(recyclerView) {
                        @Override
                        public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                            int position = viewHolder.getAdapterPosition();
                            int recipeID = mAdapter.RecipeID(position);
                            Intent intent = new Intent(getApplicationContext(), RecipeDetailsActivity.class);
                            intent.putExtra("recipeID", recipeID);
                            startActivity(intent);
                        }
                    });

                    // If dataset is not null, add adapter
                    buildRecyclerView(recipeDataset);
                } else {
                    if (response.body().getError() != null)
                        label.setText(response.body().getError());
                    else
                        label.setText("Matching Recipes Found: " + "0");
                }
            }

            @Override
            public void onFailure(Call<RecipeListResponse> call, Throwable t) {
                Toast.makeText(MatchingRecipesActivity.this, "Connection error, please check your network", Toast.LENGTH_SHORT).show();
            }
        });
    }
}