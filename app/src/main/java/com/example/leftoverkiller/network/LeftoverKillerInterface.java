package com.example.leftoverkiller.network;

import com.example.leftoverkiller.model.Ingredient;
import com.example.leftoverkiller.model.IngredientListReponse;
import com.example.leftoverkiller.model.Recipe;
import com.example.leftoverkiller.model.RecipeListResponse;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LeftoverKillerInterface {

    @FormUrlEncoded
    @POST("get_recipes.php")
    Call<RecipeListResponse> getRecipes();

    @FormUrlEncoded
    @POST("get_ingredients.php")
    Call<IngredientListReponse> getIngredients();

}
