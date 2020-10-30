package com.example.leftoverkiller.application;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leftoverkiller.R;
import com.example.leftoverkiller.model.Recipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecipesAdapter extends
        RecyclerView.Adapter<com.example.leftoverkiller.application.RecipesAdapter.MyViewHolder> implements Filterable {
    private List<Recipe> recipesList;
    private List<Recipe> recipesListAll;

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public LinearLayout linearLayout;
        public MyViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    public RecipesAdapter(List<Recipe> recipesList) {
        this.recipesList = recipesList;
        this.recipesListAll= new ArrayList<>(recipesList);
    }

    @NonNull
    @Override
    public RecipesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_recipes_list_entry, viewGroup, false);
        RecipesAdapter.MyViewHolder vh = new RecipesAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapter.MyViewHolder myViewHolder, int i) {
        TextView recipeName = myViewHolder.linearLayout.findViewById(R.id.recipe_name);
        recipeName.setText(recipesList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }
    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Recipe> filterList = new ArrayList<>();
            if(charSequence.toString().isEmpty()){
                filterList.addAll(recipesListAll);
            }else{
                for(Recipe recipe: recipesListAll){
                    if(recipe.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filterList.add(recipe);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            recipesList.clear();
            recipesList.addAll((Collection<? extends Recipe>)filterResults.values);
            notifyDataSetChanged();
        }
    };




    
}
