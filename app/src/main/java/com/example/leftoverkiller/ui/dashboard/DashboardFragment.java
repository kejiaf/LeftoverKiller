package com.example.leftoverkiller.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;

import com.example.leftoverkiller.IngredientSearchActivity;
import com.example.leftoverkiller.R;
import com.example.leftoverkiller.application.IngredientsAdapter;

import java.util.ArrayList; //TODO: remove once placeholder is changed
import java.util.Arrays;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    // Floating Action Button
    FloatingActionButton fabSearch;
    Button addIngredient;

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
        fabSearch = getView().findViewById(R.id.fab_search_recipes);

        // Get add ingredients button
        addIngredient = getView().findViewById(R.id.b_add_ingredient);

        // Get recycler view
        recyclerView = (RecyclerView) getView().findViewById(R.id.ingredients_recycler_view);

        // Setting below to false along with using NestedScrollView forces the page to only scroll
        // with one scroll bar
        recyclerView.setNestedScrollingEnabled(false);

        // Set recycler layout manager
        layoutManager = new LinearLayoutManager( getActivity() );
        recyclerView.setLayoutManager(layoutManager);

        // If dataset is not null, add adapter
        buildRecyclerView(ingredientDataset);

        // Set up autocomplete text view
        ArrayAdapter<String> autoTextAdapter = new ArrayAdapter<String>( getContext(),
                android.R.layout.simple_dropdown_item_1line, ingredientDataset );
        final AutoCompleteTextView autoTextView = (AutoCompleteTextView)
                getView().findViewById(R.id.ingredients_search_auto_complete);
        autoTextView.setAdapter(autoTextAdapter);

        // Set up autocomplete text view and corresponding add button to correct dimensions
        View parent = (View) autoTextView.getParent();
        autoTextView.setWidth( (int)( parent.getWidth()  * 0.9 ) ); // 90% of width
        addIngredient.setWidth( (int)( parent.getWidth()  * 0.1 ) ); // 10% of width

        // Set up floating action button
        // TODO: search for recipes!
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                getActivity().startActivity( new Intent(getActivity(), IngredientSearchActivity.class) );
            }
        });

        // Set up add ingredient button
        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // TODO: implement with endpoints
                String enteredIngredient = autoTextView.getText().toString();
                addToRecyclerView( enteredIngredient );
            }
        });
    }

    private void buildRecyclerView(ArrayList<String> ingredientDataset) {
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

    private void addToRecyclerView( String ingredient )
    {
        // TODO: implement with endpoints
        ingredientDataset.add( ingredient );
        mAdapter.notifyDataSetChanged();
    }
}