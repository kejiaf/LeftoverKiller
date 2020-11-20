package com.example.leftoverkiller.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientListResponse {

    @SerializedName("success")
    Boolean success;

    @SerializedName("error")
    String error;

    @SerializedName("ingredients")
    List<Ingredient> ingredients;

    public IngredientListResponse(Boolean success, String error, List<Ingredient> ingredients) {
        this.success = success;
        this.error = error;
        this.ingredients = ingredients;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
