package com.example.leftoverkiller;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leftoverkiller.application.LeftoverKillerApplication;
import com.example.leftoverkiller.application.RecipesAdapter;
import com.example.leftoverkiller.application.Utils;
import com.example.leftoverkiller.model.Ingredient;
import com.example.leftoverkiller.model.IngredientListRequest;
import com.example.leftoverkiller.model.IngredientListResponse;
import com.example.leftoverkiller.model.Recipe;
import com.example.leftoverkiller.model.RecipeListResponse;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailsActivity extends AppCompatActivity {

    ImageView recipeImage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView recipeIngredients;
    TextView recipeName;
    TextView recipeInstruction;
    FloatingActionButton addToFavList;
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

    private void initViews() {
        recipeImage = findViewById(R.id.image_recipe);
        recipeName = findViewById(R.id.recipe_name);
        recipeIngredients = findViewById(R.id.recipe_ingredients);
        recipeInstruction = findViewById(R.id.recipe_instruction);
        addToFavList = findViewById(R.id.fab_addfav);
        collapsingToolbarLayout = findViewById(R.id.collapse_toolbar);

        recipeID = getIntent().getIntExtra("recipeID", -1);

        /*
         *change button image
         */
        //if in fav list
        if (Utils.isRecipeInFavorites(recipeID, getApplicationContext()))
            addToFavList.setImageResource(R.drawable.ic_baseline_remove_24);
        else
            addToFavList.setImageResource(R.drawable.ic_baseline_add_24);

        /*
         * add/remove favorite list
         */

    }

    @Override
    public void onStart() {
        super.onStart();
        Call<Recipe> call = ((LeftoverKillerApplication) this.getApplication()).apiService.getRecipeDetails(recipeID);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, final Response<Recipe> response) {
                if (response.body() != null) {
                    recipeName.setText(response.body().getName());

                    List<Ingredient> list = response.body().getIngredients();
                    StringBuilder ingredientList = new StringBuilder();
                    for (Ingredient a : list) {
                        ingredientList.append("    ");
                        ingredientList.append(a.getName());
                        ingredientList.append(System.getProperty("line.separator"));
                    }
                    recipeIngredients.setText(ingredientList.toString());

                    String instructions = response.body().getInstructions();
                    String[] steps = instructions.split("\\.");
                    StringBuilder text = new StringBuilder();
                    for (String step : steps) {
                        step.trim();
                        text.append(step);
                        text.append(System.getProperty("line.separator"));
                        text.append(System.getProperty("line.separator"));
                    }
                    recipeInstruction.setText(text.toString());
                    //addToFavList.setVisibility(View.VISIBLE);

                    addToFavList.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Utils.isRecipeInFavorites(recipeID, getApplicationContext())) {
                                Utils.removeFromFavorites(recipeID, getApplicationContext());
                                addToFavList.setImageResource(R.drawable.ic_baseline_add_24);
                            } else {
                                Utils.addRecipe(response.body(), getApplicationContext());
                                addToFavList.setImageResource(R.drawable.ic_baseline_remove_24);
                            }
                        }
                    });


                    //Picasso.get().load(response.body().getImageUrl()).fit().centerCrop().into(recipeImage);
                    Picasso.get().load(response.body().getImageURL()).fit().centerCrop().into(recipeImage,
                            new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    collapsingToolbarLayout.setBackground(recipeImage.getDrawable());
                                }

                                @Override
                                public void onError(Exception e) {

                                }
                            });
                } else {
                    //addToFavList.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), response.body().getError() != null ? response.body().getError() : "Please check your network connection!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                //addToFavList.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Please check your network connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}