package com.example.lista_zadan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    private Context context;
    private int resource;
    private int tabId;
    private List<Task> originalList;
    private List<Task> filteredList;
    public TaskAdapter(Context context,int resource, ArrayList<Task> tasks) {

        super(context, resource, tasks);
        this.context = context;
        this.resource = resource;
        this.originalList = new ArrayList<>(tasks);
        this.filteredList = new ArrayList<>(tasks);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(convertView == null)
            convertView = inflater.inflate(resource, parent, false);

        if (filteredList.isEmpty()) {
            // If the filteredList is empty, hide the views
            convertView.setVisibility(View.GONE);
            return convertView;
        } else {
            convertView.setVisibility(View.VISIBLE);
        }

        Task task = filteredList.get(position);
        if (task == null) {
            return convertView;
        }

        TextView title = convertView.findViewById(R.id.taskCellTitle);
        TextView date = convertView.findViewById(R.id.taskCellDate);
        CheckBox isDone = convertView.findViewById(R.id.taskCellDone);
        title.setText(task.getTitle());
        date.setText(task.getEndDate());
        isDone.setChecked(task.getDone());

        return convertView;
    }
    public void setTabId(int tabId) {
        this.tabId = tabId;
        applyFilter();
    }
    private void applyFilter() {
        filteredList.clear();
        for (Task task : originalList) {
            if (task.getTabId() == tabId) {
                filteredList.add(task);
            }
        }
        notifyDataSetChanged();
    }
}
