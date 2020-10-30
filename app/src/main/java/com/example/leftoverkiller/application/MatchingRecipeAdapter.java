package com.example.leftoverkiller.application;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;

import com.example.leftoverkiller.R;

import java.util.List;

public class MatchingRecipeAdapter extends
        RecyclerView.Adapter<com.example.leftoverkiller.application.MatchingRecipeAdapter.MyViewHolder>
{
    private List<String> mathcingRecipeDataset; //TODO: change to Ingredient later
    public boolean areItemsClickable = false;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        public LinearLayout linearLayout;

        public MyViewHolder(LinearLayout ll) {
            super(ll);
            linearLayout = ll;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MatchingRecipeAdapter(List<String> mathcingRecipeDataset) {
        this.mathcingRecipeDataset = mathcingRecipeDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public com.example.leftoverkiller.application.MatchingRecipeAdapter.MyViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_matching_recipe_list_entry, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        //TODO: set textview or something similar to start off with
        TextView recipeName = holder.linearLayout.findViewById(R.id.recipe_name); //TODO: change to recipe_name
        recipeName.setText( mathcingRecipeDataset.get(position) );
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mathcingRecipeDataset.size();
    }
}