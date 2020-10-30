package com.example.leftoverkiller.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientListReponse {

    @SerializedName("success")
    Boolean success;

    @SerializedName("ingredients")
    List<Ingredient> ingredients;

    public IngredientListReponse(Boolean success, List<Ingredient> ingredients) {
        this.success = success;
        this.ingredients = ingredients;
    }

    public Boolean getSuccess() {
        return success;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
