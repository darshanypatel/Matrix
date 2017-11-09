package com.spit.techfest.matrix;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.PopupMenu;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by Mustafa on 29/08/2014.
 */
public class EventItemView extends FrameLayout implements View.OnClickListener{

    //Category color to be added

    private ImageView logo,favorite,overflow;
    private TextView name,category,time,date,venue;
    private PopupMenu overflowMenu;
    private boolean isFavorite;
    private EventItem eventItem;
    private final String[] CATEGORY = { "Pre_fest","Fun","BotMania","TechFun","Coding","Tech-Cult","Brain_Drain"};
    private final int[] COLOR_RES = { R.color.material_red, R.color.material_deep_purple,
            R.color.material_indigo,R.color.material_teal,R.color.material_light_green,
            R.color.material_orange,R.color.material_brown};


    public EventItemView(final Context context) {
        super(context);

        View v = LayoutInflater.from(context).inflate(R.layout.event_item,this,false);
        logo = (ImageView)v.findViewById(R.id.event_logo);
        name = (TextView)v.findViewById(R.id.event_name);
        category = (TextView)v.findViewById(R.id.category);
        time = (TextView)v.findViewById(R.id.time);
        date = (TextView)v.findViewById(R.id.date);
        venue = (TextView)v.findViewById(R.id.venue_place);

        favorite = (ImageView)v.findViewById(R.id.img_favorite);
        favorite.setOnClickListener(this);
        overflow = (ImageView)v.findViewById(R.id.img_overflow);
        overflow.setOnClickListener(this);

        overflowMenu = new PopupMenu(context,overflow);
        overflowMenu.inflate(R.menu.event_overflow);
        overflowMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.event_share:
                        String msg = "Check out " + getEventName() + "at Matrix 2014";
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, msg);
                        try
                        {
                            getContext().startActivity(Intent.createChooser(shareIntent, "Select an action"));
                            return true;
                        }
                        catch (Exception ex){}

                        break;
                    case R.id.event_reminder:
                        Calendar cal = Calendar.getInstance();
                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        intent.setType("vnd.android.cursor.item/event");
                        intent.putExtra("beginTime", cal.getTimeInMillis());
                        intent.putExtra("allDay", false);
                        intent.putExtra("rrule", "FREQ=DAILY");
                        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                        intent.putExtra("title", name.getText().toString());
                        getContext().startActivity(intent);
                        break;


                }
                return false;
            }
        });

        v.setOnClickListener(this);
        this.addView(v);
    }

    private void  setCategoryColor()
    {
        for(int i = 0; i < CATEGORY.length; i++)
            if(CATEGORY[i].equalsIgnoreCase(category.getText().toString()))
                category.setTextColor(getResources().getColor(COLOR_RES[i]));
    }



    public void setEvent(EventItem e)
    {

        if(e.getImage() != null) {
            //logo.setImageURI(e.getLogoUri());
            byte [] encodeByte= Base64.decode(e.getImage(), Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            logo.setImageBitmap(bitmap);
        }

        name.setText(e.getName());
        category.setText(e.getCategory().toUpperCase());
        time.setText(e.getTimeDay1());
        date.setText(e.getDate());
        venue.setText(e.getVenue());
        isFavorite = e.getFavorite();
        setFavoriteImage();
        setCategoryColor();
        this.eventItem = e;
    }

    public void setFavorite(boolean value)
    {
        isFavorite = value;
    }

    private void setFavoriteImage()
    {
        if(isFavorite)
            favorite.setImageResource(R.drawable.ic_action_important);
        else
            favorite.setImageResource(R.drawable.ic_action_not_important);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_favorite:
                //insert query here
                setFavorite(!isFavorite);
                setFavoriteImage();
                eventItem.toggleFavorite();
                EventItem.updateDatabase(getContext(),eventItem);
                break;

            case R.id.img_overflow:
                overflowMenu.show();
                break;

            default:
                Intent descIntent = new Intent(getContext(),EventDesc.class);
                getContext().startActivity(descIntent);
                break;




        }

    }

    public String getEventName()
    {
        return name.getText().toString();
    }


}
