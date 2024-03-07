package com.example.notificationapp;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class Notification extends BroadcastReceiver {
    int notificationID = 1;
    String channelID = "notificationChannel";
    String messageExtra = "messageExtra";
    String titleExtra = "titleExtra";
    @Override
    public void onReceive(Context context, Intent intent) {
        android.app.Notification notification = new NotificationCompat.Builder(context , channelID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(intent.getStringExtra(titleExtra))
                .setContentText(intent.getStringExtra(messageExtra))
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID , notification);
    }
}
