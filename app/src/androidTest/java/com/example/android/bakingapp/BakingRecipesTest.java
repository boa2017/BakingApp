/*
 *
 *  * PROJECT LICENSE
 *  *
 *  * This project was submitted by Beatriz Ovejero as part of the Android Developer
 *  * Nanodegree at Udacity.
 *  *
 *  * As part of Udacity Honor code, your submissions must be your own work, hence
 *  * submitting this project as yours will cause you to break the Udacity Honor Code
 *  * and the suspension of your account.
 *  *
 *  * As author of the project, I allow you to check it as a reference, but if you submit it
 *  * as your own project, it's your own responsibility if you get expelled.
 *  *
 *  * Copyright (c) 2018 Beatriz Ovejero
 *  *
 *  * Besides the above notice, the following license applies and this license notice must be
 *  * included in all works derived from this project.
 *  *
 *  * MIT License
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all
 *  * copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */

package com.example.android.bakingapp;

import android.content.Context;
import android.support.test.espresso.intent.Intents;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.data.models.Recipe;
import com.example.android.bakingapp.ui.activities.RecipeFullStepsActivity;
import com.example.android.bakingapp.ui.activities.StepDetailActivity;
import com.example.android.bakingapp.utilities.BaseTest;
import com.example.android.bakingapp.utilities.Navigation;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by beita on 22/05/2018.
 */


@RunWith(AndroidJUnit4.class)
public class BakingRecipesTest extends BaseTest {

    @Test
    public void clickRecyclerViewItemHasIntentWithAKey() {
        //Checks the existence of the key
        Intents.init();

        Navigation.getRecipeData(0);
        intended(hasExtraWithKey(RecipeFullStepsActivity.RECIPE_KEY));

        Intents.release();

    }

    @Test
    public void clickOnRecyclerViewItem_opensRecipeInfoActivity() {

        Navigation.getRecipeData(0);

        onView(withId(R.id.recipe_details_ingredients))
                .check(matches(isDisplayed()));

        onView(withId(R.id.recipe_step_list))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnRecyclerViewStepItem_opensRecipeStepActivity_orFragment() {
        Navigation.getRecipeData(0);

        boolean twoPaneMode = bakingAppApplication.getResources().getBoolean(R.bool.twoPaneMode);
        if (!twoPaneMode) {
            // Checks if the intent released is StepDetailActivity and the keys are present
            Intents.init();
            Navigation.selectRecipeStep(1);
            intended(hasComponent(StepDetailActivity.class.getName()));
            intended(hasExtraWithKey(StepDetailActivity.RECIPE_KEY));
            intended(hasExtraWithKey(StepDetailActivity.STEP_SELECTED_KEY));
            Intents.release();

            // Check TabLayout
            onView(withId(R.id.recipe_steps_tab_layout))
                    .check(matches(isCompletelyDisplayed()));
        } else {
            Navigation.selectRecipeStep(1);

            onView(withId(R.id.recipe_step_video))
                    .check(matches(isDisplayed()));
        }
    }


    // Check the functionality of the add widget button

    @Test
    public void checkSendToWidgetButtonFunctionality() {
        // Clear the preferences values
        bakingAppApplication.getSharedPreferences(Prefs.PREFS_NAME, Context.MODE_PRIVATE).edit()
                .clear()
                .commit();

        Navigation.getRecipeData(0);

        onView(withId(R.id.action_send_to_widget))
                .check(matches(isDisplayed()))
                .perform(click());

        // Get the recipe base64 string from sharedPrefs
        Recipe recipe = Prefs.loadRecipe(bakingAppApplication);

        // Recipe assert is not null
        assertNotNull(recipe);
    }

}
