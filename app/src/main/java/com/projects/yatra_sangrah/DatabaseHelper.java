package com.projects.yatra_sangrah;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "YatraSangrah.db";
    private static final int DATABASE_VERSION = 1;
    private String tableName;

    public DatabaseHelper(@Nullable Context context,String tableName) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.tableName = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE "+tableName+"("+"id INTEGER PRIMARY KEY AUTOINCREMENT = 1,tripName TEXT NOT NULL";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+tableName);
        onCreate(db);
    }
    public void createTable(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        String create_table = "CREATE TABLE IF NOT EXISTS "+tableName+"("+"id INTEGER PRIMARY KEY AUTOINCREMENT,tripName TEXT NOT NULL)";
        db.execSQL(create_table);
    }
    public boolean insertTrip(String tripName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tripName",tripName);
        long result = db.insert(tableName,null,contentValues);
        return result != -1;
    }
    public Cursor getAllTrips(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+tableName,null);
    }
    public boolean deleteTrip(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(tableName,"id=?",new String[]{String.valueOf(id)});
        return result > 0;
    }
}
