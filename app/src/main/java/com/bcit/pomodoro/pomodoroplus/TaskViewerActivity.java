package com.bcit.pomodoro.pomodoroplus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private SavePackage toSave;
    private ArrayList<TaskModel> currentModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_viewer);

        // Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        // setSupportActionBar(toolbar);

        taskHolder = (LinearLayout) findViewById(R.id.task_holder);
        // getDataModels();
        SavePackage save = retrieveSave();
        loadTasks(save.getTaskModels(), save.getCompletedTasks());

        Intent intent = getIntent();
        Log.d("DEBUG", String.valueOf(intent.getBooleanExtra("next-task-flag", false)));

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
    private SavePackage retrieveSave() {
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
            Gson gson = new Gson();
            toSave = gson.fromJson(jsonContent.toString(), SavePackage.class);

            if (getIntent().getBooleanExtra("next-task-flag", false)) {
                toSave.incrementCompletedTasks();
            }

            return toSave; // .getTaskModels();

        }catch(NullPointerException e){
            Toast.makeText(getApplicationContext(), "You do not have any tasks!", Toast.LENGTH_LONG);
            return null;
        }
    }

    private void loadTasks(ArrayList<TaskModel> taskModels, int completed) {
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

        int i = 0;
        while (i < completed) {
            Log.d("DEBUG", "Size of temp:" + temp.size() + " i: " + i);
            temp.remove(0);
            i++;
        }

        currentModels = temp;

        modify.setNotifications(temp, getApplicationContext());
        temp = modify.returnTasks();

        for (TaskModel tm : temp) {
            taskHolder.addView(new TaskView(getApplicationContext(), null, tm, TaskViewerActivity.this));
        }
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FileInputStream     fis = null;
        FileOutputStream fos = null;
        StringBuilder       jsonContent;
        File                file;

        jsonContent = new StringBuilder();
        file        = new File(getApplicationContext().getFilesDir(), DateHelper.getTodayString());

        try {
            if(checkEmpty(currentModels)){
                //File toDelete = new File(fileName);
                if(file.exists())
                    file.delete();
                Toast.makeText(getApplicationContext(), "You do not have any tasks to add!", Toast.LENGTH_LONG).show();
                return;
            }

            Log.d("Size ", String.valueOf(currentModels.size()));

            //CREATING THE FILE IF IT DOESN'T EXIST USING FILEOUTPUT BECAUSE file.createFile() FAILS :'(;
            Log.d("TaskViewerActivity", "File does not exist, creating");

            // Create save package
            SavePackage save = new SavePackage(toSave.getTaskModels(), toSave.getCompletedTasks());

            // Serialize new save package
            Gson gson = new Gson();
            String content = gson.toJson(save);

            // Create file
            fos = openFileOutput(DateHelper.getTodayString(), Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            Log.d("FILE", "Failed to create file");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("FILE", "Failed to write file");
            e.printStackTrace();
        } catch (IllegalStateException e){
            Log.d("Update", "Failed to update task arraylist");
            e.printStackTrace();
        }
    }

    private boolean checkEmpty(ArrayList<TaskModel> temp){
        for(TaskModel t: temp){
            if(t.getAlive()){
                return false;
            }
        }
        return true;
    }
}
