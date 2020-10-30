package com.example.leftoverkiller.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientListRequest {
    @SerializedName("ingredients")
    List<String> ingredients;

    public IngredientListRequest(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
