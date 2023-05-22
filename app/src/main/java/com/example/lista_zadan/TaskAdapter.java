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
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    private Context context;
    private int resource;
    public TaskAdapter(Context context,int resource, ArrayList<Task> tasks) {

        super(context, resource, tasks);
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
}
