package com.bcit.pomodoro.pomodoroplus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Jordan H on 2016-04-09.
 */
public class ModifyTasks {
    ArrayList<TaskModel> tasks;
    ModifyTasks(ArrayList<TaskModel> t){
        tasks = t;
    }

    public void splitTasks(ArrayList<TaskModel> temp){
        ArrayList<TaskModel> end = new ArrayList<TaskModel>();
        for(TaskModel t : temp){
            int mins = (int)t.getTimeLeft() / 1000 / 60;
            if(mins/25 >= 1 && mins/25 != 0){
                int num25 = mins/25;
                for(int i = 0; i < num25; i++){
                    if(mins % 25 == 0 && i + 1 == num25){
                        break;
                    }
                    // public TaskModel(String title, String category, long duration, int color)
                    TaskModel split = new TaskModel(t.getTitle(), t.getCategory(), 1000 * 60 * 25, t.getBgColor(), t.getPriority());
                    split.setAlive(t.getAlive());
                    end.add(split);

                }
                Log.d("END", "End of loop");
            }
            if(mins%25 != 0){
                TaskModel remainder = new TaskModel(t.getTitle(), t.getCategory(), 1000 * 60 *(mins % 25), t.getBgColor(), t.getPriority());
                remainder.setAlive(t.getAlive());
                end.add(remainder);
            }else{
                TaskModel whole = new TaskModel(t.getTitle(), t.getCategory(), 1000 * 60 *25, t.getBgColor(), t.getPriority());
                whole.setAlive(t.getAlive());
                end.add(whole);
            }
        }
        tasks = end;
    }

    public void sortTasks(ArrayList<TaskModel> temp){
        ArrayList<TaskModel> extreme = new ArrayList<TaskModel>();
        ArrayList<TaskModel> high = new ArrayList<TaskModel>();
        ArrayList<TaskModel> medium = new ArrayList<TaskModel>();
        ArrayList<TaskModel> low = new ArrayList<TaskModel>();

        ArrayList<TaskModel> sorted = new ArrayList<TaskModel>();
        Random r = new Random();
        for(TaskModel t : temp){
            if(t.getPriority().equals("Extremely High"))
                extreme.add(t);
            else if(t.getPriority().equals("High"))
                high.add(t);
            else if(t.getPriority().equals("Medium"))
                medium.add(t);
            else
                low.add(t);
        }
        Log.d("Extreme " , String.valueOf(extreme.size()));
        Log.d("High " , String.valueOf(high.size()));
        Log.d("Medium ", String.valueOf(medium.size()));
        Log.d("Low ", String.valueOf(low.size()));
        for(int i =0; i < temp.size(); i++){
            if(extreme.size() != 0){
                TaskModel e = extreme.get(r.nextInt(extreme.size()));
                sorted.add(e);
                extreme.remove(e);
            }else if(high.size() != 0){
                TaskModel h = high.get(r.nextInt(high.size()));
                sorted.add(h);
                high.remove(h);
            }else if(medium.size() != 0){
                TaskModel m = medium.get(r.nextInt(medium.size()));
                sorted.add(m);
                medium.remove(m);
            }else{
                TaskModel l = low.get(r.nextInt(low.size()));
                sorted.add(l);
                low.remove(l);
            }
        }
        tasks = sorted;
    }

    public void insertBreaks(ArrayList<TaskModel> temp){
        TaskModel Break = new TaskModel("Quick Break", "Break", 1000*60*10, Color.WHITE, "break");
        ArrayList<TaskModel> breaks = new ArrayList<TaskModel>();
        TaskModel last = temp.get(temp.size() - 1);
        for(TaskModel t: temp){
            breaks.add(t);
            if(t != last){
                breaks.add(Break);
            }
        }
        tasks = breaks;
    }


    public void setNotifications(ArrayList<TaskModel> temp, Context context){
        // int counter = 0;
        TaskModel t = temp.get(0);

        //Add counter and t.getTimeLeft()(in some fucked up value);
        int toAdd = (int)t.getTimeLeft() / 1000 / 60;
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(context, NotificationReceiver.class);
        intent.putExtra("content", t.getTitle());
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, (toAdd));
        long time = c.getTimeInMillis();
        manager.set(AlarmManager.RTC_WAKEUP, time, alarmIntent);
        //set task for deletion
        final TaskModel toDelete = t;
        Handler timerHandler = new Handler();
        Runnable timerRunnable = new Runnable(){
            @Override
            public void run(){
                deleteTask(tasks, toDelete);
            }
        };
        timerHandler.postDelayed(timerRunnable, time - 100);

        // counter += toAdd;
    }

    public void deleteTask(ArrayList<TaskModel> temp, TaskModel toDelete){
        Log.d("DELET TASK", "BEING RUN");
        for(TaskModel t: temp){
            if(t == toDelete){
                temp.remove(t);
                break;
            }
        }
        tasks = temp;
    }


    public ArrayList<TaskModel> returnTasks(){
        return tasks;
    }
}
