package com.example.leftoverkiller.ui.dashboard;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.example.leftoverkiller.R;
import com.example.leftoverkiller.application.IngredientsAdapter;

import java.util.ArrayList; //TODO: remove once placeholder is changed
import java.util.Arrays;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    // Floating Action Button
    FloatingActionButton fab;

    // Recycler view stuff
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> ingredientDataset = new ArrayList<>(Arrays.asList("ingredient 1", "ingredient 2", "ingredient 3", "ingredient 4", "ingredient 5")); // TODO: placeholder!
    //private ArrayList<String> ingredientDataset = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_ingredients, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get floating action button in layout
        fab = getView().findViewById(R.id.fab_search_recipes);

        // Get recycler view
        recyclerView = (RecyclerView) getView().findViewById(R.id.ingredients_recycler_view);

        // Setting below to false along with using NestedScrollView forces the page to only scroll
        // with one scroll bar
        recyclerView.setNestedScrollingEnabled(false);

        // Set recycler layout manager
        layoutManager = new LinearLayoutManager( getActivity() );
        recyclerView.setLayoutManager(layoutManager);

        // TODO: empty dataset message if dataset is empty
        // If dataset is not null, add adapter
        buildRecyclerView();

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) getView().findViewById(R.id.ingredients_search_view);
        // current activity is the searchable activity
        searchView.setSearchableInfo( searchManager.getSearchableInfo( getActivity().getComponentName() ) );
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        // Set up floating action button
        // TODO: search for recipes!
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void buildRecyclerView() {
        // If dataset is not null, add adapter
        if (ingredientDataset != null) {
            Log.i("ingredientlist", "ingredient data not null with size " + ingredientDataset.size());
            // Adapter for recycler view
            mAdapter = new IngredientsAdapter(ingredientDataset);
            recyclerView.setAdapter(mAdapter);
        }

        // Hide recycler view if empty and replace with an empty dataset message
        if (ingredientDataset == null || ingredientDataset.size() == 0) {
            TextView tvEmptyWarning = getView().findViewById(R.id.tv_empty_view_warning);
            recyclerView.setVisibility(View.GONE);
            tvEmptyWarning.setVisibility(View.VISIBLE);
        } else {
            TextView tvEmptyWarning = getView().findViewById(R.id.tv_empty_view_warning);
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyWarning.setVisibility(View.GONE);
        }
    }
}