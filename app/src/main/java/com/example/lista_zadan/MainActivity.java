package com.example.lista_zadan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    public ListView tabsListView;
    public TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loadDB();
        initAdapter();
    }

    private void init() {
        tabsListView = findViewById(R.id.tabsListView);
        //create items for debugging
        //Tab.arrayList.add(new Tab(Tab.arrayList.size(), "Test"));
        //Task.arrayList.add(new Task(Task.arrayList.size(), "Test", "10.10.2020", false, 0));
    }

    private void loadDB() {
        SqlAdapter sql = SqlAdapter.dbObject(this);
        sql.readTabFromDB();
    }

    private void initAdapter() {
        tabAdapter = new TabAdapter(getApplicationContext(), R.layout.tab_cell, Tab.arrayList);
        tabsListView.setAdapter(tabAdapter);

    }

    public void newTab(View view) {
        Intent newTabIntent = new Intent(this, TabDetailActivity.class);
        startActivity(newTabIntent);
    }

    public void tasksView(View view) {
        Intent tasksView = new Intent(this, TaskActivity.class);
        startActivity(tasksView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tabAdapter.notifyDataSetChanged();
    }
}