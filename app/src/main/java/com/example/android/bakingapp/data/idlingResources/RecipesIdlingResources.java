package com.example.android.bakingapp.data.idlingResources;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by beita on 15/05/2018.
 */

public class RecipesIdlingResources implements IdlingResource {

    @Nullable
    private volatile ResourceCallback callback;

    // Idleness is controlled by this boolean.
    private AtomicBoolean isIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    /**
     * Change status of idling resource.
     */
    public void setIdleState(boolean idleState) {
        isIdleNow.set(idleState);
        if (idleState && callback != null) {
            callback.onTransitionToIdle();
        }
    }
}