package com.bcit.pomodoro.pomodoroplus;

import java.util.ArrayList;

/**
 * Wraps our models to allow serialization.
 */
class SavePackage {

    private ArrayList<TaskModel> taskModels;
    private int completedTasks = 0;

    SavePackage(ArrayList<TaskModel> models) {
        taskModels = models;
        completedTasks = 0;
    }

    SavePackage(ArrayList<TaskModel> models, int completed) {
        this.taskModels     = models;
        this.completedTasks = completed;
    }

    public void incrementCompletedTasks() {
        completedTasks++;
    }

    public ArrayList<TaskModel> getTaskModels() { return taskModels; }
    public int getCompletedTasks() { return completedTasks; }
    public void setTaskModels(TaskModel t){
        taskModels.add(t);
    }
    public void setCompletedTasks(int n) { completedTasks = n; }
}
