package com.example.parasite.habittrack.data;

import android.provider.BaseColumns;

/**
 * Created by Parasite on 22-May-17.
 */

public class DatabaseContract {

    private DatabaseContract() {

    }

    public static final class HabitEntry implements BaseColumns {


        public final static String TABLE_NAME = "habits";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_HABIT_NAME = "name";
        public final static String COLUMN_HABIT_FREQUENCY = "frequency";
        public final static String COLUMN_HABIT_RATING = "rating";


        public static final int RATING_NEUTRAL = 0;
        public static final int RATING_GOOD = 1;
        public static final int RATING_BAD = 2;

        public static final int FREQUENCY_NOT_OFTEN = 0;
        public static final int FREQUENCY_OFTEN = 1;
        public static final int FREQUENCY_ALWAYS = 2;

    }
}
