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
