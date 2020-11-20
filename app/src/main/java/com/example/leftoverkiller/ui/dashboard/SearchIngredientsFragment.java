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

import com.example.leftoverkiller.MatchingRecipesActivity;
import com.example.leftoverkiller.R;
import com.example.leftoverkiller.application.IngredientsAdapter;
import com.example.leftoverkiller.application.LeftoverKillerApplication;
import com.example.leftoverkiller.model.Ingredient;
import com.example.leftoverkiller.model.IngredientListResponse;

import java.util.ArrayList; //TODO: remove once placeholder is changed
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchIngredientsFragment extends Fragment {

    // Floating Action Button
    FloatingActionButton fabSearch;
    Button addIngredient;

    // Recycler view stuff
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> selectedIngredientDataset = new ArrayList<>(); // TODO: placeholder!
    private List<Ingredient> availableIngredients = new ArrayList<>();
    private List<Integer> availableIngredientsIDs = new ArrayList<>();
    private List<Integer> ingredientIDs = new ArrayList<>();
    private AutoCompleteTextView autoTextView;
    private Set<String> availableIngredientSet = new HashSet();
    private Set<String> selectedIngredientSet = new HashSet();

    //private ArrayList<String> ingredientDataset = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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
                addToRecyclerView(adapterView.getAdapter().getItem(i).toString());
//                Log.d("AUTOTEXTVIEW", "TEST");
            }
        });

        // Set up autocomplete text view and corresponding add button to correct dimensions
        View parent = (View) autoTextView.getParent();
        autoTextView.setWidth((int) (parent.getWidth() * 0.9)); // 90% of width
        addIngredient.setWidth((int) (parent.getWidth() * 0.1)); // 10% of width


        callAPI();

        // If dataset is not null, add adapter
        buildRecyclerView();

        // Set up floating action button
        // TODO: search for recipes!
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if there is at least on selected ingredient, do search, otherwise display msg
                if( selectedIngredientDataset != null && selectedIngredientDataset.size() > 0 ) {
                    Intent searchIntent = new Intent(getActivity(), MatchingRecipesActivity.class);
                    searchIntent.putExtra("ingredient_list", selectedIngredientDataset);
                    getActivity().startActivity(searchIntent);
                }
                else
                {
                    Toast.makeText( getActivity(), "You must select at least one ingredient." +
                            " Select an ingredient from the search bar and try " +
                            "again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up add ingredient button
        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredIngredient = autoTextView.getText().toString();
                addToRecyclerView(enteredIngredient);
            }
        });
    }


    private void buildRecyclerView() {
        // If dataset is not null, add adapter
        TextView tvEmptyWarning = getView().findViewById(R.id.tv_empty_view_warning);

        if (selectedIngredientDataset != null && selectedIngredientDataset.size() == 0) {
            // Adapter for recycler view
            mAdapter = new IngredientsAdapter(selectedIngredientDataset, selectedIngredientSet,
                    tvEmptyWarning, recyclerView, availableIngredientsIDs, availableIngredients);
            recyclerView.setAdapter(mAdapter);
        }

        // Hide recycler view if empty and replace with an empty dataset message
        if (selectedIngredientDataset == null || selectedIngredientDataset.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            tvEmptyWarning.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyWarning.setVisibility(View.GONE);
        }
    }

    private void callAPI() {

        Call<IngredientListResponse> call = ((LeftoverKillerApplication) getActivity().getApplication()).apiService.getIngredients();
        call.enqueue(new Callback<IngredientListResponse>() {
            @Override
            public void onResponse(Call<IngredientListResponse> call, Response<IngredientListResponse> response) {
                IngredientListResponse ingredientListResponse = response.body();
                if (ingredientListResponse.getSuccess() && ingredientListResponse.getIngredients() != null && ingredientListResponse.getIngredients().size() != 0) {
                    List<Ingredient> listResponse = response.body().getIngredients();
                    availableIngredients.addAll(listResponse);
                    availableIngredientSet.clear();
                    List<String> ingredientList = new ArrayList<>();
                    for (Ingredient ingredient : availableIngredients) {
                        ingredientList.add(ingredient.getName());
                        availableIngredientSet.add(ingredient.getName());
                        availableIngredientsIDs.add( ingredient.getIngredientId() );
                    }
                    // Set up autocomplete text view
                    ArrayAdapter<String> autoTextAdapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_dropdown_item_1line, ingredientList);
                    autoTextView.setAdapter(autoTextAdapter);
                }
                else if( response.body().getSuccess() == false )
                {
                    // TODO: fix getError() in following lines:
                    Toast.makeText( getActivity(), "Was able to contact server, but " +
                            "something went wrong with fetching available ingredients (" +
                            response.body().getError() + ").", Toast.LENGTH_SHORT ).show();
                }
                else if( response.body().getIngredients() == null )
                {
                    Toast.makeText( getActivity(), "Was able to contact server, but could not " +
                            "get list of available ingredients.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IngredientListResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Please check your network connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToRecyclerView(String ingredient) {
        if(ingredient.equalsIgnoreCase("") || ingredient == null) // if ingredient is empty string
        {
            Toast.makeText(getActivity(), "Ingredient field is empty, please type in or " +
                    "select an ingredient to add it to the list.", Toast.LENGTH_SHORT).show();
            return;
        }

        // if entered ingredient does not match predefined entry
        if (!availableIngredientSet.contains(ingredient)) {
            Toast.makeText(getActivity(), "Ingredient not available", Toast.LENGTH_SHORT).show();
            return;
        }

        // if ingredient has already been selected
        if (selectedIngredientSet.contains(ingredient)) {
            Toast.makeText(getActivity(), "Ingredient has already been added.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (selectedIngredientDataset.isEmpty()) {
            selectedIngredientDataset.add(ingredient);
            selectedIngredientSet.add(ingredient);
            mAdapter.notifyDataSetChanged();

            // Set recycler view to be visible when first element added
            TextView tvEmptyWarning = getView().findViewById(R.id.tv_empty_view_warning);
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyWarning.setVisibility(View.GONE);
        } else {
            selectedIngredientDataset.add(ingredient);
            selectedIngredientSet.add(ingredient);
            mAdapter.notifyDataSetChanged();
        }

        autoTextView.setText("");
    }
}