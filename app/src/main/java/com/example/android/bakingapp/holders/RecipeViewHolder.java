package com.example.android.bakingapp.holders;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by beita on 17/05/2018.
 */

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.list_recipe_name)
    public TextView mRecipeNameTV;

    @BindView(R.id.list_recipe_servings)
    public TextView mServingsTV;

    @BindView(R.id.recipe_image)
    public AppCompatImageView mRecipeIV;

    public RecipeViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

}
