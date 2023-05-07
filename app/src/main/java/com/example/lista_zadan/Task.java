package com.example.lista_zadan;

import java.util.ArrayList;
import java.util.Date;

public class Task extends Tab {
    public static ArrayList<Task> arrayList = new ArrayList<>();
    private int id;
    private String title;
    private Date endDate;
    private boolean done;

    private int tabId;

    public Task(int id, String title, Date endDate, Boolean done, int tabId) {
        super(id, title);
        this.endDate = endDate;
        this.done = done;
        this.tabId = tabId;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean getDone() {
        return done;
    }

    public  void setDone(boolean done) {
        this.done = done;
    }

    public int getTabId() {
        return tabId;
    }

    public void setTabId(int tabId) {
        this.tabId = tabId;
    }
}
