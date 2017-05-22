package com.example.parasite.habittrack.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.parasite.habittrack.data.DatabaseContract.HabitEntry;

/**
 * Created by Parasite on 22-May-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "habitlogs.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_HABITS_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_HABIT_FREQUENCY + " INTEGER NOT NULL DEFAULT 0, "
                + HabitEntry.COLUMN_HABIT_RATING + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_HABITS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getDetails(String[] projection) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseContract.HabitEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }
}