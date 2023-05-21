package com.example.lista_zadan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

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
        public static final String TAB_ID = "tabID";
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
            TaskDB.TAB_ID + " INTEGER)";

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
        db.execSQL(TAB_CREATE_ENTRIES);
        db.execSQL(TASK_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TAB_DELETE_ENTRIES);
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

    public void addTaskToDB(Task task, int tabID) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TaskDB._ID, task.getId());
        contentValues.put(TaskDB.COLUMN_NAME_TITLE, task.getTitle());
        contentValues.put(TaskDB.END_DATE, task.getEndDate());
        contentValues.put(TaskDB.IS_DONE, task.getDone());
        contentValues.put(TaskDB.TAB_ID, tabID);
        sql.insert(TaskDB.TABLE_NAME, null, contentValues);
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
//
//        while(cursor.moveToNext()) {
//            int id = cursor.getInt(cursor.getColumnIndexOrThrow(TabDB._ID));
//            String title = cursor.getString(cursor.getColumnIndexOrThrow(TabDB.COLUMN_NAME_TITLE));
//            Tab tab = new Tab(id, title);
//            Tab.arrayList.add(tab);
//        }
//        cursor.close();


    }

    public void readTaskFromDB() {
        SQLiteDatabase sql = this.getReadableDatabase();

        try (Cursor result = sql.rawQuery("SELECT * FROM " + TaskDB.TABLE_NAME, null)) {
            if(result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String title = result.getString(2);
                    String endDate = result.getString(3);
                    String isDone = result.getString(4);
                    int tabID = result.getInt(5);

                    boolean done;
                    done = isDone.equals("true");

                    Task task = new Task(id, title, endDate, done, tabID);
                    Task.arrayList.add(task);
                }
            }
        }
    }

    public void updateTabInDB(Tab tab) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TabDB._ID, tab.getId());
        contentValues.put(TabDB.COLUMN_NAME_TITLE, tab.getTitle());

        sql.update(TabDB.TABLE_NAME, contentValues, TabDB._ID + " =? ", new String[]{String.valueOf(tab.getId())});
    }
}
