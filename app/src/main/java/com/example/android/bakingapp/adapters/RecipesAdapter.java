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
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Recipe;
import com.example.android.bakingapp.holders.RecipeViewHolder;
import com.example.android.bakingapp.interfaces.Listeners;
import com.example.android.bakingapp.utilities.GlideApp;

import java.util.List;

/**
 * Created by beita on 15/05/2018.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    private Context mContext;
    private List<Recipe> mRecipes;
    private Listeners.OnItemClickListener mOnItemClickListener;


    /**
     * Recycler View adapter for recipes list.
     */

    public RecipesAdapter(Context context, List<Recipe> recipes, Listeners.OnItemClickListener onItemClickListener) {
        this.mContext = context;
        this.mRecipes = recipes;
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item_list, parent, false);

        return new RecipeViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, final int position) {
        holder.mRecipeNameTV.setText(mRecipes.get(position).getName());
        holder.mServingsTV.setText(mContext.getString(R.string.servings, mRecipes.get(position).getServings()));

        String recipeImage = mRecipes.get(position).getImage();
        if (!recipeImage.isEmpty()) {
            GlideApp.with(mContext)
                    .load(recipeImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.receta)
                    .into(holder.mRecipeIV);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }


}
