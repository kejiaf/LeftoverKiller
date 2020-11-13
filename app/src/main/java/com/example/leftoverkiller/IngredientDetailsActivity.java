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
import com.example.leftoverkiller.application.RecipeWithIngredientAdapter;
import com.example.leftoverkiller.model.Ingredient;
import com.example.leftoverkiller.model.IngredientListResponse;
import com.example.leftoverkiller.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientDetailsActivity extends AppCompatActivity {
    private int ingredientID;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView ingredientName;

    private List<Recipe> listOfRecipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_details);
        ingredientID = getIntent().getIntExtra( "INGREDIENT_ID" , -1 );
        ingredientName = this.findViewById(R.id.ingredient_detail_name);

        recyclerView = (RecyclerView) findViewById(R.id.recipes_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // Setting below to false along with using NestedScrollView forces the page to only scroll
        // with one scroll bar
        recyclerView.setNestedScrollingEnabled(false);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        callAPI();
        //buildRecyclerView();
    }

    private void buildRecyclerView() {
        // If dataset is not null, add adapter
        TextView tvEmptyWarning = this.findViewById(R.id.tv_empty_recipes_warning);

        if (listOfRecipes != null || listOfRecipes.size() != 0) {
            // Adapter for recycler view
            mAdapter = new RecipeWithIngredientAdapter(listOfRecipes);
            recyclerView.setAdapter(mAdapter);
        }

        // Hide recycler view if empty and replace with an empty dataset message
        if (listOfRecipes == null || listOfRecipes.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            tvEmptyWarning.setVisibility(View.VISIBLE);
        } else {
            Log.i("debug_me", "brv inside" );
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyWarning.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged(); // needed so the recycler view actually shows new data
        }
    }

    private void callAPI() {

        Call<Ingredient> call = ((LeftoverKillerApplication) this.getApplication()).apiService.getIngredientDetails( ingredientID );
        call.enqueue(new Callback<Ingredient>() {
            @Override
            public void onResponse(Call<Ingredient> call, Response<Ingredient> response) {
                ingredientName.setText( response.body().getName() );
                Log.i("debug_me", "list of recipe debug" );
                if (response.body().getTopRecipes() != null) {
                    listOfRecipes.addAll( response.body().getTopRecipes() ); // TODO: top recipes null?
                    Log.i("debug_me", "list of recipe debug inside " + listOfRecipes.get(0).getName() );

                    buildRecyclerView();
                    //List<String> ingredientList = new ArrayList<>();
                }
            }

            @Override
            public void onFailure(Call<Ingredient> call, Throwable t) {
                Toast.makeText(getActivity(), "Please check your network connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // created this for onFailure method in callAPI
    // onFailure would not accept "this" as the first argument
    private AppCompatActivity getActivity()
    {
        return this;
    }
}