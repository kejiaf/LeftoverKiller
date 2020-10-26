package com.example.leftoverkiller.network;

import com.example.leftoverkiller.model.Ingredient;
import com.example.leftoverkiller.model.Recipe;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LeftoverKillerInterface {

    @FormUrlEncoded
    @POST("get_recipes.php")
    Call<Recipe> getRecipes();

    @FormUrlEncoded
    @POST("get_ingredients.php")
    Call<Ingredient> getIngredients();

}
