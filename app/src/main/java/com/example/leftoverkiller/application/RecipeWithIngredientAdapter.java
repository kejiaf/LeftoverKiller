package com.example.leftoverkiller.application;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leftoverkiller.R;
import com.example.leftoverkiller.model.Recipe;

import java.util.List;

public class RecipeWithIngredientAdapter extends
        RecyclerView.Adapter<RecipeWithIngredientAdapter.MyViewHolder>
{
    private List<Recipe> recipesList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout linearLayout;
        public MyViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecipeWithIngredientAdapter(List<Recipe> recipesList) {
        recipesList = recipesList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecipeWithIngredientAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_recipes_list_entry, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView recipeName = holder.linearLayout.findViewById(R.id.ingredient_detail_name);
        recipeName.setText( recipesList.get(position).getName() );

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recipesList.size();
    }
}
