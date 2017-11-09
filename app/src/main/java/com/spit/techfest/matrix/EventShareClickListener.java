package com.spit.techfest.matrix;

import android.content.Intent;
import android.view.View;

/**
 * Created by Mustafa on 10/09/2014.
 */
public class EventShareClickListener implements View.OnClickListener{
    @Override
    public void onClick(View view) {
        String msg = "Check out" + ((EventItemView)view).getEventName() + "at Matrix 2014";
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, msg);
        try
        {
            view.getContext().startActivity(Intent.createChooser(shareIntent, "Select an action"));
        }
        catch (Exception ex){}

    }
}
