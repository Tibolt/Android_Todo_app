package com.example.lista_zadan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class SqlAdapter extends SQLiteOpenHelper {

    private  static SqlAdapter sqlAdapter;

    private static final String DATABASE_NAME = "NoteDB";
    private static final int DATABASE_VERSION = 1;


    /* Inner class that defines the table contents */
    public static class TabDB implements BaseColumns {
        public static final String TABLE_NAME = "Tab";
        public static final String COLUMN_NAME_TITLE = "title";
    }
    public static class TaskDB implements BaseColumns {
        public static final String TABLE_NAME = "Task";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String IS_DONE = "isDone";
        public static final String END_DATE = "endDate";
        public static final String TAB_NAME = "tabName";
    }
    private static final String TAB_CREATE_ENTRIES =
            "CREATE TABLE " +
            TabDB.TABLE_NAME + " (" +
            TabDB._ID + " INTEGER PRIMARY KEY," +
            TabDB.COLUMN_NAME_TITLE + " TEXT)";
    private static final String TAB_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TabDB.TABLE_NAME;

    private static final String TASK_CREATE_ENTRIES =
            "CREATE TABLE " +
            TaskDB.TABLE_NAME + " (" +
            TaskDB._ID + " INTEGER PRIMARY KEY," +
            TaskDB.COLUMN_NAME_TITLE + " TEXT," +
            TaskDB.END_DATE + " TEXT," +
            TaskDB.IS_DONE + " TEXT," +
            TaskDB.TAB_NAME + " TEXT)";

    private static final String TASK_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TaskDB.TABLE_NAME;

    public SqlAdapter(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SqlAdapter dbObject(Context context) {
        if(sqlAdapter == null)
            sqlAdapter = new SqlAdapter(context);

        return sqlAdapter;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TASK_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TASK_DELETE_ENTRIES);
        onCreate(db);
    }


    public void addTabToDB(Tab tab) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Check if there is an item with the same ID in the database
        String query = "SELECT * FROM " + TabDB.TABLE_NAME +" WHERE _id = ?";
        String[] selectionArgs = {String.valueOf(tab.getId())};
        Cursor cursor = sql.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            // If there is an item with the same ID, increment the ID by 1
            tab.setId(tab.getId() + 1);
        }
        cursor.close();

        contentValues.put(TabDB._ID, tab.getId());
        contentValues.put(TabDB.COLUMN_NAME_TITLE, tab.getTitle());
        sql.insert(TabDB.TABLE_NAME, null, contentValues);
        sql.close();
    }

    public void addTaskToDB(Task task) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Check if there is an item with the same ID in the database
        String query = "SELECT * FROM " + TaskDB.TABLE_NAME +" WHERE _id = ?";
        String[] selectionArgs = {String.valueOf(task.getId())};
        Cursor cursor = sql.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            // If there is an item with the same ID, increment the ID by 1
            task.setId(task.getId() + 1);
        }
        cursor.close();

        String done;
        if (task.getDone())
            done = "true";
        else done = "false";


        contentValues.put(TaskDB._ID, task.getId());
        contentValues.put(TaskDB.COLUMN_NAME_TITLE, task.getTitle());
        contentValues.put(TaskDB.END_DATE, task.getEndDate());
        contentValues.put(TaskDB.IS_DONE, done);
        contentValues.put(TaskDB.TAB_NAME, task.getTabName());
        sql.insert(TaskDB.TABLE_NAME, null, contentValues);
        sql.close();
    }

    public void readTabFromDB() {
        SQLiteDatabase sql = this.getReadableDatabase();

        try (Cursor result = sql.rawQuery("SELECT * FROM " + TabDB.TABLE_NAME, null)) {
            if(result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    String title = result.getString(1);

                    Tab tab = new Tab(id, title);
                    Tab.arrayList.add(tab);
                }
            }
        }
    }

    public void readTaskFromDB() {
        SQLiteDatabase sql = this.getReadableDatabase();

        try (Cursor result = sql.rawQuery("SELECT * FROM " + TaskDB.TABLE_NAME, null)) {
            if(result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    String title = result.getString(1);
                    String endDate = result.getString(2);
                    String isDone = result.getString(3);
                    String tabName = result.getString(4);

                    boolean done;
                    if (isDone.equals("true"))
                        done = true;
                    else done = false;

                    Task task = new Task(id, title, endDate, done, tabName);
                    Task.arrayList.add(task);
                }
            }
        }
        sql.close();
    }

    public void updateTaskInDB(Task task) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String done;
        if (task.getDone())
            done = "true";
        else done = "false";

        contentValues.put(TaskDB._ID, task.getId());
        contentValues.put(TaskDB.COLUMN_NAME_TITLE, task.getTitle());
        contentValues.put(TaskDB.END_DATE, task.getEndDate());
        contentValues.put(TaskDB.IS_DONE, done);
        contentValues.put(TaskDB.TAB_NAME, task.getTabName());

        sql.update(TaskDB.TABLE_NAME, contentValues, TaskDB._ID + " =? ", new String[]{String.valueOf(task.getId())});
        sql.close();
    }

    public void deleteTaskFromDB(Task task) {
        SQLiteDatabase sql = this.getWritableDatabase();
        sql.delete(TaskDB.TABLE_NAME, "_id = ?", new String[] { String.valueOf(task.getId()) });
        sql.close();
    }

    public ArrayList<String> getAllTabNames() {
        ArrayList<String> tabNames = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + TaskDB.TAB_NAME + " FROM " + TaskDB.TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String tabName = cursor.getString(cursor.getColumnIndex(TaskDB.TAB_NAME));
                tabNames.add(tabName);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return tabNames;
    }
}
