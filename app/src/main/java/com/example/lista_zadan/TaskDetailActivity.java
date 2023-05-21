package com.example.lista_zadan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

public class TaskDetailActivity extends AppCompatActivity {
    private EditText titleEditText, descEditText, tabNameEditText;
    private Task selectedTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        init();
        checkIfEdit();
    }

    private void init() {
        titleEditText = findViewById(R.id.taskTitleEditText);
        descEditText = findViewById(R.id.taskDescEditText);
        tabNameEditText = findViewById(R.id.tabNameEditText);
    }

    public void saveTask(View view) {
        SqlAdapter sql = SqlAdapter.dbObject(this);
        String title = String.valueOf(titleEditText.getText());
        String desc = String.valueOf(descEditText.getText());
        String tabName = String.valueOf(tabNameEditText.getText());

        if(selectedTask == null) {
            int id = Task.arrayList.size();
            Task newTask = new Task(id, title, desc, false, tabName);
            Task.arrayList.add(newTask);
            sql.addTaskToDB(newTask);
        }
        else {
            selectedTask.setTitle(title);
            sql.updateTaskInDB(selectedTask);
        }
        finish();
    }

    private void checkIfEdit() {
        Intent pIntent = getIntent();

        int id = pIntent.getIntExtra("editTab", -1);
        selectedTask = Task.getSelectedID(id);

        if (selectedTask != null) {
            titleEditText.setText(selectedTask.getTitle());
        }
    }

    public void finishView(View view){
        finish();
    }
}