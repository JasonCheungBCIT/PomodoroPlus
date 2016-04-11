package com.bcit.pomodoro.pomodoroplus;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Jordan H on 2016-04-09.
 */
public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){

        Intent result = new Intent(context, TaskViewerActivity.class);
        result.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pending = PendingIntent.getActivity(context, 0, result, PendingIntent.FLAG_UPDATE_CURRENT);
        String message = intent.getStringExtra("content");

        NotificationCompat.Builder  builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.pomo);
        builder.setContentTitle(message + " time is up");
        builder.setContentText("Move on to the next task!");

        builder.setContentIntent(pending);

        Notification test = builder.build();
        NotificationManagerCompat.from(context).notify(0, test);

    }


}
