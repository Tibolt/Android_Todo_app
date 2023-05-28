package com.example.lista_zadan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

public class TaskDetailActivity extends AppCompatActivity {
    private EditText titleEditText, descEditText, tabNameEditText;
    private CheckBox isDoneCheckBox;
    private Button datePickerButton;
    private DatePickerDialog datePickerDialog;
    private Button deleteButton;
    private Task selectedTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        init();
        checkIfEdit();
        initDateDialog();
    }

    private void init() {
        titleEditText = findViewById(R.id.taskTitleEditText);
//        descEditText = findViewById(R.id.taskDescEditText);
        tabNameEditText = findViewById(R.id.tabNameEditText);
        isDoneCheckBox = findViewById(R.id.isDoneCheck);
        datePickerButton = findViewById(R.id.datePicker);
        deleteButton = findViewById(R.id.deleteButton);
    }

    public void saveTask(View view) {
        SqlAdapter sql = SqlAdapter.dbObject(this);
        String title = String.valueOf(titleEditText.getText());
        String tabName = String.valueOf(tabNameEditText.getText());
        boolean isDone = isDoneCheckBox.isChecked();

        String date = datePickerButton.getText().toString();

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
        datePickerButton.setText(getTodayDate());
//        System.out.println("ID: " + id);

        if (selectedTask != null) {
            titleEditText.setText(selectedTask.getTitle());
            //descEditText.setText(selectedTask.getEndDate());
            datePickerButton.setText(selectedTask.getEndDate());
            tabNameEditText.setText(selectedTask.getTabName());
            isDoneCheckBox.setChecked(selectedTask.getDone());
        }
        else {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }
    public void finishView(View view){
        finish();
    }

    private void initDateDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = toDateString(dayOfMonth, month, year);
                datePickerButton.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        int style = android.R.style.Theme_DeviceDefault_Dialog_Alert;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }

    private String toDateString(int day, int month, int year) {
        return day + " " + getMonthString(month) + " " + year;
    }

    private String getMonthString(int month) {
        if (month==1)
            return "JAN";
        if (month==2)
            return "FEB";
        if (month==3)
            return "MAR";
        if (month==4)
            return "APR";
        if (month==5)
            return "MAY";
        if (month==6)
            return "JUN";
        if (month==7)
            return "JUL";
        if (month==8)
            return "AUG";
        if (month==9)
            return "SEP";
        if (month==10)
            return "OCT";
        if (month==11)
            return "NOV";
        if (month==12)
            return "DEC";
        return "JAN";
    }

    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        month = month+1;
        return toDateString(day, month, year);
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public void deleteTask(View view) {
        SqlAdapter sql = SqlAdapter.dbObject(this);
        selectedTask.setDeleted(true);
        sql.deleteTaskFromDB(selectedTask);
        finish();
    }
}