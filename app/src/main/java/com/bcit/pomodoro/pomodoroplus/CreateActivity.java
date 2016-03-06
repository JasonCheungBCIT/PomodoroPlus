package com.bcit.pomodoro.pomodoroplus;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

public class CreateActivity extends AppCompatActivity {

    private EditText etTitle, etDuration;
    private int color;
    private Spinner sCategory, sPriority, sColor;
    private ArrayAdapter<String> categoryAdapter, priorityAdapter, colorAdapter;
    private static final String FILE_NAME = "stored_tasks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        final Resources res;
        final String[] categoryArray, colorArray, priorityArray;

        etTitle         = (EditText) findViewById(R.id.etName);
        etDuration      = (EditText) findViewById(R.id.etDuration);
        sCategory       = (Spinner) findViewById(R.id.spinCategory);
        sPriority       = (Spinner) findViewById(R.id.spinPriority);
        sColor          = (Spinner) findViewById(R.id.spinColor);
        res             = getResources();
        categoryArray   = res.getStringArray(R.array.category_arrays);
        colorArray      = res.getStringArray(R.array.color_arrays);
        priorityArray   = res.getStringArray(R.array.priority_arrays);
        categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categoryArray);
        priorityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, priorityArray);
        colorAdapter    = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, colorArray);

        //categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCategory.setAdapter(categoryAdapter);
        sColor.setAdapter(colorAdapter);
        sPriority.setAdapter(priorityAdapter);
    }

    private int findColor() {
        int     color;
        String  selectedColor;

        color           = R.color.taskRed;
        selectedColor   = sColor.getSelectedItem().toString();

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

    public void verifyInfo(View v){
        String  title, sDuration, category;
        long    duration;
        int     color;

        sDuration   = etDuration.getText().toString();
        title       = etTitle.getText().toString();
        category    = sCategory.getSelectedItem().toString();
        duration    = Long.parseLong(sDuration);
        duration    = duration * 1000 * 60;
        color       = findColor();
        /*if(!etTitle.equals("") && !testDuration.equals("")) {
            Toast.makeText(getApplicationContext(), etTitle, Toast.LENGTH_LONG).show();

        }*/

        TaskModel toSave = new TaskModel(title, category, duration, ContextCompat.getColor(getApplicationContext(), color));

        StringBuilder jsonContent = new StringBuilder();
        FileInputStream fis = null;
        FileOutputStream fos = null;
        String hello = "./" + FILE_NAME;
        File file = new File(hello);

        try {
            //CREATING THE FILE IF IT DOESN'T EXIST USING FILEOUTPUT BECAUSE file.createFile() FAILS :'(;
            if(!file.exists()) {
                Toast.makeText(getApplicationContext(), "creating", Toast.LENGTH_SHORT).show();
                fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                fos.close();
            }
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
            fis.close();

            ArrayList<TaskModel> newTm = new ArrayList<>();
            SavePackage save;
            Gson gson = new Gson();
            save = gson.fromJson(jsonContent.toString(), SavePackage.class);
            if(save == null) {
                save = new SavePackage(new ArrayList<TaskModel>());
                Toast.makeText(getApplicationContext(), "again", Toast.LENGTH_SHORT).show();
            }
            save.setTaskModels(toSave);
            //newTm.add(toSave);
            for (TaskModel tm : save.getTaskModels()) {
                newTm.add(tm);
            }

            //jsonContent.append(toSave);
            //SavePackage save = new SavePackage(tm);

            save = new SavePackage(newTm);
            //save.setTaskModels(toSave);
            String content = gson.toJson(save);
            //jsonContent.append(content);

            fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("FILE", "Failed to create file");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("FILE", "Failed to write file");
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Made it", Toast.LENGTH_LONG).show();
    }


}

