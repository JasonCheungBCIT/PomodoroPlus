package com.bcit.pomodoro.pomodoroplus;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onViewTaskClick(View view) {
        Intent intent = new Intent(getApplicationContext(), TaskViewerActivity.class);
        startActivity(intent);
    }

    public void onCreateTaskClick(View view) {
        Intent intent = new Intent(getApplicationContext(), DateSelectActivity.class);
        startActivity(intent);
    }

    public void onCreateClick(View view) {
        //Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
        //startActivity(intent);
        NotificationCompat.Builder  builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.pomo);
        builder.setContentTitle("Notification test");
        builder.setContentText("It's time to take a break");

        Intent result = new Intent(this, TaskViewerActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(result);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        Notification test = builder.build();
        NotificationManagerCompat.from(this).notify(0, test);
     //   Toast.makeText(getApplicationContext(), "End", Toast.LENGTH_LONG).show();
    }

    public void onSettingClick(View view) {
        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
        startActivity(intent);
    }

    public void onMainFlowClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MainFlow.class);
        startActivity(intent);
    }

    /*
    public void onJasonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), JasonTestBench.class);
        startActivity(intent);
    }*/

}
