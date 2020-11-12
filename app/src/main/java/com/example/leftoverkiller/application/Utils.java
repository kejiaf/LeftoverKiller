package com.example.leftoverkiller.application;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.leftoverkiller.model.Recipe;
import com.example.leftoverkiller.model.RecipeListResponse;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Utils {
    public static List<Recipe> fetchFavorites(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        String json = mPrefs.getString("favorites", null);
        if (json == null)
            return null;
        RecipeListResponse recipes = ((LeftoverKillerApplication) context.getApplicationContext()).gson.fromJson(json, RecipeListResponse.class);
        return recipes.getRecipes();
    }

    public static boolean addRecipe(Recipe recipe, Context context) {
        if (recipe == null)
            return false;
        List<Recipe> recipes = fetchFavorites(context);
        if (recipes == null)
            recipes = new ArrayList<>();
        else
            for (Recipe recipeItem : recipes)
                if (recipeItem.getRecipeId() == recipe.getRecipeId())
                    return true;
        recipes.add(recipe);
        RecipeListResponse recipeListResponse = new RecipeListResponse(true, recipes);
        String json = ((LeftoverKillerApplication) context.getApplicationContext()).gson.toJson(recipeListResponse);
        SharedPreferences mPrefs = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        mPrefs.edit().putString("favorites", json).commit();
        return true;
    }
}