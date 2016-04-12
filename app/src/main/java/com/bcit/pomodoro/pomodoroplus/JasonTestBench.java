package com.bcit.pomodoro.pomodoroplus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Jason Cheung on 2016-02-13.
 */
public class JasonTestBench extends Activity {
    private static final String TAG = "JasonTestBench";
    private static final String FILE_NAME = "stored_tasks";

    private LinearLayout taskHolder;
    private TextView fileDisplay;
    private String[] allFiles;

    private int counter = 0;    // Testing: Files are overwritten if the names are the same.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jason_test_bench);

        taskHolder = (LinearLayout) findViewById(R.id.jason_task_holder);
        fileDisplay = (TextView) findViewById(R.id.file_display);
    }

    private void displayAllFiles() {
        allFiles = getApplicationContext().fileList();
        StringBuilder fatString = new StringBuilder();
        for (String s : allFiles) {
            fatString.append(s + " ");
        }
        fileDisplay.setText(fatString.toString());
    }
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

    public void onCreateFileClick(View view) {
        counter++;

        String FILENAME = FILE_NAME + counter;

        // Generate test data to save
        ArrayList<TaskModel> testModels = generateTestModels(getApplicationContext());
        SavePackage save = new SavePackage(testModels);

        // Serialize test data
        Gson gson = new Gson();
        String content = gson.toJson(save);

        // Save to file
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("FILE", "Failed to create file");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("FILE", "Failed to write file");
            e.printStackTrace();
        }

        displayAllFiles();
    }
*/
    public void onLoadFileClick(View view) {
        String FILENAME = FILE_NAME + counter;
        StringBuilder jsonContent = new StringBuilder();

        // Retrieve content from file
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILENAME);
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

        // Load the object
        for (TaskModel tm : save.getTaskModels()) {
          // taskHolder.addView(new TaskView(getApplicationContext(), null, tm));
        }

        displayAllFiles();
    }
}

