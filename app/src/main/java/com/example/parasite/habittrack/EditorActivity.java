package com.example.parasite.habittrack;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.parasite.habittrack.data.DatabaseContract;
import com.example.parasite.habittrack.data.DatabaseHelper;

/**
 * Created by Parasite on 22-May-17.
 */

public class EditorActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private Spinner mFrequencySpinner;
    private Spinner mRatingSpinner;

    private int mFrequency = DatabaseContract.HabitEntry.FREQUENCY_NOT_OFTEN;
    private int mRating = DatabaseContract.HabitEntry.RATING_BAD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        mNameEditText = (EditText) findViewById(R.id.edit_habit_name);
        mFrequencySpinner = (Spinner) findViewById(R.id.spinner_frequency);
        mRatingSpinner = (Spinner) findViewById(R.id.spinner_rating);


        setupSpinner();

    }


    private void setupSpinner() {

        ArrayAdapter frequencySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_frequency_options, android.R.layout.simple_spinner_item);

        ArrayAdapter ratingSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_rating_options, android.R.layout.simple_spinner_item);

        frequencySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ratingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mFrequencySpinner.setAdapter(frequencySpinnerAdapter);
        mRatingSpinner.setAdapter(ratingSpinnerAdapter);

        mFrequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection1 = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection1)) {
                    if (selection1.equals(getString(R.string.not_often_habit))) {
                        mFrequency = DatabaseContract.HabitEntry.FREQUENCY_NOT_OFTEN;
                    } else if (selection1.equals(getString(R.string.often_habit))) {
                        mFrequency = DatabaseContract.HabitEntry.FREQUENCY_OFTEN;
                    } else if (selection1.equals(getString(R.string.everyday_habit))) {
                        mFrequency = DatabaseContract.HabitEntry.FREQUENCY_ALWAYS;
                    }
                } else mFrequency = DatabaseContract.HabitEntry.FREQUENCY_NOT_OFTEN;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mFrequency = DatabaseContract.HabitEntry.FREQUENCY_NOT_OFTEN;
            }
        });

        mRatingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.neutral_habit))) {
                        mRating = DatabaseContract.HabitEntry.RATING_NEUTRAL;
                    } else if (selection.equals(getString(R.string.good_habit))) {
                        mRating = DatabaseContract.HabitEntry.RATING_GOOD;
                    } else if (selection.equals(getString(R.string.bad_habit))) {
                        mRating = DatabaseContract.HabitEntry.RATING_BAD;
                    } else mRating = DatabaseContract.HabitEntry.RATING_BAD;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mFrequency = DatabaseContract.HabitEntry.RATING_NEUTRAL;
            }
        });
    }

    private void insertHabit() {

        String nameString = mNameEditText.getText().toString().trim();
        DatabaseHelper mDbHelper = new DatabaseHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DatabaseContract.HabitEntry.COLUMN_HABIT_NAME, nameString);
        values.put(DatabaseContract.HabitEntry.COLUMN_HABIT_FREQUENCY, mFrequency);
        values.put(DatabaseContract.HabitEntry.COLUMN_HABIT_RATING, mRating);

        long newRowId = db.insert(DatabaseContract.HabitEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving pet", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Pet saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertHabit();
                finish();
                return true;
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
