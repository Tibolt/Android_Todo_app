package com.example.lista_zadan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class TaskActivity extends AppCompatActivity {
    private ListView tasksListView;
    private TaskAdapter taskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        init();
        initAdapter();
    }
    private void init() {
        tasksListView = findViewById(R.id.tasksListView);
    }
    private void initAdapter() {
        taskAdapter = new TaskAdapter(getApplicationContext(), R.layout.task_cell, Task.arrayList);
        tasksListView.setAdapter(taskAdapter);
    }
    public void newTask(View view) {
        Intent newTaskIntent = new Intent(this, TaskDetailActivity.class);
        startActivity(newTaskIntent);
    }

    public void finishView(View view){
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        taskAdapter.notifyDataSetChanged();
    }
}