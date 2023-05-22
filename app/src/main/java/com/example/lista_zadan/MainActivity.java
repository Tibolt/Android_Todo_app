package com.example.lista_zadan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ListView tasksListView;
    public TaskAdapter taskAdapter;
    private Spinner tabSpinner;
    public ArrayList<String> tabArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loadDB();
        initAdapter();
        initSpinerAdapter();
        setOnClickListener();
    }


    private void init() {
        tasksListView = findViewById(R.id.tasksListView);
        tabSpinner = findViewById(R.id.tabSpinner);
        //create items for debugging
        //Task.arrayList.add(new Task(Task.arrayList.size(), "Test", "10.10.2020", false, 0));
        tabArrayList.add("Title");
        tabArrayList.add("IsDone");
    }

    private void loadDB() {
        SqlAdapter sql = SqlAdapter.dbObject(this);
        sql.readTaskFromDB();
    }

    private void initAdapter() {
        taskAdapter = new TaskAdapter(this, R.layout.task_cell, Task.arrayList);
        tasksListView.setAdapter(taskAdapter);
    }

    private void initSpinerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tabArrayList);
        tabSpinner.setAdapter(adapter);

        tabSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                // Handle the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected
            }
        });
    }

    public void newTask(View view) {
        Intent newTaskIntent = new Intent(this, TaskDetailActivity.class);
        startActivity(newTaskIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskAdapter.notifyDataSetChanged();
    }
    public void finishView(View view){
        finish();
    }

    private void setOnClickListener() {
        tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task) tasksListView.getItemAtPosition(position);
                Intent taskEdit = new Intent(getApplicationContext(), TaskDetailActivity.class);
                taskEdit.putExtra("taskID", task.getId());
                startActivity(taskEdit);
            }
        });
    }
}