package com.example.android.bakingapp.widget;

import android.content.Context;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.Prefs;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Recipe;

/**
 * Created by beita on 19/05/2018.
 */


public class RemoteViews implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private Recipe recipe;

    public RemoteViews(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        recipe = Prefs.loadRecipe(mContext);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipe.getIngredients().size();
    }

    @Override
    public android.widget.RemoteViews getViewAt(int position) {
        android.widget.RemoteViews row = new android.widget.RemoteViews(mContext.getPackageName(), R.layout.widget_ingredients_item_list);

        row.setTextViewText(R.id.widget_ingredient_name, recipe.getIngredients().get(position).getIngredient());

        return row;
    }

    @Override
    public android.widget.RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
