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
