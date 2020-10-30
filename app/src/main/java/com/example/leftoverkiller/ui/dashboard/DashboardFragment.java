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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.example.leftoverkiller.IngredientSearchActivity;
import com.example.leftoverkiller.R;
import com.example.leftoverkiller.application.IngredientsAdapter;
import com.example.leftoverkiller.application.LeftoverKillerApplication;
import com.example.leftoverkiller.model.Ingredient;
import com.example.leftoverkiller.model.IngredientListResponse;

import java.util.ArrayList; //TODO: remove once placeholder is changed
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    // Floating Action Button
    FloatingActionButton fabSearch;
    Button addIngredient;

    // Recycler view stuff
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> selectedIngredientDataset = new ArrayList<>(); // TODO: placeholder!
    private List<Ingredient> availableIngredients;
    private AutoCompleteTextView autoTextView;
    private Set<String> availableIngredientSet = new HashSet();
    private Set<String> selectedIngredientSet = new HashSet();

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
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // If dataset is not null, add adapter
        buildRecyclerView();


        autoTextView = (AutoCompleteTextView)
                getView().findViewById(R.id.ingredients_search_auto_complete);
        autoTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("AUTOTEXTVIEW", "TEST");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("AUTOTEXTVIEW", "TEST");
            }
        });

        autoTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addToRecyclerView(availableIngredients.get(i).getName());
//                Log.d("AUTOTEXTVIEW", "TEST");
            }
        });

        // Set up autocomplete text view and corresponding add button to correct dimensions
        View parent = (View) autoTextView.getParent();
        autoTextView.setWidth((int) (parent.getWidth() * 0.9)); // 90% of width
        addIngredient.setWidth((int) (parent.getWidth() * 0.1)); // 10% of width


        callAPI();
        // Set up floating action button
        // TODO: search for recipes!
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), IngredientSearchActivity.class));
            }
        });

        // Set up add ingredient button
        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: implement with endpoints
                String enteredIngredient = autoTextView.getText().toString();
                addToRecyclerView(enteredIngredient);
            }
        });
    }


    private void buildRecyclerView() {
        // If dataset is not null, add adapter
        if (selectedIngredientDataset != null) {
            Log.i("ingredientlist", "ingredient data not null with size " + selectedIngredientDataset.size());
            // Adapter for recycler view
            mAdapter = new IngredientsAdapter(selectedIngredientDataset, selectedIngredientSet);
            recyclerView.setAdapter(mAdapter);
        }

        // Hide recycler view if empty and replace with an empty dataset message
        if (selectedIngredientDataset == null || selectedIngredientDataset.size() == 0) {
            TextView tvEmptyWarning = getView().findViewById(R.id.tv_empty_view_warning);
            recyclerView.setVisibility(View.GONE);
            tvEmptyWarning.setVisibility(View.VISIBLE);
        } else {
            TextView tvEmptyWarning = getView().findViewById(R.id.tv_empty_view_warning);
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyWarning.setVisibility(View.GONE);
        }
    }

    private void callAPI() {

        Call<IngredientListResponse> call = ((LeftoverKillerApplication) getActivity().getApplication()).apiService.getIngredients();
        call.enqueue(new Callback<IngredientListResponse>() {
            @Override
            public void onResponse(Call<IngredientListResponse> call, Response<IngredientListResponse> response) {
                if (response.body().getIngredients() != null) {
                    availableIngredients = response.body().getIngredients();
                    availableIngredientSet.clear();
                    List<String> ingredientList = new ArrayList<>();
                    for (Ingredient ingredient : availableIngredients) {
                        ingredientList.add(ingredient.getName());
                        availableIngredientSet.add(ingredient.getName());
                    }
                    // Set up autocomplete text view
                    ArrayAdapter<String> autoTextAdapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_dropdown_item_1line, ingredientList);
                    autoTextView.setAdapter(autoTextAdapter);
                }
            }

            @Override
            public void onFailure(Call<IngredientListResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Please check your network connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToRecyclerView(String ingredient) {
        if (!availableIngredientSet.contains(ingredient)) {
            Toast.makeText(getActivity(), "Ingredient not available", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedIngredientSet.contains(ingredient))
            return;
        selectedIngredientDataset.add(ingredient);
        selectedIngredientSet.add(ingredient);
        mAdapter.notifyDataSetChanged();
    }
}