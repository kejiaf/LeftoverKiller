package com.example.leftoverkiller.ui.home;

import android.app.Application;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.example.leftoverkiller.R;
import com.example.leftoverkiller.application.LeftoverKillerApplication;
import com.example.leftoverkiller.application.RecipesAdapter;
import com.example.leftoverkiller.model.Recipe;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import com.example.leftoverkiller.model.Recipe;
import com.example.leftoverkiller.model.RecipeListResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRecipesFragment extends Fragment {

    private SearchRecipesViewModel searchRecipesViewModel;

    private RecyclerView recyclerView;
    private RecipesAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SearchView searchView;
    //private ArrayList<String> recipesList =
      //      new ArrayList<>(Arrays.asList("chicken", "roast", "orange juice", "chiashh", "kriso", "beef", "beef 1", "beef 2", "beef 3",
        //            "chicken 1", "chicken 2", "chicken 3", "carrot", "carrot 1", "carrot 2", "carrot 3", "potato", "potato 1", "potato 2",
          //          "potato 3", "rice", "rice 1", "rice 2", "rice 3"));

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchRecipesViewModel =
                ViewModelProviders.of(this).get(SearchRecipesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_recipes, container, false);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recipes_recycler_view);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        //mAdapter = new RecipesAdapter(recipesList);
        recyclerView.setAdapter(mAdapter);

        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        //recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.recipes_menu, menu);
        MenuItem item = menu.findItem(R.id.recipe_search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onStart() {
        super.onStart();
        Call<RecipeListResponse> call = ((LeftoverKillerApplication) getActivity().getApplication()).apiService.getRecipes();
        call.enqueue(new Callback<RecipeListResponse>() {
            @Override
            public void onResponse(Call<RecipeListResponse> call, Response<RecipeListResponse> response) {
                if (response.body().getRecipes() != null) {
                    mAdapter = new RecipesAdapter(response.body().getRecipes());
                    recyclerView.setAdapter(mAdapter);
                } else {
                    recyclerView.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<RecipeListResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Please check your network connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}