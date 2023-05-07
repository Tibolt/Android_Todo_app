package com.example.lista_zadan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class TabDetailActivity extends AppCompatActivity {
    private EditText titleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_detail);
        init();

    }

    private void init() {
        titleEditText = findViewById(R.id.tabTitleEditText);
    }

    public void saveTab(View view) {
        String title = String.valueOf(titleEditText.getText());

        int id = Tab.arrayList.size();
        Tab newTab = new Tab(id, title);
        Tab.arrayList.add(newTab);

        finish();
    }
}