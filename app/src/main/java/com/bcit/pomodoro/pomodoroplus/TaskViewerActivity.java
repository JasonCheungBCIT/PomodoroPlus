package com.bcit.pomodoro.pomodoroplus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Jason Cheung on 2016-02-01.
 */
public class TaskViewerActivity extends AppCompatActivity {

    private final String FILE_NAME = "stored_tasks";
    private LinearLayout taskHolder;
    private ModifyTasks modify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_viewer);

        // Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        // setSupportActionBar(toolbar);

        taskHolder = (LinearLayout) findViewById(R.id.task_holder);
        // getDataModels();
        loadTasks(retrieveTasks());

        /*
        ArrayList<TaskModel> testModels = generateTestModels(getApplicationContext());

        for (TaskModel tm : testModels) {
            taskHolder.addView(new TaskView(getApplicationContext(), null, tm));
        }*/
    }

    /* -- DEBUG -- */
    // Currently doesn't generate any execercise / break models
    /*
    private static ArrayList<TaskModel> generateTestModels(Context context) {

        ArrayList<TaskModel> models = new ArrayList<>();

        models.add(new TaskModel("Android Dev", "Science", 15 * 60 * 1000,
                ContextCompat.getColor(context, R.color.taskRed)));
        models.add(new TaskModel("Literature", "Arts", 15 * 60 * 1000,
                ContextCompat.getColor(context, R.color.taskOrange)));
        models.add(new TaskModel("Push-ups!", "Exercise", 5 * 60 * 1000,
                ContextCompat.getColor(context, R.color.taskYellow)));
        models.add(new TaskModel("Project UI Design", "Creative", 15 * 60 * 1000,
                ContextCompat.getColor(context, R.color.taskGreen)));
        models.add(new TaskModel("Relax", "Break", 30 * 60 * 1000,
                ContextCompat.getColor(context, R.color.taskBlue)));
        models.add(new TaskModel("Foo", "Bar", 15 * 60 * 1000,
                ContextCompat.getColor(context, R.color.taskIndigo)));

        return models;
    }

    private void getDataModels(){
        StringBuilder jsonContent = new StringBuilder();
        File file = new File(getApplicationContext().getFilesDir(), FILE_NAME);

        if(!file.exists()) {
            taskHolder.addView(new TaskView(getApplicationContext(), null,
                    new TaskModel("No Tasks Assigned Yet!", "", 0, R.color.colorPrimary), TaskViewerActivity.this));
            return;
        }

        // Retrieve content from file
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            Log.d("FILE", "Failed to create file");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("FILE", "Failed to write file");
            e.printStackTrace();
        }

        // Convert back to object
        SavePackage save;
        Gson gson = new Gson();
        save = gson.fromJson(jsonContent.toString(), SavePackage.class);

        for (TaskModel tm : save.getTaskModels()) {
            taskHolder.addView(new TaskView(getApplicationContext(), null, tm, TaskViewerActivity.this));
        }
    }
*/
    /* -- LOGIC -- */
    private ArrayList<TaskModel> retrieveTasks() {
        StringBuilder jsonContent = new StringBuilder();

        // Retrieve content from file
        FileInputStream fis = null;
        try {
            fis = openFileInput(DateHelper.getTodayString());
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            Log.d("FILE", "Failed to create file");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("FILE", "Failed to write file");
            e.printStackTrace();
        }

        // Convert back to object
        try{
            SavePackage save;
            Gson gson = new Gson();
            save = gson.fromJson(jsonContent.toString(), SavePackage.class);

            return save.getTaskModels();

        }catch(NullPointerException e){
            Toast.makeText(getApplicationContext(), "You do not have any tasks!", Toast.LENGTH_LONG);
            return null;
        }
    }

    private void loadTasks(ArrayList<TaskModel> taskModels) {
        if(taskModels == null){
            return;
        }
        ArrayList<TaskModel> temp = new ArrayList<TaskModel>();
        for(TaskModel t: taskModels){
            temp.add(t);
        }
        modify = new ModifyTasks(temp);
        modify.splitTasks(temp);
        temp = modify.returnTasks();
        modify.sortTasks(temp);
        temp = modify.returnTasks();
        modify.insertBreaks(temp);
        temp = modify.returnTasks();
        modify.setNotifications(temp, getApplicationContext());
        temp = modify.returnTasks();

        for (TaskModel tm : temp) {
            taskHolder.addView(new TaskView(getApplicationContext(), null, tm, TaskViewerActivity.this));
        }
    }



}
