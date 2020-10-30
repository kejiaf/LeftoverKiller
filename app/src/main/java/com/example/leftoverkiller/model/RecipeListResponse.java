package com.example.leftoverkiller.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeListResponse {

    @SerializedName("success")
    Boolean success;

    @SerializedName("recipes")
    List<Recipe> recipes;

    public RecipeListResponse(Boolean success, List<Recipe> recipes) {
        this.success = success;
        this.recipes = recipes;
    }

    public Boolean getSuccess() {
        return success;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
