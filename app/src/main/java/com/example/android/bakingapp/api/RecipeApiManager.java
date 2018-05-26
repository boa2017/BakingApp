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

package com.example.android.bakingapp.api;

import com.example.android.bakingapp.Constants;
import com.example.android.bakingapp.data.models.Recipe;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by beita on 15/05/2018.
 */

public final class RecipeApiManager implements Serializable {

    private static volatile RecipeApiManager sharedInstance = new RecipeApiManager();
    private RecipeApiService recipeApiService;

    private RecipeApiManager() {
        if (sharedInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.RECIPES_API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        recipeApiService = retrofit.create(RecipeApiService.class);
    }

    public static RecipeApiManager getInstance() {
        if (sharedInstance == null) {
            synchronized (RecipeApiManager.class) {
                if (sharedInstance == null) sharedInstance = new RecipeApiManager();
            }
        }

        return sharedInstance;
    }

    public void getRecipes(final RecipeApiCallback<List<Recipe>> recipeApiCallback) {
        recipeApiService.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipeApiCallback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                if (call.isCanceled()) {
                    Logger.e("Request cancelled");
                    recipeApiCallback.onCancel();
                } else {
                    Logger.e(t.getMessage());
                    recipeApiCallback.onResponse(null);
                }
            }
        });
    }

}

