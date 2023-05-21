package com.example.lista_zadan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TabDetailActivity extends AppCompatActivity {
    private EditText titleEditText;
    private Tab selectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_detail);
        init();
        checkIfEdit();

    }

    private void init() {
        titleEditText = findViewById(R.id.tabTitleEditText);
    }

    public void saveTab(View view) {
        SqlAdapter sql = SqlAdapter.dbObject(this);
        String title = String.valueOf(titleEditText.getText());

        if(selectedTab == null) {

            int id = Tab.arrayList.size();
            Tab newTab = new Tab(id, title);
            Tab.arrayList.add(newTab);
            sql.addTabToDB(newTab);
        }
        else {
            selectedTab.setTitle(title);
        }
        finish();
    }

    public void finishView(View view){
        finish();
    }

    private void checkIfEdit() {
        Intent pIntent = getIntent();

        int tabID = pIntent.getIntExtra("editTab", -1);
        selectedTab = Tab.getSelectedID(tabID);

        if (selectedTab != null) {
            titleEditText.setText(selectedTab.getTitle());
        }
    }
}