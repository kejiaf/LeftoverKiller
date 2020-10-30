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

import com.example.leftoverkiller.application.LeftoverKillerApplication;
import com.example.leftoverkiller.model.Ingredient;
import com.example.leftoverkiller.model.IngredientListRequest;
import com.example.leftoverkiller.model.IngredientListResponse;
import com.example.leftoverkiller.model.RecipeListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class RecipeDetailsActivity extends AppCompatActivity {

    ImageView recipeImage;
    TextView instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

    }

    private void initViews(){
        recipeImage = findViewById(R.id.image_recipe);
        instructions = findViewById(R.id.recipe_instructions);
    }
}