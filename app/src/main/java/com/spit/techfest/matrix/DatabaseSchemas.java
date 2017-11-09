package com.spit.techfest.matrix;

import android.provider.BaseColumns;

/**
 * Created by The Joshis on 8/30/2014.
 *
 * Put all database schemas here
 * Don't instantiate!
 */
public final class DatabaseSchemas {

    public  DatabaseSchemas(){}

    public static abstract class EventEntry implements BaseColumns{
        public static final String TABLE_NAME = "event";
        public static final String COLUMN_NAME_EVENT_ID = "event_id";
        public static final String COLUMN_NAME_TIME_START_DAY1 = "time_start_day1";
        public static final String COLUMN_NAME_DURATION_DAY1 = "duration_day1";
//        public static final String COLUMN_NAME_TIME_START_DAY2 = "time_start_day2";
//        public static final String COLUMN_NAME_DURATION_DAY2 = "duration_day2";
        public static final String COLUMN_NAME_VENUE = "venue";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_CONTACT1 = "contact1";
        public static final String COLUMN_NAME_CONTACT2 = "contact2";
        public static final String COLUMN_NAME_MAX_PARTICIPANTS = "max_participants";
        public static final String COLUMN_NAME_EVENT_NAME = "event_name";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_FAVORITE = "favorite";

    }
}
