package com.example.leftoverkiller.ui.favorites;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class FavoriteRecipesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FavoriteRecipesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}