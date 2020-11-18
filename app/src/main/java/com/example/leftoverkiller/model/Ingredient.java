package com.example.leftoverkiller.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ingredient {

    @SerializedName("id")
    int ingredientId;

    @SerializedName("name")
    String name;

    @SerializedName("image_url")
    String imageURL;

    @SerializedName("top_recipes")
    List<Recipe> topRecipes;

    @SerializedName("success")
    Boolean success;

    @SerializedName("error")
    String error;

    public Ingredient(int ingredientId, String name, String imageURL, List<Recipe> topRecipes, Boolean success, String error) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.imageURL = imageURL;
        this.topRecipes = topRecipes;
        this.success = success;
        this.error = error;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public List<Recipe> getTopRecipes() {
        return topRecipes;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }
}
