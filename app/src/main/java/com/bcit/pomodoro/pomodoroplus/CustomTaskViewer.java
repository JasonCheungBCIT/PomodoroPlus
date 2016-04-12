package com.bcit.pomodoro.pomodoroplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormatSymbols;
import java.util.ArrayList;

public class CustomTaskViewer extends AppCompatActivity {

    private LinearLayout            taskHolder;
    private ArrayList<TaskModel>    tasks;
    private String                  fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_task_viewer);

        taskHolder      = (LinearLayout) findViewById(R.id.custom_task_holder);
        Intent intent   = getIntent();
        int day         = intent.getIntExtra("day", 1);
        int month       = intent.getIntExtra("month", 1);

        setTitle("Tasks For " + getMonth(month) + " " + Integer.toString(day));

        fileName = DateHelper.createDateString(
                intent.getIntExtra("day", 1),
                intent.getIntExtra("month", 1),
                intent.getIntExtra("year", 2000)
        );

        tasks = loadDataFromDate(fileName);

        displayAllTasks(tasks, getMonth(month) + " " + Integer.toString(day));
    }

    private ArrayList<TaskModel> loadDataFromDate(String date) {

        StringBuilder jsonContent = new StringBuilder();
        File fileForDate = new File(getApplicationContext().getFilesDir(), date);

        try {
            //CREATING THE FILE IF IT DOESN'T EXIST USING FILEOUTPUT BECAUSE file.createFile() FAILS :'(;
            if(fileForDate.exists()) {

                // Read existing data
                FileInputStream fis = openFileInput(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    jsonContent.append(line);
                }
                fis.close();

                // Convert back to object
                SavePackage save;
                Gson gson = new Gson();
                save = gson.fromJson(jsonContent.toString(), SavePackage.class);

                // return our models
                return save.getTaskModels();
            }
        } catch (FileNotFoundException e) {
            Log.d("FILE", "Failed to create file");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("FILE", "Failed to write file");
            e.printStackTrace();
        }

        return null;
    }

    private void displayAllTasks(ArrayList<TaskModel> taskModels, String date) {

        if(taskModels == null) {
            taskHolder.addView(new TaskView(getApplicationContext(), null, "No Tasks For " + date
                                , "", -1, R.color.colorPrimary));
            return;
        }
        for (TaskModel tm : taskModels) {
            TaskView tmp = new TaskView(getApplicationContext(), null, tm, CustomTaskViewer.this);
            taskHolder.addView(tmp);
        }
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), TaskViewerActivity.class));
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }
}
x