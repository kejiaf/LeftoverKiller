package com.example.leftoverkiller.application;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leftoverkiller.R;
import com.example.leftoverkiller.model.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IngredientsAdapter extends
        RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>
{
    private List<String> ingredientsDataset; //TODO: change to Ingredient later
    Set<String> ingredientSet;
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
    public IngredientsAdapter(ArrayList<String> ingredientsDataset, Set<String> selectedIngredientSet) {
        this.ingredientsDataset = ingredientsDataset;
        this.ingredientSet = selectedIngredientSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public com.example.leftoverkiller.application.IngredientsAdapter.MyViewHolder
        onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_ingredient_list_entry, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        //TODO: set textview or something similar to start off with
        TextView ingredientName = holder.linearLayout.findViewById(R.id.ingredient_name);
        ingredientName.setText( ingredientsDataset.get(position) );
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ingredientsDataset.size();
    }
}