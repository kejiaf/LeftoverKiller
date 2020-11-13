package com.example.leftoverkiller.application;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leftoverkiller.IngredientDetailsActivity;
import com.example.leftoverkiller.R;
import com.example.leftoverkiller.model.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IngredientsAdapter extends
        RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>
{
    private List<String> ingredientsDataset; //TODO: change to Ingredient later
    private List<Integer> ingredientsIDs;
    private List<Ingredient> availableIngredients = new ArrayList<>();
    Set<String> ingredientSet;
    TextView emptyWarn;
    RecyclerView recyclerView;
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
    public IngredientsAdapter(ArrayList<String> ingredientsDataset, Set<String> selectedIngredientSet,
                              TextView emptyWarn, RecyclerView recyclerView, List<Integer> ingredientsIDs,
                              List<Ingredient> availableIngredients) {
        this.ingredientsDataset = ingredientsDataset;
        this.ingredientSet = selectedIngredientSet;
        this.emptyWarn = emptyWarn;
        this.recyclerView = recyclerView;
        this.ingredientsIDs = ingredientsIDs;
        this.availableIngredients = availableIngredients;
    }

    public List<String> getIngredients(){
        return ingredientsDataset;
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
        final TextView ingredientName = holder.linearLayout.findViewById(R.id.ingredient_name);
        ingredientName.setText( ingredientsDataset.get(position) );

        ImageView ingredientImage = holder.linearLayout.findViewById(R.id.avatar);
        ingredientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String ingredientToBeRemoved = ingredientsDataset.get(holder.getAdapterPosition());
                ingredientsDataset.remove(holder.getAdapterPosition());
                ingredientSet.remove(ingredientToBeRemoved);
                notifyDataSetChanged();

                if( ingredientsDataset.isEmpty() )
                {
                    recyclerView.setVisibility(View.GONE);
                    emptyWarn.setVisibility(View.VISIBLE);
                }
            }
        });

        ingredientName.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), IngredientDetailsActivity.class);
                int ingredientID = -1;
                // Search for ingredient in available ingredients using ingredient name
                int i = 0;
                for( Ingredient ingredient : availableIngredients )
                {
                    if( ingredient.getName() == ingredientsDataset.get(holder.getAdapterPosition()) )
                    {
                        ingredientID = availableIngredients.get(i).getIngredientId();
                    }
                    i++;
                }
                intent.putExtra("INGREDIENT_ID", ingredientID );
                view.getContext().startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ingredientsDataset.size();
    }
}