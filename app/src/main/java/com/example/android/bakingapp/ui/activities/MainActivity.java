package com.example.android.bakingapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Recipe;
import com.example.android.bakingapp.ui.fragments.RecipeFragment;

/**
 * Created by beita on 17/05/2018.
 */

public class MainActivity extends AppCompatActivity implements RecipeFragment.OnRecipeClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        Intent intent = new Intent(this, RecipeFullStepsActivity.class);
        intent.putExtra(RecipeFullStepsActivity.RECIPE_KEY, recipe);
        startActivity(intent);
    }

}
