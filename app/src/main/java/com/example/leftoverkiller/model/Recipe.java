package com.example.leftoverkiller.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe {

    @SerializedName("recipe_id")
    int recipeId;

    @SerializedName("recipe_name")
    String name;

    @SerializedName("ingredients")
    List<Ingredient> ingredients;

    @SerializedName("img_url")
    String imageURL;

    @SerializedName("instructions")
    String instructions;

    @SerializedName("success")
    Boolean success;

    @SerializedName("error")
    String error;

    public Recipe(int recipeId, String name, List<Ingredient> ingredients, String imageURL, String instructions, Boolean success, String error) {
        this.recipeId = recipeId;
        this.name = name;
        this.ingredients = ingredients;
        this.imageURL = imageURL;
        this.instructions = instructions;
        this.success = success;
        this.error = error;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getInstructions() {
        return instructions;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }
}
