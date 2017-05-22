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

        Cursor cursorOne = mDbHelper.getDetails(projection);

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try {
            displayView.setText("The habits table contains " + cursorOne.getCount() + " habits.\n\n");
            displayView.append(DatabaseContract.HabitEntry._ID + " - " +
                    DatabaseContract.HabitEntry.COLUMN_HABIT_NAME + " - " +
                    DatabaseContract.HabitEntry.COLUMN_HABIT_FREQUENCY + " - " +
                    DatabaseContract.HabitEntry.COLUMN_HABIT_RATING + " - " + "\n");


            int idColumnIndex = cursorOne.getColumnIndex(DatabaseContract.HabitEntry._ID);
            int nameColumnIndex = cursorOne.getColumnIndex(DatabaseContract.HabitEntry.COLUMN_HABIT_NAME);
            int frequencyColumnIndex = cursorOne.getColumnIndex(DatabaseContract.HabitEntry.COLUMN_HABIT_FREQUENCY);
            int ratingColumnIndex = cursorOne.getColumnIndex(DatabaseContract.HabitEntry.COLUMN_HABIT_RATING);

            while (cursorOne.moveToNext()) {
                int currentID = cursorOne.getInt(idColumnIndex);
                String currentName = cursorOne.getString(nameColumnIndex);
                int currentFrequency = cursorOne.getInt(frequencyColumnIndex);
                int currentRating = cursorOne.getInt(ratingColumnIndex);

                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentFrequency + " - " +
                        currentRating));
            }

        } finally {
            cursorOne.close();
        }
    }
}