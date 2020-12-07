package com.example.leftoverkiller;

import com.example.leftoverkiller.application.IngredientsAdapter;
import com.example.leftoverkiller.application.MatchingRecipeAdapter;
import com.example.leftoverkiller.application.RecipesAdapter;
import com.example.leftoverkiller.model.Ingredient;
import com.example.leftoverkiller.model.Recipe;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTests {
    @Test
    public void recipeAdapterTest() {

        List<Recipe> list = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>();
        Recipe recipe = new Recipe(1, "chicken", ingredients, "x", "x", true, "");
        list.add(recipe);
        RecipesAdapter adapter = new RecipesAdapter(list, null);
        assertEquals("RecipeAdapter contains the added recipe", recipe.getName(), adapter.getRecipesList().get(0).getName());

    }

    @Test
    public void matchingRecipeAdapterTest() {
        List<Recipe> list = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>();
        Recipe recipe = new Recipe(1, "chicken", ingredients, "x", "x", true, "error");
        list.add(recipe);
        MatchingRecipeAdapter adapter = new MatchingRecipeAdapter(list);
        assertEquals("MatchingRecipeAdapter contains the added recipe", recipe.getName(), adapter.getMatchingRecipes().get(0).getName());

    }

    @Test
    public void ingredientAdapterTest() {
        ArrayList<String> ingredients = new ArrayList<>();
        Ingredient ingredient = new Ingredient(1, "Tomato", "test_url", null, false, "");
        ingredients.add(ingredient.getName());
        IngredientsAdapter adapter = new IngredientsAdapter(ingredients, null, null, null, null,null);
        assertEquals("IngredientsAdapter contains the added recipe", ingredient.getName(), adapter.getIngredients().get(0));
    }
}