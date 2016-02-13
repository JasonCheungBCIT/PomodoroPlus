package com.bcit.pomodoro.pomodoroplus;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Jason Cheung on 2016-02-01.
 */
public class TaskViewerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        LinearLayout taskHolder = (LinearLayout) findViewById(R.id.task_holder);

        ArrayList<TaskModel> testModels = generateTestModels(getApplicationContext());

        for (TaskModel tm : testModels) {
            taskHolder.addView(new TaskView(getApplicationContext(), null, tm));
        }
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
