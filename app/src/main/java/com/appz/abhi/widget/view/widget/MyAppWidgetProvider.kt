package com.appz.abhi.widget.view.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.appz.abhi.widget.R
import com.appz.abhi.widget.model.ApiInterface
import com.appz.abhi.widget.view.activity.MainActivity
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

class MyAppWidgetProvider : AppWidgetProvider() {


    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Log message
        Log.e(TAG, "Widget: onUpdate")

        // Perform this loop procedure for each App Widget that belongs to this provider
        appWidgetIds.forEach { appWidgetId ->

            val views = RemoteViews(context.packageName, R.layout.widget_layout)

            // Create an Intent to launch MainActivity
            val pendingIntent: PendingIntent = Intent(context, MainActivity::class.java)
                .let { intent ->
                    PendingIntent.getActivity(context, 0, intent, 0)
                }

            views.apply {

                val service = ApiInterface.create()
                val call: Call<JsonObject> = service.fetchRate()

                call.enqueue(object : retrofit2.Callback<JsonObject> {

                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>
                    ) {
                        Log.e(TAG, "Call inside views")
                        val responseBody = response.body()
                        // Log.e(TAG, "Response to string : ${responseBody.toString()}")
                        // Log.e(TAG, "Text: " + responseBody!!.get("text").toString())
                        // rate = responseBody!!.get("INR").asJsonObject.get("last").toString()
                        setTextViewText(
                            R.id.widget_layout_number_text_view,
                            responseBody!!.get
                                ("number").toString()
                        )
                        setTextViewText(
                            R.id.widget_layout_text_text_view, responseBody
                                .get("text").toString()
                        )
                    }

                    override fun onFailure(call: Call<JsonObject>, throwable: Throwable) {
                        Log.e(TAG, "Call inside views throwable: " + throwable.message)
                    }
                })
            }

            val service = ApiInterface.create()
            val call: Call<JsonObject> = service.fetchRate()

            call.enqueue(object : retrofit2.Callback<JsonObject> {

                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>
                ) {
                    val responseBody = response.body()
                    // Log.e(TAG, "Response to string : ${responseBody.toString()}")
                    // Log.e(TAG, "Text: " + responseBody!!.get("text").toString())
                    // rate = responseBody!!.get("INR").asJsonObject.get("last").toString()
                    views.apply {
                        Log.e(TAG, "Views inside call")
                        setTextViewText(
                            R.id.widget_layout_number_text_view,
                            responseBody!!.get
                                ("number").toString()
                        )
                        setTextViewText(
                            R.id.widget_layout_text_text_view, responseBody
                                .get("text").toString()
                        )
                    }

                    // Tell the AppWidgetManager to perform an update on the current app widget
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }

                override fun onFailure(call: Call<JsonObject>, throwable: Throwable) {
                    Log.e(TAG, "Throwable: " + throwable.message)
                }
            })

            // Get the layout for the App Widget and attach an on-click listener
            views.apply {
                setOnClickPendingIntent(R.id.widget_layout_title_text_view, pendingIntent)
            }

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }


    companion object {


        val TAG: String = MyAppWidgetProvider::class.java.simpleName
    }
}
