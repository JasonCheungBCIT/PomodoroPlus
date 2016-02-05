package com.bcit.pomodoro.pomodoroplus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Jason Cheung on 2016-02-04.
 */
public class DateSelectActivity extends AppCompatActivity {

    private DatePicker cal;
    private FloatingActionButton selectButton, todayButton, tomorrowButton;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.date_select_activity);

        cal = (DatePicker) findViewById(R.id.datePicker);

        selectButton = (FloatingActionButton) findViewById(R.id.select_button);
        todayButton = (FloatingActionButton) findViewById(R.id.today_button);
        tomorrowButton = (FloatingActionButton) findViewById(R.id.tomorrow_button);

    }

    private void printSelectedDate() {
        Toast.makeText(
                getApplicationContext(),
                "Day: " + day + " Month: " + month + " Year: " + year,
                Toast.LENGTH_SHORT
        ).show();
    }

    public void onSelectClick(View view) {
        year    = cal.getYear();
        month   = cal.getMonth();
        day     = cal.getDayOfMonth();
        printSelectedDate();
    }

    public void onTodayClick(View view) {
        final Calendar c = Calendar.getInstance();
        year    = c.get(Calendar.YEAR);
        month   = c.get(Calendar.MONTH);
        day     = c.get(Calendar.DAY_OF_MONTH);
        cal.updateDate(year, month, day);   // Visual update
        printSelectedDate();
    }

    public void onTomorrowClick(View view) {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        year    = c.get(Calendar.YEAR);
        month   = c.get(Calendar.MONTH);
        day     = c.get(Calendar.DAY_OF_MONTH);
        cal.updateDate(year, month, day);   // Visual update
        printSelectedDate();
    }
}
