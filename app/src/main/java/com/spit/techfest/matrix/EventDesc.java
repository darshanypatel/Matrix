package com.spit.techfest.matrix;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.spit.techfest.matrix.R;

import org.w3c.dom.Text;

public class EventDesc extends ActionBarActivity{

    private ShareActionProvider mShareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_desc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView eventImage = (ImageView)findViewById(R.id.ivEventBig);
        TextView title = (TextView)findViewById(R.id.tvEventName);
        TextView date = (TextView)findViewById(R.id.tvEventDate);
        TextView time = (TextView)findViewById(R.id.tvEventTime);
        TextView venue = (TextView)findViewById(R.id.tvEventVenue);
        TextView desc = (TextView)findViewById(R.id.tvEventDesc);
        Button remindMe = (Button) findViewById(R.id.bRemindMe);
        remindMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Matrix");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.event_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Here is the share content body";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
