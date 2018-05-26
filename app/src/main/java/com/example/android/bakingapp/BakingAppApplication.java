package com.example.android.bakingapp;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;

import com.example.android.bakingapp.data.idlingResources.RecipesIdlingResources;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by beita on 19/05/2018.
 */


public class BakingAppApplication extends Application {

    @Nullable
    private RecipesIdlingResources mIdlingResource;

    public BakingAppApplication() {

        // The IdlingResource will be null in production.
        if (BuildConfig.DEBUG) {
            initializeIdlingResource();
        }

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                // Enable logging only on debug
                return BuildConfig.DEBUG;
            }
        });

    }

    @VisibleForTesting
    @NonNull
    private IdlingResource initializeIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new RecipesIdlingResources();
        }
        return mIdlingResource;
    }

    public void setIdleState(boolean state) {
        if (mIdlingResource != null)
            mIdlingResource.setIdleState(state);
    }

    @Nullable
    public RecipesIdlingResources getIdlingResource() {
        return mIdlingResource;
    }
}
