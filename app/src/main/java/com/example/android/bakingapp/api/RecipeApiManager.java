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

