package com.example.leftoverkiller.ui.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import com.example.leftoverkiller.RecipeDetailsActivity;
import com.example.leftoverkiller.application.LeftoverKillerApplication;
import com.example.leftoverkiller.application.RecipesAdapter;
import com.example.leftoverkiller.application.Utils;
import com.example.leftoverkiller.model.Recipe;
import com.example.leftoverkiller.model.RecipeListResponse;
import com.example.leftoverkiller.ui.home.SearchRecipesFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteRecipesFragment extends Fragment {


    private RecyclerView recyclerView;
    private RecipesAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search_recipes, container, false);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recipes_recycler_view);
        List<Recipe> recipes = Utils.fetchFavorites(getContext());
        layoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new RecipesAdapter(recipes == null ? new ArrayList<Recipe>() : recipes);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }


}