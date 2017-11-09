package com.spit.techfest.matrix;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by The Joshis on 8/30/2014.
 *
 * Helper class for the DB
 */
public class EventsDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DB_NAME = "Events.db";
    private Context context;
    private String DB_PATH;
    public SQLiteDatabase database;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private final static String SQL_CREATE_ENTRIES = "CREATE TABLE " + DatabaseSchemas.EventEntry.TABLE_NAME + " (" +
            DatabaseSchemas.EventEntry._ID + " INTEGER PRIMARY KEY," +
            DatabaseSchemas.EventEntry.COLUMN_NAME_EVENT_ID + TEXT_TYPE + COMMA_SEP +
            DatabaseSchemas.EventEntry.COLUMN_NAME_EVENT_NAME + TEXT_TYPE + COMMA_SEP +
            DatabaseSchemas.EventEntry.COLUMN_NAME_CATEGORY + TEXT_TYPE + COMMA_SEP +
            DatabaseSchemas.EventEntry.COLUMN_NAME_CONTACT1 + TEXT_TYPE + COMMA_SEP +
            DatabaseSchemas.EventEntry.COLUMN_NAME_CONTACT2 + TEXT_TYPE + COMMA_SEP +
            DatabaseSchemas.EventEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
            DatabaseSchemas.EventEntry.COLUMN_NAME_DURATION_DAY1 + TEXT_TYPE + COMMA_SEP +
            DatabaseSchemas.EventEntry.COLUMN_NAME_TIME_START_DAY1 + TEXT_TYPE + COMMA_SEP +
            DatabaseSchemas.EventEntry.COLUMN_NAME_MAX_PARTICIPANTS + INTEGER_TYPE + COMMA_SEP +
            DatabaseSchemas.EventEntry.COLUMN_NAME_VENUE + TEXT_TYPE + COMMA_SEP +
            DatabaseSchemas.EventEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
            DatabaseSchemas.EventEntry.COLUMN_NAME_FAVORITE + INTEGER_TYPE + COMMA_SEP +
            DatabaseSchemas.EventEntry.COLUMN_NAME_IMAGE + TEXT_TYPE +
            " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseSchemas.EventEntry.TABLE_NAME;

    public EventsDbHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;
        //Write a full path to the databases of your application
        String packageName = context.getPackageName();
        DB_PATH = String.format("//data//data//%s//databases//", packageName);
//       openDataBase();
    }

 /*   public SQLiteDatabase openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        if (database == null) {
            createDataBase();
            database = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        return database;
    }

    private void copyDataBase() throws IOException {
        //Open a stream for reading from our ready-made database
        //The stream source is located in the assets
        InputStream externalDbStream = context.getAssets().open(DB_NAME);

        //Path to the created empty database on your Android device
        String outFileName = DB_PATH + DB_NAME;

        //Now create a stream for writing the database byte by byte
        OutputStream localDbStream = new FileOutputStream(outFileName);

        //Copying the database
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = externalDbStream.read(buffer)) > 0) {
            localDbStream.write(buffer, 0, bytesRead);
        }
        //Don’t forget to close the streams
        localDbStream.close();
        externalDbStream.close();
    }

    public void createDataBase() {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e(this.getClass().toString(), "Copying error");
                throw new Error("Error copying database!");
            }
        } else {
            Log.i(this.getClass().toString(), "Database already exists");
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDb = null;
        try {
            String path = DB_PATH + DB_NAME;
            checkDb = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "Error while checking db");
        }
        //Android doesn’t like resource leaks, everything should
        // be closed
        if (checkDb != null) {
            checkDb.close();
        }
        return checkDb != null;
    }
*/
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
