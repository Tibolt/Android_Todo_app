package com.example.lista_zadan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class TaskActivity extends AppCompatActivity {
    private ListView tasksListView;
    private TaskAdapter taskAdapter;
    private TextView tabTitle;
    private int tabID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        init();
        initAdapter();
        setTitle();
    }
    private void init() {
        tasksListView = findViewById(R.id.tasksListView);
        tabTitle = findViewById(R.id.tabName);

        Intent pIntent = getIntent();
        tabID = pIntent.getIntExtra("currentTabID", -1);
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
    private void setTitle() {
        Tab selectedTab = Tab.getSelectedID(tabID);

        if (selectedTab != null) {
            tabTitle.setText(selectedTab.getTitle());
        }
    }

    public void editTabIntent(View view) {
//        Intent pIntent = getIntent();
//        tabID = pIntent.getIntExtra("currentTabID", -1);

        Intent tabIntent = new Intent(this, TabDetailActivity.class);
        tabIntent.putExtra("editTab", tabID);
        startActivity(tabIntent);
        finish(); // finish to back to Tab screen
    }
}