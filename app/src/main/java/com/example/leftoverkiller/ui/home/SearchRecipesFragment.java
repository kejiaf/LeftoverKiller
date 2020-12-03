package com.example.leftoverkiller.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.leftoverkiller.R;
import com.example.leftoverkiller.RecipeDetailsActivity;
import com.example.leftoverkiller.application.LeftoverKillerApplication;
import com.example.leftoverkiller.application.RecipesAdapter;

import retrofit2.Call;

import com.example.leftoverkiller.model.RecipeListResponse;

import retrofit2.Callback;
import retrofit2.Response;

public class SearchRecipesFragment extends Fragment {


    private RecyclerView recyclerView;
    private RecipesAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SearchView searchView;
    private TextView  tvEmptyWarning;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search_recipes, container, false);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recipes_recycler_view);
        tvEmptyWarning = getView().findViewById(R.id.recipes_label);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new MyItemClickListener(recyclerView){
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                int recipeID = mAdapter.RecipeID(position);
                Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
                intent.putExtra("recipeID", recipeID);
                startActivity(intent);
            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public abstract class MyItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

        private GestureDetectorCompat mGestureDetectorCompat;
        private RecyclerView mRecyclerView;

        public MyItemClickListener(RecyclerView recyclerView) {
            this.mRecyclerView = recyclerView;
            mGestureDetectorCompat =
                    new GestureDetectorCompat(mRecyclerView.getContext(),new SearchRecipesFragment.MyItemClickListener.MyGestureListener());
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetectorCompat.onTouchEvent(e);
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetectorCompat.onTouchEvent(e);
            return false;
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }

        public abstract void onItemClick(RecyclerView.ViewHolder vh);

        private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null) {
                    RecyclerView.ViewHolder viewHolder =
                            mRecyclerView.getChildViewHolder(childView);
                    onItemClick(viewHolder);
                }
                return true;
            }
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.recipes_menu, menu);
        MenuItem item = menu.findItem(R.id.recipe_search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onStart() {
        super.onStart();
        Call<RecipeListResponse> call = ((LeftoverKillerApplication) getActivity().getApplication()).apiService.getRecipes();
        call.enqueue(new Callback<RecipeListResponse>() {
            @Override
            public void onResponse(Call<RecipeListResponse> call, Response<RecipeListResponse> response) {
                if (response.body().getRecipes() != null) {
                    mAdapter = new RecipesAdapter(response.body().getRecipes(), getContext());
                    recyclerView.setAdapter(mAdapter);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No Recipes Found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RecipeListResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Please check your network connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}