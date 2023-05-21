package com.example.lista_zadan;

import java.util.ArrayList;

public class Tab {
    public static ArrayList<Tab> arrayList = new ArrayList<>();
    protected int id;
    protected String title;

    public Tab(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static Tab getSelectedID(int passedID) {
        for(Tab tab : arrayList) {
            if(tab.getId() == passedID)
                return tab;
        }
        return null;
    }
}
