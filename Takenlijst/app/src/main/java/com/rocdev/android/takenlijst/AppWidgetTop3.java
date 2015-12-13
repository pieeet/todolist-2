package com.rocdev.android.takenlijst;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidgetTop3 extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget_top3);

            //klikken op widget titel TextView opent de app
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.titelTextView, pendingIntent);

            //update de UI
            String[] taaknamen = new TakenlijstDB(context).getWidgetTaken(3);
            views.setTextViewText(R.id.taak1TextView,
                    taaknamen[0] == null ? "" : taaknamen[0]);
            views.setTextViewText(R.id.taak2TextView,
                    taaknamen[1] == null ? "" : taaknamen[1]);
            views.setTextViewText(R.id.taak3TextView,
                    taaknamen[2] == null ? "" : taaknamen[2]);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(TakenlijstDB.TAAK_VERANDERD)) {
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            ComponentName provider = new ComponentName(context, AppWidgetTop3.class);
            int[] appWidgetIds = manager.getAppWidgetIds(provider);
            onUpdate(context, manager,appWidgetIds);
        }
    }
}

