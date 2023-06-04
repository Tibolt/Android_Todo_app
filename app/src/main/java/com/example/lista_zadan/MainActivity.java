package com.example.lista_zadan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
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

    NotificationManagerCompat notifyManagerCompat;
    Notification notification;
    NotificationCompat.Builder notifyBuilder;

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

        tabArrayList.add("Title");
        tabArrayList.add("IsDone");
        tabArrayList.add("Tab");
        tabArrayList.add("Priority");
    }

    private void loadDB() {
        SqlAdapter sql = SqlAdapter.dbObject(this);
        sql.readTaskFromDB();

//        ArrayList<String> tabNames = sql.getAllTabNames();
//        for (String tab : tabNames){
//            if (! tabArrayList.contains(tab))
//                tabArrayList.add(tab);
//        }
    }

    private void initAdapter() {
        taskAdapter = new TaskAdapter(this, R.layout.task_cell, Task.nonDeletedTasks());
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
                switch (selectedItem) {
                    case "Title":
                        taskAdapter.sortByTitle();
                        break;
                    case "IsDone":
                        taskAdapter.sortByDone();
                        break;
                    case "Priority":
                        taskAdapter.sortByPriority();
                        break;
                    default:
                        taskAdapter.sortByTab();
                        break;
                }
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
        initAdapter();
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
    public void showNotification(View view) {
        // Create an explicit intent to open your app when the notification is clicked
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "tasks")
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                .setContentTitle("Your Notification Title")
                .setContentText("Your Notification Text")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Get the NotificationManagerCompat instance and display the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

}