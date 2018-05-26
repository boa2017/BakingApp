package com.example.android.bakingapp.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by beita on 17/05/2018.
 */

public class IngredientsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.recipe_details_ingredients)
    public TextView mIngredientsTV;

    public IngredientsViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

    }

}
