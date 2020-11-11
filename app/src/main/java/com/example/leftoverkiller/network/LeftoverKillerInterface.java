package com.example.leftoverkiller.network;

import com.example.leftoverkiller.model.Ingredient;
import com.example.leftoverkiller.model.IngredientListRequest;
import com.example.leftoverkiller.model.IngredientListResponse;
import com.example.leftoverkiller.model.Recipe;
import com.example.leftoverkiller.model.RecipeListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LeftoverKillerInterface {

    @POST("get_recipes.php")
    Call<RecipeListResponse> getRecipes();

    @POST("get_ingredients.php")
    Call<IngredientListResponse> getIngredients();

    @POST("get_matching_recipes.php")
    Call<RecipeListResponse> getMatchingRecipes(@Body IngredientListRequest ingredientList);

    @POST("get_recipe_details.php")
    Call<Recipe> getRecipeDetails(@Body IngredientListRequest ingredientList);

    @POST("get_ingredient_details.php")
    Call<Ingredient> getIngredientDetails(@Body IngredientListRequest ingredientList);

}
