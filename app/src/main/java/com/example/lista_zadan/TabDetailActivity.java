package com.example.lista_zadan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        SqlAdapter sql = SqlAdapter.dbObject(this);

        int id = Tab.arrayList.size();
        String title = String.valueOf(titleEditText.getText());
        Tab newTab = new Tab(id, title);
        Tab.arrayList.add(newTab);
        sql.addTabToDB(newTab);
        finish();
    }

    public void finishView(View view){
        finish();
    }
}