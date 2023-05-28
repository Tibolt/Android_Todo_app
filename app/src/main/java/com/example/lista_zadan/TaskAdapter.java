package com.example.lista_zadan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    private Context context;
    private int resource;
    private ArrayList<Task> tasks;
    public TaskAdapter(Context context,int resource, ArrayList<Task> tasks) {

        super(context, resource, tasks);
        this.tasks = tasks;
        this.context = context;
        this.resource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(convertView == null)
            convertView = inflater.inflate(resource, parent, false);


        Task task = getItem(position);
        TextView title = convertView.findViewById(R.id.taskCellTitle);
        TextView date = convertView.findViewById(R.id.taskCellDate);
        TextView tabName = convertView.findViewById(R.id.tabName);
        TextView isDone = convertView.findViewById(R.id.taskCellDone);
        title.setText(task.getTitle());
        date.setText(task.getEndDate());
        tabName.setText(task.getTabName());
        //isDone.setChecked(task.getDone());

        if (task.getDone()) {
            isDone.setText("#");
            isDone.setTextColor(ContextCompat.getColor(context, R.color.green));
        }
        else {
            isDone.setText("o");
            isDone.setTextColor(ContextCompat.getColor(context, R.color.red));
        }

        return convertView;
    }

    public void sortByTitle() {
        // Sort by title
        tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                String title1 = task1.getTitle();
                String title2 = task2.getTitle();
                // Sort in ascending order: alphabetically by title
                return title1.compareToIgnoreCase(title2);
            }
        });
        notifyDataSetChanged();
    }
    public void sortByDone() {
        // Sort by done status
        tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                boolean isDone1 = task1.getDone();
                boolean isDone2 = task2.getDone();
                // Sort in descending order: done tasks first, undone tasks next
                return Boolean.compare(isDone2, isDone1);
            }
        });
        notifyDataSetChanged();
    }
    public void sortByTab() {
        // Sort by TAB_NAME
        tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                String title1 = task1.getTabName();
                String title2 = task2.getTabName();

                // Handle null values
                if (title1 == null && title2 == null) {
                    return 0;
                } else if (title1 == null) {
                    return 1; // title1 is null, so it should be considered greater (placed at the bottom)
                } else if (title2 == null) {
                    return -1; // title2 is null, so it should be considered greater (placed at the bottom)
                } else {
                    // Sort in ascending order: alphabetically by TAB_NAME
                    return title1.compareToIgnoreCase(title2);
                }
            }
        });
        notifyDataSetChanged();
    }
}
