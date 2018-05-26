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

package com.example.android.bakingapp.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Ingredients;
import com.example.android.bakingapp.data.models.Recipe;
import com.example.android.bakingapp.holders.IngredientsViewHolder;
import com.example.android.bakingapp.holders.StepsViewHolder;
import com.example.android.bakingapp.interfaces.Listeners;

import java.util.Locale;

/**
 * Created by beita on 15/05/2018.
 */

public class SingleRecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Recipe mRecipe;
    private Listeners.OnItemClickListener mOnItemClickListener;

    public SingleRecipeAdapter(Recipe recipe, Listeners.OnItemClickListener onItemClickListener) {
        this.mRecipe = recipe;
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) { // Ingredients
            return new IngredientsViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredients_list_item, parent, false));
        } else { // Steps
            return new StepsViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.step_list_item, parent, false));
        }

    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof IngredientsViewHolder) {
            IngredientsViewHolder viewHolder = (IngredientsViewHolder) holder;

            StringBuilder ingValue = new StringBuilder();
            for (int i = 0; i < mRecipe.getIngredients().size(); i++) {
                Ingredients ingredients = mRecipe.getIngredients().get(i);
                ingValue.append(String.format(Locale.getDefault(), "• %s (%d %s)", ingredients.getIngredient(), ingredients.getQuantity(), ingredients.getMeasure()));
                if (i != mRecipe.getIngredients().size() - 1)
                    ingValue.append("\n");
            }

            viewHolder.mIngredientsTV.setText(ingValue.toString());
        } else if (holder instanceof StepsViewHolder) {
            StepsViewHolder viewHolder = (StepsViewHolder) holder;
            viewHolder.mTvStepOrder.setText(String.valueOf(position - 1) + ".");
            viewHolder.mTvStepName.setText(mRecipe.getSteps().get(position - 1).getShortDescription());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(position - 1);
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        else
            return 1;
    }

    @Override
    public int getItemCount() {
        return mRecipe.getSteps().size() + 1;
    }
}
