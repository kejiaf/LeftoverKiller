package com.example.leftoverkiller;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leftoverkiller.application.LeftoverKillerApplication;
import com.example.leftoverkiller.application.RecipesAdapter;
import com.example.leftoverkiller.model.Ingredient;
import com.example.leftoverkiller.model.IngredientListRequest;
import com.example.leftoverkiller.model.IngredientListResponse;
import com.example.leftoverkiller.model.Recipe;
import com.example.leftoverkiller.model.RecipeListResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailsActivity extends AppCompatActivity {

    ImageView recipeImage;
    TextView recipeName;
    TextView recipeInstruction;
    int recipeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        //toolBarLayout.setTitle(getTitle());
        initViews();
    }

    private void initViews(){
        recipeImage = findViewById(R.id.image_recipe);
        recipeName = findViewById(R.id.recipe_name);
        recipeInstruction = findViewById(R.id.recipe_instruction);

        recipeID = getIntent().getIntExtra("recipeID", -1);
        //recipeName.setText("Sample Recipe" + recipeID);
    }
    @Override
    public void onStart() {
        super.onStart();
        Call<Recipe> call = ((LeftoverKillerApplication) this.getApplication()).apiService.getRecipeDetails(recipeID);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.body() != null) {
                    recipeName.setText(response.body().getName());
                    recipeInstruction.setText(response.body().getInstructions());
                    Picasso.get().load(response.body().getImageUrl()).fit().centerCrop().into(recipeImage);
                } else {
                    Toast.makeText(getApplicationContext(), "Please check your network connection!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Please check your network connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}