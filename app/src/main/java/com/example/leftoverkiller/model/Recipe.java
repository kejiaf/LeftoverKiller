package com.example.leftoverkiller.model;

import java.util.List;

public class Recipe {

    int recipeId;

    String name;

    List<Ingredient> ingredients;

    String imageURL;

    String procedure;

    public Recipe(int recipeId, String name, List<Ingredient> ingredients, String imageUrl, String procedure) {
        this.recipeId = recipeId;
        this.name = name;
        this.ingredients = ingredients;
        this.imageURL = imageUrl;
        this.procedure = procedure;
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

    public String getImageUrl() {
        return imageURL;
    }

    public String getProcedure() {
        return procedure;
    }
}
