package com.example.lista_zadan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    public ListView tasksListView;
    public TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loadDB();
        initAdapter();
        setOnClickListener();
    }


    private void init() {
        tasksListView = findViewById(R.id.tasksListView);
        //create items for debugging
        //Tab.arrayList.add(new Tab(Tab.arrayList.size(), "Test"));
        //Task.arrayList.add(new Task(Task.arrayList.size(), "Test", "10.10.2020", false, 0));
    }

    private void loadDB() {
        SqlAdapter sql = SqlAdapter.dbObject(this);
        sql.readTaskFromDB();
    }

    private void initAdapter() {
        taskAdapter = new TaskAdapter(this, R.layout.task_cell, Task.arrayList);
        tasksListView.setAdapter(taskAdapter);
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