package com.example.leftoverkiller;

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
public class endpoint_test {
    @Test
    public void addition_isCorrect() {

        List<Recipe> list = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>();
        Recipe recipe = new Recipe(1, "chicken", ingredients, "x", "x", true);
        list.add(recipe);
        RecipesAdapter adapter = new RecipesAdapter(list);
        assertEquals("RecipeAdapter works", recipe.getName(), adapter.getRecipesList().get(0).getName());

    }
}