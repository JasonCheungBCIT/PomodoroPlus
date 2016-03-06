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

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CreateActivity extends AppCompatActivity {

    private String etTitle, category;
    private long duration;
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

    public void verifyInfo(View v){
        etTitle = ((EditText)findViewById(R.id.etName)).getText().toString();
        category = sCategory.getSelectedItem().toString();
        String testDuration = ((EditText)findViewById(R.id.etDuration)).getText().toString();
        /*if(!etTitle.equals("") && !testDuration.equals("")) {
            Toast.makeText(getApplicationContext(), etTitle, Toast.LENGTH_LONG).show();

        }*/
       duration = Long.parseLong(((EditText) findViewById(R.id.etDuration)).getText().toString());
        //color as a string but COLORS A FUCKING INTTTT
        //color = Integer.parseInt(sColor.getSelectedItem().toString());

        TaskModel toSave = new TaskModel(etTitle, category,duration, ContextCompat.getColor(getApplicationContext(), R.color.taskRed));

        StringBuilder jsonContent = new StringBuilder();
        FileInputStream fis = null;
        FileOutputStream fos = null;


        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
            fis.close();

            jsonContent.append(toSave);
            SavePackage save;
            Gson gson = new Gson();
            save = gson.fromJson(jsonContent.toString(), SavePackage.class);
            save.setTaskModels(toSave);
            String content = gson.toJson(save);

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

    }
}

