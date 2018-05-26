package com.example.android.bakingapp.utilities;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;

import com.example.android.bakingapp.BakingAppApplication;
import com.example.android.bakingapp.ui.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

/**
 * Created by beita on 21/05/2018.
 */

public abstract class BaseTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    protected BakingAppApplication bakingAppApplication;
    protected IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        bakingAppApplication = (BakingAppApplication) activityTestRule.getActivity().getApplicationContext();
        mIdlingResource = bakingAppApplication.getIdlingResource();
        // Register Idling Resources
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
