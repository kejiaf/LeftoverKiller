package com.example.leftoverkiller.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeListResponse {

    @SerializedName("success")
    Boolean success;

    @SerializedName("recipes")
    List<Recipe> recipes;

    @SerializedName("error")
    String error;

    public RecipeListResponse(Boolean success, List<Recipe> recipes, String error) {
        this.success = success;
        this.recipes = recipes;
        this.error = error;
    }

    public Boolean getSuccess() {
        return success;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public String getError() {
        return error;
    }
}
