package com.example.leftoverkiller.application;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leftoverkiller.R;
import com.example.leftoverkiller.RecipeDetailsActivity;
import com.example.leftoverkiller.model.Recipe;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecipesAdapter extends
        RecyclerView.Adapter<com.example.leftoverkiller.application.RecipesAdapter.MyViewHolder> implements Filterable {
    private List<Recipe> recipesList;
    private List<Recipe> recipesListAll;
    Context mcontext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;

        public MyViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    public List<Recipe> getRecipesList() {
        return recipesListAll;
    }

    public RecipesAdapter(List<Recipe> recipesList, Context context) {
        this.recipesList = recipesList;
        this.recipesListAll = new ArrayList<>(recipesList);
        mcontext = context;
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
    public void onBindViewHolder(@NonNull final RecipesAdapter.MyViewHolder myViewHolder, int i) {
        TextView recipeName = myViewHolder.linearLayout.findViewById(R.id.recipe_name);
        ImageView recipeImage = myViewHolder.linearLayout.findViewById(R.id.avatar);
        recipeName.setText(recipesList.get(i).getName());
        Picasso.get().load(recipesList.get(i).getImageURL()).fit().centerCrop().into(recipeImage);
        final int recipeId = recipesList.get(i).getRecipeId();
        final Context context = myViewHolder.linearLayout.getContext();

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecipeDetailsActivity.class);
                intent.putExtra("recipeID", recipeId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public int RecipeID(int position) {
        return recipesList.get(position).getRecipeId();
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Recipe> filterList = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                filterList.addAll(recipesListAll);
            } else {
                for (Recipe recipe : recipesListAll) {
                    if (recipe.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
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
                recipesList.addAll((Collection<? extends Recipe>) filterResults.values);
                notifyDataSetChanged();
        }
    };


}
