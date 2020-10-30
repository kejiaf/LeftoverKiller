package com.example.leftoverkiller.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.example.leftoverkiller.R;

public class FavoriteRecipesFragment extends Fragment {

    private FavoriteRecipesViewModel favoriteRecipesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoriteRecipesViewModel =
                ViewModelProviders.of(this).get(FavoriteRecipesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_fav_recipes, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        favoriteRecipesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}