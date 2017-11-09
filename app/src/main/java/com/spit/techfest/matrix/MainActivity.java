package com.spit.techfest.matrix;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;

import android.support.v4.app.FragmentManager;


import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import android.support.v4.widget.DrawerLayout;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private final int SCHEDULE = 0;
    private final int EVENTS = 1;
    private final int FAVORITE = 2;
    private final int ABOUT_US = 3;


    ProgressDialog progressDialog;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    JSONArray jArray = null;

    String event_name, timeday1, durationday1, venue, category, date, url;

    SharedPreferences sharedPreferences;

    ByteArrayOutputStream baos;

    boolean isFirstStart;

    Bitmap bitmap;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private Drawable actionBarColor;//= new ColorDrawable(getResources().getColor(R.color.black));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Matrix", 0);
        isFirstStart = sharedPreferences.getBoolean(getString(R.string.first_start), true);


        if(isFirstStart) {
            Log.d("Use", "First use");

            EventItem e = new EventItem();
            //if(count == 0)
            e.delete_content(getApplicationContext());

            new getAllListItems().execute(new ApiConnector());

        }
        else Log.d("Use", "Not first use");

        EventListFragment.mEvents = EventItem.getEvents(this);



        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        actionBarColor= new ColorDrawable(getResources().getColor(R.color.black));
        getSupportActionBar().setBackgroundDrawable(actionBarColor);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

   /* @Override
    protected void onRestart() {
        super.onRestart();

        EventItem e = new EventItem();
        if(count == 0)
            e.delete_content(getApplicationContext());

        if(isFirstStart) {
            Log.d("Use", "First use");

            new getAllListItems().execute(new ApiConnector());

        }
        else Log.d("Use", "Not first use");
    }*/

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        // update the main content by replacing fragments
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position)
        {
            case SCHEDULE:
                fragment = EventListFragment.newInstance("schedule","");
                getSupportActionBar().setBackgroundDrawable(actionBarColor);
                break;
            case EVENTS:
                fragment = new EventTabFragment();
                break;
            case FAVORITE:
                fragment = EventListFragment.newInstance("favorite","");
                getSupportActionBar().setBackgroundDrawable(actionBarColor);
                break;
            case ABOUT_US:
                //Todo
                fragment = new AboutUs();
                getSupportActionBar().setBackgroundDrawable(actionBarColor);
                break;
        }
        onSectionAttached(position);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case SCHEDULE:

                mTitle = getString(R.string.title_schedule);
                restoreActionBar();
                break;
            case EVENTS:

                mTitle = getString(R.string.title_events);
                break;
            case FAVORITE:

                mTitle = getString(R.string.title_favorite);
                restoreActionBar();
                break;
            case ABOUT_US:
                mTitle = getString(R.string.title_about_us);
                restoreActionBar();
                break;
        }
        getSupportActionBar().setTitle(mTitle);

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.app_share:
                String msg = "Check out MATRIX The Fest:\n";
                //TODO replace path
                String path = "https://play.google.com/store/apps/details?id=" + getPackageName();
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, msg + path);
                try {
                    startActivity(Intent.createChooser(intent, "Select an action"));
                } catch (android.content.ActivityNotFoundException ex) {
                    // (handle error)
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private class getAllListItems extends AsyncTask<ApiConnector, Long, JSONArray>
    {

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(Integer.parseInt(values[0] + ""));
        }

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            jArray =  params[0].getListItems();

            double count = 100.0 / (jArray.length() / 2);
            int t = (int) count;
            //double total = 0;

            if(jArray == null)
                Log.d("Jarray ", "Jarray is null");
            else
            {
                for (int i = 0,j = jArray.length()/2; i < jArray.length()/2; i++, j++)
                {
                    try {

                        Log.d("count ", count + "");

                        JSONObject jsonObject = jArray.getJSONObject(i);
                        JSONObject jsonObject1 = jArray.getJSONObject(j);

                        //event info :
                        event_name = jsonObject.getString("event_name");
                        timeday1 = jsonObject.getString("time_start_day1");
                        date = "26/9/2014";

                        venue = jsonObject.getString("venue");
                        category = jsonObject.getString("category");
                        durationday1 = jsonObject.getString("duration_day2");

                        //images :
                        url = jsonObject1.getString("image_src");

                        URL u = new URL(url);
                        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream input = conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(input);

                        baos=new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                        byte [] b=baos.toByteArray();
                        String temp= Base64.encodeToString(b, Base64.DEFAULT);

                        EventItem e = new EventItem();
                        e.setName(event_name);
                        e.setCategory(category);
                        e.setDurationDay1(durationday1);
                        e.setVenue(venue);
                        e.setDate(date);
                        e.setImage(temp);
                        e.setTimeDay1(timeday1);

                        EventItem.saveToDatabase(getApplicationContext(), e);

                        progressDialog.incrementProgressBy(t);
                        /*if(i % 2 == 0)
                            t += 1;
                        else
                            t -= 1;*/
                    }
                    catch(JSONException e)
                    {
                        e.printStackTrace();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            return jArray;

        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            progressDialog.dismiss();

            //Boolean isFirstStart = sharedPreferences.getBoolean(getString(R.string.first_start), true);

            if(isFirstStart) {
                Log.d("Use", "First use");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.first_start), false);
                editor.commit();
            }
            finish();
            startActivity(getIntent());
        }

        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(MainActivity.this, android.R.style.Theme_DeviceDefault_Light_Panel);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(81);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(true);
            progressDialog.setTitle("Please Wait...");
            progressDialog.setMessage("Download in progress...");
            progressDialog.show();
        }
    }
}
