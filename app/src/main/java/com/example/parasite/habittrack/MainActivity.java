package com.example.parasite.habittrack;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.parasite.habittrack.data.DatabaseContract;
import com.example.parasite.habittrack.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new DatabaseHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInformation();
    }

    private void displayDatabaseInformation() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseContract.HabitEntry._ID,
                DatabaseContract.HabitEntry.COLUMN_HABIT_NAME,
                DatabaseContract.HabitEntry.COLUMN_HABIT_FREQUENCY,
                DatabaseContract.HabitEntry.COLUMN_HABIT_RATING};

        Cursor cursor = db.query(
                DatabaseContract.HabitEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try {
            displayView.setText("The habits table contains " + cursor.getCount() + " habits.\n\n");
            displayView.append(DatabaseContract.HabitEntry._ID + " - " +
                    DatabaseContract.HabitEntry.COLUMN_HABIT_NAME + " - " +
                    DatabaseContract.HabitEntry.COLUMN_HABIT_FREQUENCY + " - " +
                    DatabaseContract.HabitEntry.COLUMN_HABIT_RATING + " - " + "\n");


            int idColumnIndex = cursor.getColumnIndex(DatabaseContract.HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(DatabaseContract.HabitEntry.COLUMN_HABIT_NAME);
            int frequencyColumnIndex = cursor.getColumnIndex(DatabaseContract.HabitEntry.COLUMN_HABIT_FREQUENCY);
            int ratingColumnIndex = cursor.getColumnIndex(DatabaseContract.HabitEntry.COLUMN_HABIT_RATING);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentFrequency = cursor.getInt(frequencyColumnIndex);
                int currentRating = cursor.getInt(ratingColumnIndex);

                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentFrequency + " - " +
                        currentRating));


            }
        } finally {
            cursor.close();
        }


    }

}




