package com.bcit.pomodoro.pomodoroplus;

/**
 * Created by Jason Cheung on 2016-02-13.
 */
public class TaskModel {

    private String title, category;
    private String priority;
    private long timeLeft;  // TODO: Investigate holding time-left or just the deadline.
    private int bgColor;    // Note: Actual color value.
    private boolean alive = true;

    public TaskModel(String title, String category, long duration, int color, String priority) {
        this.title    = title;
        this.category = category;
        this.timeLeft = duration;
        this.bgColor  = color;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setAlive(boolean live){this.alive  = live;}

    public boolean getAlive(){return alive;}

    public String getPriority(){ return priority; }
}
