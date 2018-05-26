package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.bakingapp.Prefs;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Recipe;
import com.example.android.bakingapp.ui.activities.MainActivity;


/**
 * Created by beita on 19/05/2018.
 */

/**
 * Implementation of Recipe Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId) {

        Recipe recipe = Prefs.loadRecipe(context);
        if (recipe != null) {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients);

            views.setTextViewText(R.id.widget_recipe_name, recipe.getName());

            views.setOnClickPendingIntent(R.id.widget_recipe_name, pendingIntent);

            // Initialize list view
            Intent intent = new Intent(context, RecipeWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            // Bind remote adapter
            views.setRemoteAdapter(R.id.widget_recipe_lv, intent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_recipe_lv);
        }
    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateRecipeWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateRecipeWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality when the widget is created.
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality when the widget is disabled.
    }
}

