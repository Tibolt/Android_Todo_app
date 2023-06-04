package com.example.lista_zadan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.util.Calendar;
import java.util.Date;

public class TaskDetailActivity extends AppCompatActivity {
    private EditText titleEditText, tabNameEditText;
    private NumberPicker priorityEditText;
    private CheckBox isDoneCheckBox;
    private Button datePickerButton;
    private DatePickerDialog datePickerDialog;
    private Button deleteButton;
    private Task selectedTask;
    private NotificationReciver notificationReceiver;
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        init();
        checkIfEdit();
        initDateDialog();

        createNotifiactionChannel();
    }

    private void init() {
        titleEditText = findViewById(R.id.taskTitleEditText);
        tabNameEditText = findViewById(R.id.tabNameEditText);
        isDoneCheckBox = findViewById(R.id.isDoneCheck);
        datePickerButton = findViewById(R.id.datePicker);
        deleteButton = findViewById(R.id.deleteButton);
        priorityEditText = findViewById(R.id.priorityEditText);

        priorityEditText.setMinValue(1);
        priorityEditText.setMaxValue(3);
    }

    public void saveTask(View view) {
        SqlAdapter sql = SqlAdapter.dbObject(this);
        String title = String.valueOf(titleEditText.getText());
        String tabName = String.valueOf(tabNameEditText.getText());
        boolean isDone = isDoneCheckBox.isChecked();
        int priority = priorityEditText.getValue();

        String date = datePickerButton.getText().toString();

        if(selectedTask == null) {
            int id = Task.arrayList.size();
            Task newTask = new Task(id, title, date, isDone, tabName, priority);
            Task.arrayList.add(newTask);
            sql.addTaskToDB(newTask);
        }
        else {
            selectedTask.setTitle(title);
            selectedTask.setEndDate(date);
            selectedTask.setTabName(tabName);
            selectedTask.setDone(isDone);
            selectedTask.setPriority(priority);

            sql.updateTaskInDB(selectedTask);
        }
        // Schedule the notification
        scheduleNotification(title, "Bookmark: " + tabName);
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
            priorityEditText.setValue(selectedTask.getPriority());
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
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        calendar.add(Calendar.MINUTE, 1); // Add one minute to the current time
//        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//
//        showAlert(calendar.getTimeInMillis());
    }

    private void showAlert(long time) {
        new AlertDialog.Builder(this)
            .setTitle("Notification Date")
            .setMessage(
                    "Title: Test" +
                    "\nTime: " + new Date(time)
            )
            .setPositiveButton("Okay", null)
            .show();
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
    private void scheduleNotification(String title, String text) {
        // Create an intent to launch the notification
        Intent notificationIntent = new Intent(this, NotificationReciver.class);
        notificationIntent.putExtra("title", title);
        notificationIntent.putExtra("text", text);
        pendingIntent = PendingIntent.getBroadcast(this, NotificationReciver.notificationID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Get the AlarmManager instance and schedule the notification
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (60 * 1000), pendingIntent);

    }
    private void createNotifiactionChannel() {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NotificationReciver.channelID, "Notification channel", importance);
            NotificationManager notifyManager = getSystemService(NotificationManager.class);
            notifyManager.createNotificationChannel(channel);
        }
    }
}