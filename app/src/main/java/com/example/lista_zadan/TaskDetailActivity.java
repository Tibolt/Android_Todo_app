package com.example.lista_zadan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

public class TaskDetailActivity extends AppCompatActivity {
    private EditText titleEditText, descEditText;
    private int tabID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        init();
    }

    private void init() {
        titleEditText = findViewById(R.id.taskTitleEditText);
        descEditText = findViewById(R.id.taskDescEditText);

        Intent pIntent = getIntent();
        tabID = pIntent.getIntExtra("currentTabID", -1);
        System.out.println("ID of tab: " + tabID);
    }

    public void saveTask(View view) {
        String title = String.valueOf(titleEditText.getText());
        String desc = String.valueOf(descEditText.getText());

        int id = Task.arrayList.size();
        Task newTask = new Task(id, title, desc, false, tabID);
        Task.arrayList.add(newTask);

        finish();
    }

    public void finishView(View view){
        finish();
    }
}