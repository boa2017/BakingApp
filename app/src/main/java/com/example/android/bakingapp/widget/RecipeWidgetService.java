package com.example.android.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.Prefs;
import com.example.android.bakingapp.data.models.Recipe;

/**
 * Created by beita on 19/05/2018.
 */


public class RecipeWidgetService extends RemoteViewsService {

    public static void updateWidget(Context context, Recipe recipe) {
        Prefs.saveRecipe(context, recipe);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidget.class));
        RecipeWidget.updateRecipeWidgets(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        return new RemoteViews(getApplicationContext());
    }

}