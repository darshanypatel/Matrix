package com.spit.techfest.matrix;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abbas on 28/08/2014.
 */
public class EventListAdapter extends ArrayAdapter<EventItem> {

    private Context context;
    private List<EventItem> mObjects;
    public EventListAdapter(Context context, int resource, List<EventItem> objects) {
        super(context, resource, objects);
        mObjects = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = new EventItemView(context);
        EventItemView convertEvent = (EventItemView)convertView;
        try {
            convertEvent.setEvent(mObjects.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
