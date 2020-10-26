package com.example.leftoverkiller.model;

import java.util.List;

public class Ingredient {
    int ingredientId;
    String name;
    String imageURL;
    List<Recipe> topRecipes;

    public Ingredient(int ingredientId, String name, String imageURL, List<Recipe> topRecipes) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.imageURL = imageURL;
        this.topRecipes = topRecipes;
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
}
