package com.bcit.pomodoro.pomodoroplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.File;
import java.util.Calendar;

/**
 * Created by Jason Cheung on 2016-03-06.
 *
 * This is how the app is supposed to be used on launch.
 *
 * check if existing task for today
 *      yes > view tasks
 *      no > select date > create tasks > re-check.
 */
public class MainFlow extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if a file exists for today
        String fileName = DateHelper.getTodayString();
        File file = new File(getApplicationContext().getFilesDir(), fileName);

        if (file.exists()) {
            // tasks exist for this date, show them
            startActivity(new Intent(this, TaskViewerActivity.class));
        } else {
            // No tasks for this date, go to create tasks
            startActivity(new Intent(this, DateSelectActivity.class));
        }

    }
}
