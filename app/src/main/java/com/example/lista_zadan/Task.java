package com.example.lista_zadan;

import java.util.ArrayList;
import java.util.Date;

public class Task extends Tab {
    public static ArrayList<Task> arrayList = new ArrayList<>();
    private String endDate;
    private boolean done;
    private String tabName;
    private boolean deleted;

    public Task(int id, String title, String endDate, Boolean done, String  tabName) {
        super(id, title);
        this.endDate = endDate;
        this.done = done;
        this.tabName = tabName;
        deleted = false;
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean getDone() {
        return done;
    }

    public  void setDone(boolean done) {
        this.done = done;
    }
    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public boolean getDeleted() { return deleted; }
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }
    public static Task getSelectedID(int passedID) {
        for(Task task : arrayList) {
            if(task.getId() == passedID)
                return task;
        }
        return null;
    }

    public static ArrayList<Task> nonDeletedTasks() {
        ArrayList<Task> nonDeleted = new ArrayList<>();
        for (Task task : arrayList) {
            if (!task.getDeleted())
                nonDeleted.add(task);
        }
        return nonDeleted;
    }

}
