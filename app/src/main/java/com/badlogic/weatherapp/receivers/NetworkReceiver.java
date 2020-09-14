package com.badlogic.weatherapp.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.app.NotificationCompat;

import com.badlogic.weatherapp.R;

public class NetworkReceiver extends BroadcastReceiver {
    private int messageId = 1000;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isOnline(context)){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Broadcast Receiver")
                    .setContentText("Network connection is lost");
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(messageId++, builder.build());
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
