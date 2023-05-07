package com.example.lista_zadan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {
    public TaskAdapter(Context context, ArrayList<Task> tasks) {

        super(context, 0, tasks);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task task = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_cell, parent, false);

        TextView title = convertView.findViewById(R.id.taskCellTitle);
        TextView date = convertView.findViewById(R.id.taskCellDate);
        TextView isDone = convertView.findViewById(R.id.taskCellDone);

        title.setText(task.getTitle());
        date.setText(task.getEndDate().toString());
        //return super.getView(position, convertView, parent);
        return convertView;
    }
}
