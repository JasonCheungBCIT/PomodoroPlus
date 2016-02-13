package com.bcit.pomodoro.pomodoroplus;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Jason Cheung on 2016-02-01.
 */
public class TaskView extends RelativeLayout {

    /* -- FIELDS -- */
    protected RelativeLayout taskView;
    protected TextView title, category, duration;
    protected long timeLeft;  // TODO: Investigate holding time-left or just the deadline.
    protected int bgColor;    // Stored in res/values/colors


    /* -- CONSTRUCTOR -- */
    public TaskView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TaskView (Context context, AttributeSet attrs,
                     String title, String category, long timeLeft, int color) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.task_view, this);

        taskView = (RelativeLayout) findViewById(R.id.task_view);

        this.title = (TextView) findViewById(R.id.task_title);
        this.category = (TextView) findViewById(R.id.category);
        this.duration = (TextView) findViewById(R.id.duration);

        this.title.setText(title);
        this.category.setText(category);
        this.duration.setText(Long.toString(timeLeft / 1000 / 60) + " min");
        this.taskView.setBackgroundColor(color);
    }

    public TaskView (Context context, AttributeSet attrs, TaskModel model) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.task_view, this);

        taskView = (RelativeLayout) findViewById(R.id.task_view);

        this.title    = (TextView) findViewById(R.id.task_title);
        this.category = (TextView) findViewById(R.id.category);
        this.duration = (TextView) findViewById(R.id.duration);

        this.title.setText(model.getTitle());
        this.category.setText(model.getCategory());
        this.duration.setText(Long.toString(model.getTimeLeft() / 1000 / 60) + " min");
        this.taskView.setBackgroundColor(model.getBgColor());
    }

    /* -- GETS AND SETS -- */
    // TODO: Doesn't actually update the view
    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getCategory() {
        return category;
    }

    public void setCategory(TextView category) {
        this.category = category;
    }

    public TextView getDuration() {
        return duration;
    }

    public void setDuration(TextView duration) {
        this.duration = duration;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
}

// TODO: this stupid color system.
// Note: Bottom two don't set color.
class ExerciseView extends TaskView {

    public ExerciseView (Context context, AttributeSet attrs,
                     String title, String category, long timeLeft) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.exercise_view, this);

        taskView = (RelativeLayout) findViewById(R.id.task_view);

        this.title = (TextView) findViewById(R.id.task_title);
        this.category = (TextView) findViewById(R.id.category);
        this.duration = (TextView) findViewById(R.id.duration);

        this.title.setText(title);
        this.category.setText(category);
        this.duration.setText(Long.toString(timeLeft / 1000 / 60) + "min");
    }
}

class BreakView extends TaskView {
    public BreakView (Context context, AttributeSet attrs,
                         String title, String category, long timeLeft) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.break_view, this);

        taskView = (RelativeLayout) findViewById(R.id.task_view);

        this.title = (TextView) findViewById(R.id.task_title);
        this.category = (TextView) findViewById(R.id.category);
        this.duration = (TextView) findViewById(R.id.duration);

        this.title.setText(title);
        this.category.setText(category);
        this.duration.setText(Long.toString(timeLeft / 1000 / 60) + "min");
    }
}

