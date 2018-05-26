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
