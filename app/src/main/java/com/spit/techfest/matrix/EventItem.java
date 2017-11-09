package com.spit.techfest.matrix;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Abbas on 28/08/2014.
 */
public class EventItem {

    private int id;
    private static int count = 0;
    private String name;
    private String category;
    private String venue;
    private String date;
    //    private String time;
    private String durationDay1;
    //    private String durationDay2;
    private String timeDay1;
    //    private String timeDay2;
    private String description;
    private String contact1;
    private String contact2;
    private String image;
    private int maxParticipants;
    private Uri logoUri;
    private int favorite;

    public EventItem() {}

    public EventItem(String name, String category, String venue, String durationDay1, String durationDay2, String timeDay1, String timeDay2)
    {
        this.name = name;
        this.category = category;
        this.venue = venue;
        this.date = "27/09/2014";
        this.durationDay1 = durationDay1;
//        this.durationDay2 = durationDay2;
//        this.time = timeDay1;
        this.timeDay1 = timeDay1;
//        this.timeDay2 = timeDay2;
    }

    /**
     *Returns an ArrayList of all EventItems in the database
     *PLANNED: Add an overloaded method to query for events within a category
     *
     *This method should probably be used with an AsyncTask
     */
    public static ArrayList<EventItem> getEvents(Context context){
        Log.d("get", "getEvents called");
        EventsDbHelper eventsDbHelper = new EventsDbHelper(context);
        SQLiteDatabase db;
        db = eventsDbHelper.getReadableDatabase();
        ArrayList<EventItem> events = new ArrayList<EventItem>();

        String[] projection = {
                DatabaseSchemas.EventEntry.COLUMN_NAME_EVENT_ID,
                DatabaseSchemas.EventEntry.COLUMN_NAME_VENUE,
                DatabaseSchemas.EventEntry.COLUMN_NAME_CATEGORY,
                DatabaseSchemas.EventEntry.COLUMN_NAME_EVENT_NAME,
                DatabaseSchemas.EventEntry.COLUMN_NAME_TIME_START_DAY1,
//                DatabaseSchemas.EventEntry.COLUMN_NAME_TIME_START_DAY2,
                DatabaseSchemas.EventEntry.COLUMN_NAME_DESCRIPTION,
                DatabaseSchemas.EventEntry.COLUMN_NAME_DURATION_DAY1,
//                DatabaseSchemas.EventEntry.COLUMN_NAME_DURATION_DAY2,
                DatabaseSchemas.EventEntry.COLUMN_NAME_MAX_PARTICIPANTS,
                DatabaseSchemas.EventEntry.COLUMN_NAME_CONTACT1,
                DatabaseSchemas.EventEntry.COLUMN_NAME_CONTACT2,
                DatabaseSchemas.EventEntry.COLUMN_NAME_IMAGE,
                DatabaseSchemas.EventEntry.COLUMN_NAME_FAVORITE,
        };

        Cursor c = db.query(
                DatabaseSchemas.EventEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (c.moveToFirst()){
            do{
                int id = c.getInt(c.getColumnIndex(DatabaseSchemas.EventEntry.COLUMN_NAME_EVENT_ID));
                String name = c.getString(c.getColumnIndex(DatabaseSchemas.EventEntry.COLUMN_NAME_EVENT_NAME));
                String category = c.getString(c.getColumnIndex(DatabaseSchemas.EventEntry.COLUMN_NAME_CATEGORY));
                String venue = c.getString(c.getColumnIndex(DatabaseSchemas.EventEntry.COLUMN_NAME_VENUE));
                String duration1 = c.getString(c.getColumnIndex(DatabaseSchemas.EventEntry.COLUMN_NAME_DURATION_DAY1));
//              String duration2 = c.getString(c.getColumnIndex(DatabaseSchemas.EventEntry.COLUMN_NAME_DURATION_DAY2));
                int maxPart = c.getInt(c.getColumnIndex(DatabaseSchemas.EventEntry.COLUMN_NAME_MAX_PARTICIPANTS));
                String time1 = c.getString(c.getColumnIndex(DatabaseSchemas.EventEntry.COLUMN_NAME_TIME_START_DAY1));
//                String time2 = c.getString(c.getColumnIndex(DatabaseSchemas.EventEntry.COLUMN_NAME_TIME_START_DAY2));
                String contact1 = c.getString(c.getColumnIndex(DatabaseSchemas.EventEntry.COLUMN_NAME_CONTACT1));
                String contact2 = c.getString(c.getColumnIndex(DatabaseSchemas.EventEntry.COLUMN_NAME_CONTACT2));
                String description = c.getString(c.getColumnIndex(DatabaseSchemas.EventEntry.COLUMN_NAME_DESCRIPTION));
                String image = c.getString(c.getColumnIndex(DatabaseSchemas.EventEntry.COLUMN_NAME_IMAGE));
                int favorite = c.getInt(c.getColumnIndex(DatabaseSchemas.EventEntry.COLUMN_NAME_FAVORITE));


                EventItem event = new EventItem();

                event.setName(name);
                event.setCategory(category);
                event.setVenue(venue);
                event.setDurationDay1(duration1);
//                event.setDurationDay2(duration2);
                event.setMaxParticipants(maxPart);
                event.setTimeDay1(time1);
//                event.setTimeDay2(time2);
//                event.setTime(time1);
                event.setDescription(description);
                event.setImage(image);
                event.setContact1(contact1);
                event.setContact2(contact2);
                event.setFavorite(favorite);
                event.setId(id);


                events.add(event);
            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return events;
    }

    /*
    To save values to database, just use this method with an EventItem parameter
    e.g. EventItem.saveToDatabase(getApplicationContext(), event)

     This method should probably be used with an AsyncTask
     */
    public static void saveToDatabase(Context context, EventItem event){
        Log.d("save", "saveToDataBase called " + (count + 1));
        count++;
        EventsDbHelper eventsDbHelper = new EventsDbHelper(context);
        SQLiteDatabase db = eventsDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DatabaseSchemas.EventEntry.COLUMN_NAME_CATEGORY, event.getCategory());
        cv.put(DatabaseSchemas.EventEntry.COLUMN_NAME_EVENT_ID, event.getId());
        cv.put(DatabaseSchemas.EventEntry.COLUMN_NAME_CONTACT1, event.getContact1());
        cv.put(DatabaseSchemas.EventEntry.COLUMN_NAME_CONTACT2, event.getContact2());
        cv.put(DatabaseSchemas.EventEntry.COLUMN_NAME_DESCRIPTION, event.getDescription());
        cv.put(DatabaseSchemas.EventEntry.COLUMN_NAME_DURATION_DAY1, event.getDurationDay1());
//        cv.put(DatabaseSchemas.EventEntry.COLUMN_NAME_DURATION_DAY2, event.getDurationDay2());
        cv.put(DatabaseSchemas.EventEntry.COLUMN_NAME_EVENT_NAME, event.getName());
        cv.put(DatabaseSchemas.EventEntry.COLUMN_NAME_IMAGE, event.getImage());
        cv.put(DatabaseSchemas.EventEntry.COLUMN_NAME_MAX_PARTICIPANTS, event.getMaxParticipants());
        cv.put(DatabaseSchemas.EventEntry.COLUMN_NAME_TIME_START_DAY1, event.getTimeDay1());
//        cv.put(DatabaseSchemas.EventEntry.COLUMN_NAME_TIME_START_DAY2, event.getTimeDay2());
        cv.put(DatabaseSchemas.EventEntry.COLUMN_NAME_VENUE, event.getVenue());
        cv.put(DatabaseSchemas.EventEntry.COLUMN_NAME_DATE, event.getDate());
        cv.put(DatabaseSchemas.EventEntry.COLUMN_NAME_FAVORITE, 0);

        db.insert(DatabaseSchemas.EventEntry.TABLE_NAME, null, cv);
        Log.d("saved ID", "" + (Integer)cv.get(DatabaseSchemas.EventEntry.COLUMN_NAME_EVENT_ID));
        db.close();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /*    public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    */
    public Uri getLogoUri() {
        return logoUri;
    }

    public void setLogoUri(Uri logoUri) {
        this.logoUri = logoUri;
    }

    /*public String getDurationDay2() { return durationDay2; }*/

    /*public void setDurationDay2(String durationDay2) { this.durationDay2 = durationDay2; }*/

    public String getDurationDay1() {
        return durationDay1;
    }

    public void setDurationDay1(String durationDay1) {
        this.durationDay1 = durationDay1;
    }

    public String getTimeDay1() {
        return timeDay1;
    }

    public void setTimeDay1(String timeDay1) {
        this.timeDay1 = timeDay1;
    }

    /*public String getTimeDay2() {
        return timeDay2;
    }*/

    /*public void setTimeDay2(String timeDay2) {
        this.timeDay2 = timeDay2;
    }*/

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public void delete_content(Context context){
        EventsDbHelper eventsDbHelper = new EventsDbHelper(context);
        SQLiteDatabase db;
        db = eventsDbHelper.getReadableDatabase();
        db.delete(DatabaseSchemas.EventEntry.TABLE_NAME,null,null);

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getFavorite() {
        if (favorite == 1) return true;
        else return false;
    }

    public void toggleFavorite() {
        if(favorite == 1)
            this.favorite = 0;
        else this.favorite = 1;
    }

    public void setFavorite(boolean favorite) {
        if(favorite)
            this.favorite = 1;
        else this.favorite = 0;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public static void updateDatabase(Context context, EventItem eventItem) {
        EventsDbHelper dbHelper = new EventsDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Log.d("updateDatabase", "Updating database");


        ContentValues cv = new ContentValues();
        cv.put(DatabaseSchemas.EventEntry.COLUMN_NAME_FAVORITE, eventItem.getFavorite());
        db.update(DatabaseSchemas.EventEntry.TABLE_NAME,
                cv,
                DatabaseSchemas.EventEntry.COLUMN_NAME_EVENT_NAME + " = ? ",
                new String[]{eventItem.getName()}
        );

        db.close();
    }

}


