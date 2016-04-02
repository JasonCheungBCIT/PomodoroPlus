package com.bcit.pomodoro.pomodoroplus;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jordan H on 2016-02-08.
 */
public class SettingActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainSettingsFragment()).commit();
/*      DELETE THIS LATER
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
        Toast.makeText(getApplicationContext(), "End", Toast.LENGTH_LONG).show();
        */
    }

    @Override
    protected void onStart(){
        super.onStart();
        setContentView(R.layout.activity_setting);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainSettingsFragment()).commit();

    }

    public static class MainSettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

}
