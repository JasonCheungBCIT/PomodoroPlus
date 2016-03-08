package com.bcit.pomodoro.pomodoroplus;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import petrov.kristiyan.colorpicker.ColorPicker;

public class CreateActivity extends AppCompatActivity {
    private static final String TAG = "CreateActivity";

    private Context             context;
    private EditText             etTitle, etDuration;
    private int                  color;
    private Spinner              sCategory, sPriority; // sColor;
    private ArrayAdapter<String> categoryAdapter, priorityAdapter, colorAdapter;
    private static final String  FILE_NAME = "stored_tasks";
    private String              fileName;
    private ColorPicker         colorPicker;
    private LinearLayout taskHolder;

    private ArrayList<TaskModel> tasks;
    private int                  selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_2);

        final Resources res;
        final String[] categoryArray, colorArray, priorityArray;
        context = this;

        etTitle         = (EditText) findViewById(R.id.etName);
        etDuration      = (EditText) findViewById(R.id.etDuration);
        sCategory       = (Spinner) findViewById(R.id.spinCategory);
        sPriority       = (Spinner) findViewById(R.id.spinPriority);
        // sColor          = (Spinner) findViewById(R.id.spinColor);
        res             = getResources();
        categoryArray   = res.getStringArray(R.array.category_arrays);
        colorArray      = res.getStringArray(R.array.color_arrays);
        priorityArray   = res.getStringArray(R.array.priority_arrays);
        categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categoryArray);
        priorityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, priorityArray);
        colorAdapter    = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, colorArray);

        taskHolder      = (LinearLayout) findViewById(R.id.task_holder);

        //categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCategory.setAdapter(categoryAdapter);
        // sColor.setAdapter(colorAdapter);
        sPriority.setAdapter(priorityAdapter);

        colorPicker = new ColorPicker(this);
        colorPicker.setRoundButton(true);
        colorPicker.setColors(
                ContextCompat.getColor(context, R.color.taskRed),
                ContextCompat.getColor(context, R.color.taskOrange),
                ContextCompat.getColor(context, R.color.taskYellow),
                ContextCompat.getColor(context, R.color.taskGreen),
                ContextCompat.getColor(context, R.color.taskBlue),
                ContextCompat.getColor(context, R.color.taskIndigo),
                ContextCompat.getColor(context, R.color.taskViolet));

        colorPicker.setFastChooser(new ColorPicker.OnFastChooseColorListener() {
            @Override
            public void setOnFastChooseColorListener(int position, int color) {
                Toast.makeText(context, "color:" + color, Toast.LENGTH_SHORT).show();
                selectedColor = color;
                colorPicker.setDefaultColor(color);
                colorPicker.dismissDialog();
            }
        });

        /*
        colorPicker.setFastChooser(new ColorPicker.OnFastChooseColorListener() {
            @Override
            public void setOnFastChooseColorListener(int position, int color) {
                colorPicker.dismissDialog();
            }
        }).setNegativeButton("DEFAULT",new ColorPicker.OnButtonListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEFAULT","default");
            }
        }).setPositiveButton("CANCEL", new ColorPicker.OnButtonListener() {
            @Override
            public void onClick(View v) {
                Log.d("CANCEL","cancel");
            }
        }).setDefaultColor(Color.parseColor("#f84c44")).setColumns(5).setDialogFullHeight().show();
*/

        // Retrieve date information
        fileName = DateHelper.createDateString(
                getIntent().getIntExtra("day", 1),
                getIntent().getIntExtra("month", 1),
                getIntent().getIntExtra("year", 2000)
        );

        // Load any data from this date
        tasks = loadDataFromDate(fileName);
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        displayAllTasks(tasks);
    }

    private ArrayList<TaskModel> loadDataFromDate(String date) {

        StringBuilder jsonContent = new StringBuilder();
        File fileForDate = new File(getApplicationContext().getFilesDir(), date);

        try {
            //CREATING THE FILE IF IT DOESN'T EXIST USING FILEOUTPUT BECAUSE file.createFile() FAILS :'(;
            if(fileForDate.exists()) {
                Log.d(TAG, "Existing file found for date");

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

        Log.d(TAG, "No existing file for date");
        return null;
    }

    private void displayAllTasks(ArrayList<TaskModel> taskModels) {
        for (TaskModel tm : taskModels) {
            taskHolder.addView(new TaskView(getApplicationContext(), null, tm));
        }
    }

    private int findColor() {
        int     color;
        String  selectedColor;

        color           = R.color.taskRed;
        selectedColor   = "Asf";//sColor.getSelectedItem().toString();

        switch(selectedColor) {
            case "Red":
                color = R.color.taskRed;
                break;
            case "Orange":
                color = R.color.taskOrange;
                break;
            case "Yellow":
                color = R.color.taskYellow;
                break;
            case "Green":
                color = R.color.taskGreen;
                break;
            case "Blue":
                color = R.color.taskBlue;
                break;
            case "Indigo":
                color = R.color.taskIndigo;
                break;
            case "Violet":
                color = R.color.taskViolet;
                break;
        }

        return color;
    }

    public void verifyInfo(View v) {
        String              title, sDuration, category;
        long                duration;
        int                 color;
        TaskModel           toSave;


        sDuration   = etDuration.getText().toString();
        title       = etTitle.getText().toString();
        category    = sCategory.getSelectedItem().toString();
        duration    = (sDuration.equals("")) ? 0 : Long.parseLong(sDuration);
        duration    = duration * 1000 * 60;
        toSave      = new TaskModel(title, category, duration, selectedColor);

        if(title.equals("") || duration == 0) {
            Toast.makeText(getApplicationContext(), "Please enter all fields!", Toast.LENGTH_LONG).show();
            return;
        }

        tasks.add(toSave);
        taskHolder.addView(new TaskView(getApplicationContext(), null, toSave), 0);

    }

    public void onColorClick(View view) {
        colorPicker.show();
    }

    public void onDoneClick(View view) {
        FileInputStream     fis = null;
        FileOutputStream    fos = null;
        StringBuilder       jsonContent;
        File                file;

        jsonContent = new StringBuilder();
        file        = new File(getApplicationContext().getFilesDir(), fileName);

        try {
            //CREATING THE FILE IF IT DOESN'T EXIST USING FILEOUTPUT BECAUSE file.createFile() FAILS :'(;
            Log.d(TAG, "File does not exist, creating");

            // Create save package
            SavePackage save = new SavePackage(tasks);

            // Serialize new save package
            Gson gson = new Gson();
            String content = gson.toJson(save);

            // Create file
            fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            Log.d("FILE", "Failed to create file");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("FILE", "Failed to write file");
            e.printStackTrace();
        }

        Toast.makeText(this, "Task created!", Toast.LENGTH_SHORT).show();

        // startActivity(new Intent(this, MainActivity.class));


        startActivity(new Intent(this, MainFlow.class));
    }
}

