package com.bcit.pomodoro.pomodoroplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onViewTaskClick(View view) {
        Intent intent = new Intent(getApplicationContext(), TaskViewerActivity.class);
        startActivity(intent);
    }

    public void onCreateTaskClick(View view) {
        Intent intent = new Intent(getApplicationContext(), DateSelectActivity.class);
        startActivity(intent);
    }

    public void onCreateClick(View view) {
        Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
        startActivity(intent);
    }

    public void onSettingClick(View view) {
        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
        startActivity(intent);
    }

    public void onMainFlowClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MainFlow.class);
        startActivity(intent);
    }

    /*
    public void onJasonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), JasonTestBench.class);
        startActivity(intent);
    }*/

}
