package com.bcit.pomodoro.pomodoroplus;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Jason Cheung on 2016-02-01.
 */
public class TaskViewerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String FILE_NAME = "stored_tasks";
    private LinearLayout taskHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_viewer);

        // Start Navigation
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // End navigation

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
        for (TaskModel tm : taskModels) {
            taskHolder.addView(new TaskView(getApplicationContext(), null, tm, TaskViewerActivity.this));
        }
    }


    /* -- NAVIGATION -- */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.task_viewer_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_LONG).show();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
