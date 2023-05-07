package com.example.lista_zadan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    public ListView tabsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initAdapter();
    }

    private void init() {
        tabsListView = findViewById(R.id.tabsListView);
    }

    private void initAdapter() {
        TabAdapter tabAdapter = new TabAdapter(getApplicationContext(), Tab.arrayList);
        tabsListView.setAdapter(tabAdapter);

    }

    public void newTab(View view) {
        Intent newTabIntent = new Intent(this, TabDetailActivity.class);
        startActivity(newTabIntent);
    }
}