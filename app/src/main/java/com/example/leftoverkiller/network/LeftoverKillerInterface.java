package com.example.leftoverkiller.network;

import com.example.leftoverkiller.model.IngredientListRequest;
import com.example.leftoverkiller.model.IngredientListResponse;
import com.example.leftoverkiller.model.RecipeListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LeftoverKillerInterface {

    @FormUrlEncoded
    @POST("get_recipes.php")
    Call<RecipeListResponse> getRecipes();

    @FormUrlEncoded
    @POST("get_ingredients.php")
    Call<IngredientListResponse> getIngredients();

    @POST("get_matching_recipes.php")
    Call<RecipeListResponse> getMatchingRecipes(@Body IngredientListRequest ingredientList);


}
