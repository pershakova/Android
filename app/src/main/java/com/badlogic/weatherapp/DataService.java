package com.badlogic.weatherapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

public class DataService extends IntentService {
    private static final String PREDICTION_DATE = "ru.geekbrains.service.DATE";
    static final String PREDICTION_RESULT = "ru.geekbrains.service.RESULT";

    public DataService() {
        super("DataService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String date = intent.getStringExtra(PREDICTION_DATE);
        String result = getDataFromInternet(date);
        sendBrodcast(result);
    }

    public static void startDataService(Context context, String date) {
        Intent intent = new Intent(context, DataService.class);
        intent.putExtra(PREDICTION_DATE, date);
        context.startService(intent);
    }

    public String getDataFromInternet(String date){
        String someJsonFromInternet = "some json from internet using date";
        return someJsonFromInternet;
    }

    private void sendBrodcast(String result) {
        Intent broadcastIntent = new Intent(MainActivity.BROADCAST_ACTION_CALCFINISHED);
        broadcastIntent.putExtra(PREDICTION_RESULT, result);
        sendBroadcast(broadcastIntent);
    }
}



