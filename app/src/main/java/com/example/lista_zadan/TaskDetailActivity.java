package com.example.lista_zadan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Date;

public class TaskDetailActivity extends AppCompatActivity {
    private EditText titleEditText, descEditText, tabNameEditText;
    private CheckBox isDoneCheckBox;
    private DatePicker datePicker;
    private Button deleteButton;
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
//        descEditText = findViewById(R.id.taskDescEditText);
        tabNameEditText = findViewById(R.id.tabNameEditText);
        isDoneCheckBox = findViewById(R.id.isDoneCheck);
        datePicker = findViewById(R.id.datePicker);
        deleteButton = findViewById(R.id.deleteButton);
    }

    public void saveTask(View view) {
        SqlAdapter sql = SqlAdapter.dbObject(this);
        String title = String.valueOf(titleEditText.getText());
        String tabName = String.valueOf(tabNameEditText.getText());
        boolean isDone = isDoneCheckBox.isChecked();

        String date = datePicker.toString();

        if(selectedTask == null) {
            int id = Task.arrayList.size();
            Task newTask = new Task(id, title, date, isDone, tabName);
            Task.arrayList.add(newTask);
            sql.addTaskToDB(newTask);
        }
        else {
            selectedTask.setTitle(title);
            selectedTask.setEndDate(date);
            selectedTask.setTabName(tabName);
            selectedTask.setDone(isDone);

            sql.updateTaskInDB(selectedTask);
        }
        finish();
    }

    private void checkIfEdit() {
        Intent pIntent = getIntent();

        int id = pIntent.getIntExtra("taskID", -1);
        selectedTask = Task.getSelectedID(id);
//        System.out.println("ID: " + id);

        if (selectedTask != null) {
            titleEditText.setText(selectedTask.getTitle());
            //descEditText.setText(selectedTask.getEndDate());
            tabNameEditText.setText(selectedTask.getTabName());
            isDoneCheckBox.setChecked(selectedTask.getDone());
        }
    }

    public void finishView(View view){
        finish();
    }
}