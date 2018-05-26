package com.example.android.bakingapp.api;

import com.example.android.bakingapp.data.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by beita on 15/05/2018.
 */

interface RecipeApiService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();

}
