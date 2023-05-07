package com.example.lista_zadan;

import java.util.ArrayList;

public class Tab {
    public static ArrayList<Tab> arrayList = new ArrayList<>();
    private int id;
    private String title;

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
}
