package com.example.android.bakingapp.utilities;

import android.support.test.espresso.contrib.RecyclerViewActions;

import com.example.android.bakingapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by beita on 21/05/2018.
 */

public class Navigation {
    public static void getRecipeData(int recipePosition) {
        onView(withId(R.id.recipe_list_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipePosition, click()));
    }

    public static void selectRecipeStep(int recipeStep) {
        onView(withId(R.id.recipe_step_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipeStep, click()));
    }
}
