package com.example.android.bakingapp.api;


/**
 * Created by beita on 15/05/2018.
 */

public interface RecipeApiCallback<T> {
    void onResponse(T result);

    void onCancel();
}
