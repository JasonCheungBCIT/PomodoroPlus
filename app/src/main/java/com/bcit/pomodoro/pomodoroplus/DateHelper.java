package com.bcit.pomodoro.pomodoroplus;

import java.util.Calendar;

/**
 * Created by Jason Cheung on 2016-03-06.
 *
 * Helper class to consolidate how we retrieve and generate date strings.
 */
public class DateHelper {

    public static String getTodayString() {
        final Calendar c = Calendar.getInstance();
        int year    = c.get(Calendar.YEAR);
        int month   = c.get(Calendar.MONTH);
        int day     = c.get(Calendar.DAY_OF_MONTH);
        String today = day + "-" + month + "-" + year;
        return today;
    }

    public static int[] getTodayInt() {
        final Calendar c = Calendar.getInstance();
        int year    = c.get(Calendar.YEAR);
        int month   = c.get(Calendar.MONTH);
        int day     = c.get(Calendar.DAY_OF_MONTH);
        int[] today = {day, month, year};
        return today;
    }

    public static String createDateString(int day, int month, int year) {
        return day + "-" + month + "-" + year;
    }

}
